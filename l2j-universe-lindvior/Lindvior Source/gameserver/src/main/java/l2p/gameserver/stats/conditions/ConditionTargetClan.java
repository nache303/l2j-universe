package l2p.gameserver.stats.conditions;

import l2p.gameserver.model.Creature;
import l2p.gameserver.stats.Env;

public class ConditionTargetClan extends Condition {
    private final boolean _test;

    public ConditionTargetClan(String param) {
        _test = Boolean.valueOf(param);
    }

    @Override
    protected boolean testImpl(Env env) {
        Creature Char = env.character;
        Creature target = env.target;
        return Char.getPlayer() != null && target.getPlayer() != null && (Char.getPlayer().getClanId() != 0 && Char.getPlayer().getClanId() == target.getPlayer().getClanId() == _test || Char.getPlayer().getParty() != null && Char.getPlayer().getParty() == target.getPlayer().getParty());
    }
}