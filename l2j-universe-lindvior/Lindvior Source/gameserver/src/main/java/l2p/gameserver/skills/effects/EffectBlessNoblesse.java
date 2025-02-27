package l2p.gameserver.skills.effects;

import l2p.gameserver.model.Effect;
import l2p.gameserver.stats.Env;

public final class EffectBlessNoblesse extends Effect {
    public EffectBlessNoblesse(Env env, EffectTemplate template) {
        super(env, template);
    }

    @Override
    public void onStart() {
        super.onStart();
        getEffected().setIsBlessedByNoblesse(true);
    }

    @Override
    public void onExit() {
        super.onExit();
        getEffected().setIsBlessedByNoblesse(false);
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}