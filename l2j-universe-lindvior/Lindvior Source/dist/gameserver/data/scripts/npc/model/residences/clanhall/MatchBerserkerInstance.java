package npc.model.residences.clanhall;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.instances.residences.clanhall.CTBBossInstance;
import l2p.gameserver.templates.npc.NpcTemplate;

/**
 * @author VISTALL
 * @date 19:37/22.04.2011
 */
public class MatchBerserkerInstance extends CTBBossInstance {
    private static final long serialVersionUID = 1L;

    public MatchBerserkerInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void reduceCurrentHp(double damage, double refdamage, Creature attacker, Skill skill, boolean awake, boolean standUp, boolean directHp, boolean canReflect, boolean transferDamage, boolean isDot, boolean sendMessage) {
        if (attacker.isPlayer())
            damage = ((damage / getMaxHp()) / 0.05) * 100;
        else
            damage = ((damage / getMaxHp()) / 0.05) * 10;

        super.reduceCurrentHp(damage, refdamage, attacker, skill, awake, standUp, directHp, canReflect, transferDamage, isDot, sendMessage);
    }
}
