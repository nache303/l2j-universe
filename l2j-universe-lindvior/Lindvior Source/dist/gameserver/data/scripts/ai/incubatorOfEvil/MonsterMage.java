/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package ai.incubatorOfEvil;

import l2p.gameserver.ai.CtrlEvent;
import l2p.gameserver.ai.Mystic;
import l2p.gameserver.geodata.GeoEngine;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.instances.NpcInstance;

import java.util.List;

/**
 * @author Mazaffaka
 */
public class MonsterMage extends Mystic {
    private Creature target = null;

    public MonsterMage(NpcInstance actor) {
        super(actor);
    }

    @Override
    public boolean isGlobalAI() {
        return false;
    }

    @Override
    protected void onEvtSpawn() {
        startAttack();
    }

    @Override
    protected boolean thinkActive() {
        return startAttack();
    }

    private boolean startAttack() {
        NpcInstance actor = getActor();
        if (target == null) {
            List<Creature> around = actor.getAroundCharacters(3000, 150);
            if (around != null && !around.isEmpty()) {
                for (Creature obj : around) {
                    if (checkTarget(obj)) {
                        if (target == null || actor.getDistance3D(obj) < actor.getDistance3D(target))
                            target = obj;
                    }
                }
            }
        }

        if (target != null && !actor.isAttackingNow() && !actor.isCastingNow() && !target.isDead() && GeoEngine.canSeeTarget(actor, target, false) && target.isVisible()) {
            actor.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, target, 1);
            return true;
        }

        if (target != null && (!target.isVisible() || target.isDead() || !GeoEngine.canSeeTarget(actor, target, false))) {
            target = null;
            return false;
        } else if (defaultThinkBuff(10))
            return true;

        return false;
    }

    private boolean checkTarget(Creature target) {
        if (target == null)
            return false;
        if (target.isPlayer())
            return true;

        if (target.isNpc()) {
            NpcInstance npc = (NpcInstance) target;
            int _id = npc.getNpcId();

            if (_id == 27430 || _id == 27431 || _id == 27432 || _id == 27433 || _id == 27434 || _id == 27425 || _id == 33416)
                return false;
        }
        return true;
    }
}