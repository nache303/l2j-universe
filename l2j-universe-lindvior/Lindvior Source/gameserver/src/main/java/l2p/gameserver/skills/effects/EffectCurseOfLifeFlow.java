package l2p.gameserver.skills.effects;

import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntIterator;
import l2p.commons.lang.reference.HardReference;
import l2p.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Effect;
import l2p.gameserver.model.Skill;
import l2p.gameserver.network.serverpackets.SystemMessage;
import l2p.gameserver.stats.Env;

public final class EffectCurseOfLifeFlow extends Effect {
    private CurseOfLifeFlowListener _listener;

    private TObjectIntHashMap<HardReference<? extends Creature>> _damageList = new TObjectIntHashMap<HardReference<? extends Creature>>();

    public EffectCurseOfLifeFlow(Env env, EffectTemplate template) {
        super(env, template);
    }

    @Override
    public void onStart() {
        super.onStart();
        _listener = new CurseOfLifeFlowListener();
        _effected.addListener(_listener);
    }

    @Override
    public void onExit() {
        super.onExit();
        _effected.removeListener(_listener);
        _listener = null;
    }

    @Override
    public boolean onActionTime() {
        if (_effected.isDead())
            return false;

        for (TObjectIntIterator<HardReference<? extends Creature>> iterator = _damageList.iterator(); iterator.hasNext(); ) {
            iterator.advance();
            Creature damager = iterator.key().get();
            if (damager == null || damager.isDead() || damager.isCurrentHpFull())
                continue;

            int damage = iterator.value();
            if (damage <= 0)
                continue;

            double max_heal = calc();
            double heal = Math.min(damage, max_heal);
            double newHp = Math.min(damager.getCurrentHp() + heal, damager.getMaxHp());

            damager.sendPacket(new SystemMessage(SystemMessage.S1_HPS_HAVE_BEEN_RESTORED).addNumber((long) (newHp - damager.getCurrentHp())));
            damager.setCurrentHp(newHp, false);
        }

        _damageList.clear();

        return true;
    }

    private class CurseOfLifeFlowListener implements OnCurrentHpDamageListener {
        @Override
        public void onCurrentHpDamage(Creature actor, double damage, Creature attacker, Skill skill) {
            if (attacker == actor || attacker == _effected)
                return;
            int old_damage = _damageList.get(attacker.getRef());
            _damageList.put(attacker.getRef(), old_damage == 0 ? (int) damage : old_damage + (int) damage);
        }
    }
}