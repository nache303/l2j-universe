package l2p.gameserver.taskmanager;

import l2p.commons.threading.RunnableImpl;
import l2p.commons.threading.SteppingRunnableQueueManager;
import l2p.commons.util.Rnd;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.model.Player;

import java.util.concurrent.Future;

/**
 * Менеджер автосохранения игроков, шаг выполенния задач 10с.
 *
 * @author G1ta0
 */
public class AutoSaveManager extends SteppingRunnableQueueManager {
    private static final AutoSaveManager _instance = new AutoSaveManager();

    public static final AutoSaveManager getInstance() {
        return _instance;
    }

    private AutoSaveManager() {
        super(10000L);
        ThreadPoolManager.getInstance().scheduleAtFixedRate(this, 10000L, 10000L);
        //Очистка каждые 60 секунд
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl() {
            @Override
            public void runImpl() throws Exception {
                AutoSaveManager.this.purge();
            }

        }, 60000L, 60000L);
    }

    public Future<?> addAutoSaveTask(final Player player) {
        long delay = Rnd.get(180, 360) * 1000L;

        return scheduleAtFixedRate(new RunnableImpl() {

            @Override
            public void runImpl() throws Exception {
                if (!player.isOnline())
                    return;

                player.store(true);
            }

        }, delay, delay);
    }
}