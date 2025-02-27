package ai.residences.dominion;

import l2p.commons.util.Rnd;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.ai.DefaultAI;
import l2p.gameserver.data.xml.holder.EventHolder;
import l2p.gameserver.model.entity.events.EventType;
import l2p.gameserver.model.entity.events.impl.DominionSiegeRunnerEvent;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.network.serverpackets.components.NpcString;
import l2p.gameserver.scripts.Functions;

import java.util.Calendar;

public class MercenaryCaptain extends DefaultAI {
    private static final NpcString[] MESSAGES = new NpcString[]{
            NpcString.COURAGE_AMBITION_PASSION_MERCENARIES_WHO_WANT_TO_REALIZE_THEIR_DREAM_OF_FIGHTING_IN_THE_TERRITORY_WAR_COME_TO_ME_FORTUNE_AND_GLORY_ARE_WAITING_FOR_YOU,
            NpcString.DO_YOU_WISH_TO_FIGHT_ARE_YOU_AFRAID_NO_MATTER_HOW_HARD_YOU_TRY_YOU_HAVE_NOWHERE_TO_RUN};

    public MercenaryCaptain(NpcInstance actor) {
        super(actor);
        AI_TASK_ACTIVE_DELAY = AI_TASK_ATTACK_DELAY = 1000L;
    }

    @Override
    public synchronized void startAITask() {
        if (_aiTask == null)
            _aiTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(this, calcDelay(), 3600000L);
    }

    @Override
    protected synchronized void switchAITask(long NEW_DELAY) {
    }

    @Override
    protected boolean thinkActive() {
        NpcInstance actor = getActor();
        if (actor.isDead())
            return true;

        NpcString shout;
        DominionSiegeRunnerEvent runnerEvent = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 1);
        if (runnerEvent.isInProgress())
            shout = NpcString.CHARGE_CHARGE_CHARGE;
        else
            shout = MESSAGES[Rnd.get(MESSAGES.length)];

        Functions.npcShout(actor, shout);

        return false;
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }

    private static long calcDelay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 55);
        cal.set(Calendar.SECOND, 0);

        long t = System.currentTimeMillis();
        while (cal.getTimeInMillis() < t)
            cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTimeInMillis() - t;
    }
}