package npc.model.residences.clanhall;

import l2p.gameserver.model.*;
import l2p.gameserver.model.entity.events.impl.SiegeEvent;
import l2p.gameserver.model.pledge.Clan;
import l2p.gameserver.tables.SkillTable;
import l2p.gameserver.templates.npc.NpcTemplate;
import npc.model.residences.SiegeGuardInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * @author VISTALL
 * @date 13:28/06.03.2011
 */
public class NurkaInstance extends SiegeGuardInstance {
    private static final long serialVersionUID = 1L;

    public static final Skill SKILL = SkillTable.getInstance().getInfo(5456, 1);

    public NurkaInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void reduceCurrentHp(double damage, double refdamage, Creature attacker, Skill skill, boolean awake, boolean standUp, boolean directHp, boolean canReflect, boolean transferDamage, boolean isDot, boolean sendMessage) {
        if (attacker.getLevel() > (getLevel() + 8) && attacker.getEffectList().getEffectsCountForSkill(SKILL.getId()) == 0) {
            doCast(SKILL, attacker, false);
            return;
        }

        super.reduceCurrentHp(damage, refdamage, attacker, skill, awake, standUp, directHp, canReflect, transferDamage, isDot, sendMessage);
    }

    @Override
    public void onDeath(Creature killer) {
        SiegeEvent siegeEvent = getEvent(SiegeEvent.class);
        if (siegeEvent == null)
            return;

        siegeEvent.processStep(getMostDamagedClan());

        super.onDeath(killer);

        deleteMe();
    }

    public Clan getMostDamagedClan() {
        Player temp = null;

        Map<Player, Integer> damageMap = new HashMap<Player, Integer>();

        for (AggroList.HateInfo info : getAggroList().getPlayableMap().values()) {
            Playable killer = (Playable) info.attacker;
            int damage = info.damage;
            if (killer.isServitor())
                temp = killer.getPlayer();
            else if (killer.isPlayer())
                temp = (Player) killer;

            if (temp == null || temp.getClan() == null || temp.getClan().getHasHideout() > 0)
                continue;

            if (!damageMap.containsKey(temp))
                damageMap.put(temp, damage);
            else {
                int dmg = damageMap.get(temp) + damage;
                damageMap.put(temp, dmg);
            }
        }

        int mostDamage = 0;
        Player player = null;

        for (Map.Entry<Player, Integer> entry : damageMap.entrySet()) {
            int damage = entry.getValue();
            Player t = entry.getKey();
            if (damage > mostDamage) {
                mostDamage = damage;
                player = t;
            }
        }

        return player == null ? null : player.getClan();
    }

    @Override
    public boolean isEffectImmune() {
        return true;
    }
}
