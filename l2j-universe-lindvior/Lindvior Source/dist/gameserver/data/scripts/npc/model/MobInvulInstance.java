package npc.model;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.instances.MonsterInstance;
import l2p.gameserver.templates.npc.NpcTemplate;

/**
 * Данный инстанс используется мобами, которых нельзя убить, но нужно, чтобы на них действовали дебафы в onEvtSeeSpell
 *
 * @author n0nam3
 */
public final class MobInvulInstance extends MonsterInstance {
    /**
     *
     */
    private static final long serialVersionUID = 6645955537695928843L;

    public MobInvulInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(Player player, int val, Object... arg) {
    }

    @Override
    public void showChatWindow(Player player, String filename, Object... replace) {
    }

    @Override
    public void reduceCurrentHp(double i, double rd, Creature attacker, Skill skill, boolean awake, boolean standUp, boolean directHp, boolean canReflect, boolean transferDamage, boolean isDot, boolean sendMessage) {
    }

    @Override
    public boolean isInvul() {
        return false;
    }

    @Override
    public boolean isFearImmune() {
        return true;
    }

    @Override
    public boolean isParalyzeImmune() {
        return true;
    }

    @Override
    public boolean isLethalImmune() {
        return true;
    }
}