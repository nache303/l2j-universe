package l2p.gameserver.taskmanager;

import l2p.commons.threading.RunnableImpl;
import l2p.commons.threading.SteppingRunnableQueueManager;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.model.Creature;

import java.util.concurrent.Future;

/**
 * Менеджер задач по "исчезновению" убитых персонажей, шаг выполенния задач 500 мс.
 *
 * @author G1ta0
 */
public class DecayTaskManager extends SteppingRunnableQueueManager {
    private static final DecayTaskManager _instance = new DecayTaskManager();

    public static final DecayTaskManager getInstance() {
        return _instance;
    }

    private DecayTaskManager() {
        super(500L);

        ThreadPoolManager.getInstance().scheduleAtFixedRate(this, 500L, 500L);

        //Очистка каждую минуту
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl() {
            @Override
            public void runImpl() throws Exception {
                DecayTaskManager.this.purge();
            }

        }, 60000L, 60000L);
    }

    public Future<?> addDecayTask(final Creature actor, long delay) {
        return schedule(new RunnableImpl() {

            @Override
            public void runImpl() throws Exception {
                actor.doDecay();
            }

        }, delay);
    }
}