package instances;

import l2p.commons.geometry.Polygon;
import l2p.commons.threading.RunnableImpl;
import l2p.commons.util.Rnd;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.listener.actor.OnCurrentHpDamageListener;
import l2p.gameserver.listener.actor.OnDeathListener;
import l2p.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2p.gameserver.model.*;
import l2p.gameserver.model.entity.Reflection;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.network.serverpackets.ExStartScenePlayer;
import l2p.gameserver.network.serverpackets.SystemMessage;
import l2p.gameserver.utils.Location;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Инстанс Октависа, имеет 3ри стадии боя. Нормальный вариант Сделать: 29192 u,Зверь Октависа\0 a, 9C E8 A9 -1 TODO[K] - Систему работы Храма Орбиса, и связку между ними. Skills: 14027, 14035, 14279-14282, 14285, 14474(5),
 */
public class OctavisNormal extends Reflection {
    private static final int Octavis1 = 29191;
    private static final int Octavis2 = 29193;
    private static final int Octavis3 = 29194;
    private static final int OctavisRider = 29192;

    private static final int guardOctavisGladiator = 22928; // Two Stage Guard TODO[K]: 23086, 23143, 23145,
    private static final int guardOctavisHighAcademic = 22930; // Three Stage Guard TODO[K]: 23088,

    public final ZoneListener _epicZoneListener = new ZoneListener();
    public final DeathListener _deathListener = new DeathListener();
    public final CurrentHpListener _currentHpListenerFistsStage = new CurrentHpListener();
    public final CurrentHpListener _currentHpListenerOctavisRide = new CurrentHpListener();
    public final CurrentHpListener _currentHpListenerTwoStage = new CurrentHpListener();

    public ScheduledFuture<?> twoStageGuardSpawn;
    public ScheduledFuture<?> threeStageGuardSpawn;

    public boolean _entryLocked = false;
    public boolean _startLaunched = false;
    public boolean _lockedTurn = false;

    public NpcInstance octavisMassive;

    private static final int Door = 26210001;
    private static final int Door2 = 26210002;

    public static Territory centralRoomPoint = new Territory().add(new Polygon().add(210548, -118712).add(210548, -118800).add(210348, -118612).add(210748, -118312).add(210548, -118912).add(210648, -118812).add(210548, -118712).add(210448, -118512).setZmax(-10020).setZmin(-9020));

    public final AtomicInteger raidplayers = new AtomicInteger();

    @Override
    protected void onCreate() {
        super.onCreate();
        getZone("[Octavis_epic]").addListener(_epicZoneListener);
    }

    @Override
    protected void onCollapse() {
        super.onCollapse();
    }

    public class ZoneListener implements OnZoneEnterLeaveListener {
        @Override
        public void onZoneEnter(Zone zone, Creature cha) {
            if (_entryLocked) {
                return;
            }

            Player player = cha.getPlayer();
            if ((player == null) || !cha.isPlayer()) {
                return;
            }

            if (checkstartCond(raidplayers.incrementAndGet())) {
                ThreadPoolManager.getInstance().schedule(new FirstStage(), 30000L);
                _startLaunched = true;
            }
        }

        @Override
        public void onZoneLeave(Zone zone, Creature cha) {
            Player player = cha.getPlayer();
            if ((player == null) || !cha.isPlayer()) {
                return;
            }

            raidplayers.decrementAndGet();
        }
    }

    public boolean checkstartCond(int raidplayers) {
        return !((raidplayers < getInstancedZone().getMinParty()) || _startLaunched);
    }

    public class CurrentHpListener implements OnCurrentHpDamageListener {
        @Override
        public void onCurrentHpDamage(Creature actor, double damage, Creature attacker, Skill skill) {
            if (actor.getNpcId() == Octavis1) {
                if ((actor == null) || actor.isDead()) {
                    return;
                }

                if (!_lockedTurn && (actor.getCurrentHp() <= 25000)) {
                    actor.setIsInvul(true);
                    _lockedTurn = true;
                    ThreadPoolManager.getInstance().schedule(new TwoStage(), 10);
                    actor.removeListener(_currentHpListenerFistsStage);
                    octavisMassive.removeListener(_currentHpListenerOctavisRide);
                    for (NpcInstance n : getNpcs()) {
                        n.deleteMe();
                    }
                }
            }

            if (actor.getNpcId() == Octavis2) {
                if ((actor == null) || actor.isDead()) {
                    return;
                }

                if (!_lockedTurn && (actor.getCurrentHp() <= 25000)) {
                    _lockedTurn = true;
                    ThreadPoolManager.getInstance().schedule(new ThreeStage(), 10);
                    actor.removeListener(_currentHpListenerTwoStage);
                    for (NpcInstance n : getNpcs()) {
                        n.deleteMe();
                    }
                }
            }

            if (actor.getNpcId() == OctavisRider) {
                if ((actor == null) || actor.isDead()) {
                    return;
                }

                double newHp = actor.getCurrentHp();
                double maxHp = actor.getMaxHp();

                if (newHp <= (maxHp / 2)) {
                    actor.setIsInvul(true);
                    octavisMassive.setLockedTarget(false);
                } else {
                    actor.setIsInvul(false);
                    octavisMassive.setLockedTarget(true);
                }
            }
        }
    }

