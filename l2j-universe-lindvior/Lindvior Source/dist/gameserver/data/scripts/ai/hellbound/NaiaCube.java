package ai.hellbound;

import l2p.commons.threading.RunnableImpl;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.ai.DefaultAI;
import l2p.gameserver.instancemanager.naia.NaiaCoreManager;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.utils.ReflectionUtils;

/**
 * @author pchayka
 */
public class NaiaCube extends DefaultAI {

    public NaiaCube(NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        ThreadPoolManager.getInstance().schedule(new Despawn(getActor()), 120 * 1000L);
    }

    private class Despawn extends RunnableImpl {
        NpcInstance _npc;

        private Despawn(NpcInstance npc) {
            _npc = npc;
        }

        @Override
        public void runImpl() {
            _npc.deleteMe();
            NaiaCoreManager.setZoneActive(false);
            ReflectionUtils.getDoor(20240001).openMe(); // Beleth Door
            ReflectionUtils.getDoor(18250025).openMe(); // Epidos Door
        }
    }
}