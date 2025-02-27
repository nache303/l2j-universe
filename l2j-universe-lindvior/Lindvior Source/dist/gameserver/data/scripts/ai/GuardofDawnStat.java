package ai;

import l2p.commons.threading.RunnableImpl;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.ai.DefaultAI;
import l2p.gameserver.geodata.GeoEngine;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Playable;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.World;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.scripts.Functions;
import l2p.gameserver.tables.SkillTable;
import l2p.gameserver.utils.Location;

public class GuardofDawnStat extends DefaultAI {
    private static final int _aggrorange = 120;
    private static final Skill _skill = SkillTable.getInstance().getInfo(5978, 1);
    private Location _locTele = null;
    private boolean noCheckPlayers = false;

    public GuardofDawnStat(NpcInstance actor, Location telePoint) {
        super(actor);
        AI_TASK_ATTACK_DELAY = 200;
        setTelePoint(telePoint);
    }

    public class Teleportation extends RunnableImpl {

        Location _telePoint = null;
        Playable _target = null;

        public Teleportation(Location telePoint, Playable target) {
            _telePoint = telePoint;
            _target = target;
        }

        @Override
        public void runImpl() {
            _target.teleToLocation(_telePoint);
            noCheckPlayers = false;
        }
    }

    @Override
    protected boolean thinkActive() {
        NpcInstance actor = getActor();

        // проверяем игроков вокруг
        if (!noCheckPlayers)
            checkAroundPlayers(actor);

        return true;
    }

    private boolean checkAroundPlayers(NpcInstance actor) {
        for (Playable target : World.getAroundPlayables(actor, _aggrorange, _aggrorange))
            if (target != null && target.isPlayer() && !target.isInvul() && GeoEngine.canSeeTarget(actor, target, false)) {
                actor.doCast(_skill, target, true);
                Functions.npcSay(actor, "Intruder alert!! We have been infiltrated!");
                noCheckPlayers = true;
                ThreadPoolManager.getInstance().schedule(new Teleportation(getTelePoint(), target), 3000);
                return true;
            }
        return false;
    }

    private void setTelePoint(Location loc) {
        _locTele = loc;
    }

    private Location getTelePoint() {
        return _locTele;
    }

    @Override
    protected void thinkAttack() {
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }

    @Override
    protected void onIntentionAttack(Creature target) {
    }

    @Override
    protected void onEvtAttacked(Creature attacker, int damage) {
    }

    @Override
    protected void onEvtAggression(Creature attacker, int aggro) {
    }

    @Override
    protected void onEvtClanAttacked(Creature attacked_member, Creature attacker, int damage) {
    }

}