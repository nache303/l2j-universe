package ai;

import l2p.commons.util.Rnd;
import l2p.gameserver.ai.CtrlEvent;
import l2p.gameserver.ai.Fighter;
import l2p.gameserver.data.xml.holder.NpcHolder;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.SimpleSpawner;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.utils.Location;

/**
 * AI моба Frost Buffalo для Frozen Labyrinth.<br>
 * - Если был атакован физическим скилом, спавнится миньон-мобы Lost Buffalo 22093 в количестве 4 штук.<br>
 * - Не используют функцию Random Walk, если были заспавнены "миньоны"<br>
 *
 * @author SYS
 */
public class FrostBuffalo extends Fighter {
    private boolean _mobsNotSpawned = true;
    private static final int MOBS = 22093;
    private static final int MOBS_COUNT = 4;

    public FrostBuffalo(NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtSeeSpell(Skill skill, Creature caster) {
        NpcInstance actor = getActor();
        if (skill.isMagic())
            return;
        if (_mobsNotSpawned) {
            _mobsNotSpawned = false;
            for (int i = 0; i < MOBS_COUNT; i++)
                try {
                    SimpleSpawner sp = new SimpleSpawner(NpcHolder.getInstance().getTemplate(MOBS));
                    sp.setLoc(Location.findPointToStay(actor, 100, 120));
                    NpcInstance npc = sp.doSpawn(true);
                    if (caster.isPet() || caster.isServitor())
                        npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, caster, Rnd.get(2, 100));
                    npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, caster.getPlayer(), Rnd.get(1, 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected void onEvtDead(Creature killer) {
        _mobsNotSpawned = true;
        super.onEvtDead(killer);
    }

    @Override
    protected boolean randomWalk() {
        return _mobsNotSpawned;
    }
}