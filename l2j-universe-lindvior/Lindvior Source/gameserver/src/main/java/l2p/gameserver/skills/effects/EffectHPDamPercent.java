package l2p.gameserver.skills.effects;

import l2p.gameserver.model.Effect;
import l2p.gameserver.stats.Env;

public class EffectHPDamPercent extends Effect {
    public EffectHPDamPercent(final Env env, final EffectTemplate template) {
        super(env, template);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (_effected.isDead())
            return;

        double newHp = (100. - calc()) * _effected.getMaxHp() / 100.;
        newHp = Math.min(_effected.getCurrentHp(), Math.max(0, newHp));
        _effected.setCurrentHp(newHp, false);
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}