    private class FirstStage extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            _entryLocked = true;

            closeDoor(Door);
            closeDoor(Door2);

            for (Player player : getPlayers()) {
                player.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_OCTABIS_OPENING);
            }

            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    NpcInstance octavisFirstStage = addSpawnWithoutRespawn(Octavis1, new Location(207192, 120568, -10032, 49151), 0);
                    octavisFirstStage.addListener(_currentHpListenerFistsStage);
                    octavisFirstStage.setLockedTarget(true);
                    octavisMassive = octavisFirstStage;

                    NpcInstance octavisRider = addSpawnWithoutRespawn(OctavisRider, new Location(207192, 120588, -10032, 49151), 0);
                    octavisRider.addListener(_currentHpListenerOctavisRide);
                }
            }, 26700);
        }
    }

    private class TwoStage extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            for (Player player : getPlayers()) {
                player.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_OCTABIS_PHASECH_A);
            }

            twoStageGuardSpawn = ThreadPoolManager.getInstance().scheduleAtFixedRate(new SpawnGuardForStage(1), 10000L, 120000L); // 10 secs for movie

            NpcInstance octavisTwoStage = addSpawnWithoutRespawn(Octavis2, new Location(207192, 120568, -10032, 49151), 0);
            octavisTwoStage.addListener(_currentHpListenerTwoStage);
            _lockedTurn = false;
        }
    }

    private class SpawnGuardForStage extends RunnableImpl {
        int _stage, _guard1MinCount, _guard1MaxCount, _guard2MinCount, _guard2MaxCount;

        public SpawnGuardForStage(int stage) // 1 - two, 2 - three
        {
            _stage = stage;
        }

        @Override
        public void runImpl() throws Exception {
            switch (_stage) {
                case 1:
                    _guard1MinCount = 5;
                    _guard1MaxCount = 10;
                    break;
                case 2:
                    _guard2MinCount = 5;
                    _guard2MaxCount = 10;
                    _guard1MinCount = 5;
                    _guard1MaxCount = 10;
                    break;
                default:
                    break;
            }

            for (int i = 0; i < Rnd.get(_guard1MinCount, _guard1MaxCount); i++) {
                addSpawnWithoutRespawn(guardOctavisGladiator, Territory.getRandomLoc(centralRoomPoint, getGeoIndex()), 0);
            }
            for (int i = 0; i < Rnd.get(_guard2MinCount, _guard2MaxCount); i++) {
                addSpawnWithoutRespawn(guardOctavisHighAcademic, Territory.getRandomLoc(centralRoomPoint, getGeoIndex()), 0);
            }
        }
    }

    private class ThreeStage extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            twoStageGuardSpawn.cancel(true);
            for (Player player : getPlayers()) {
                player.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_OCTABIS_PHASECH_B);
            }

            threeStageGuardSpawn = ThreadPoolManager.getInstance().scheduleAtFixedRate(new SpawnGuardForStage(2), 14000L, 120000L); // 14 secs for movie

            NpcInstance octavisThreeStage = addSpawnWithoutRespawn(Octavis3, new Location(207192, 120568, -10032, 49151), 0);
            octavisThreeStage.addListener(_deathListener);
        }
    }

    private class DeathListener implements OnDeathListener {
        @Override
        public void onDeath(Creature self, Creature killer) {
            if (self.isNpc() && (self.getNpcId() == Octavis3)) {
                threeStageGuardSpawn.cancel(true);
                ThreadPoolManager.getInstance().schedule(new EndingMovie(), 10);
                openDoor(Door);
                openDoor(Door2);
            }
        }
    }

    private class EndingMovie extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            for (Player p : getPlayers()) {
                p.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_OCTABIS_ENDING);
            }
            for (NpcInstance n : getNpcs()) {
                n.deleteMe();
            }
            ThreadPoolManager.getInstance().schedule(new CollapseInstance(), 38000L); // 38 secs for movie
        }
    }

    public class CollapseInstance extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            startCollapseTimer(5 * 60 * 1000L);
            doCleanup();
            for (Player p : getPlayers()) {
                p.sendPacket(new SystemMessage(SystemMessage.THIS_DUNGEON_WILL_EXPIRE_IN_S1_MINUTES).addNumber(5));
            }
        }
    }

    public void doCleanup() {
        if (twoStageGuardSpawn != null) {
            twoStageGuardSpawn.cancel(true);
        }
        if (threeStageGuardSpawn != null) {
            threeStageGuardSpawn.cancel(true);
        }
    }
}