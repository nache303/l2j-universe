package quests;

import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author pchayka
 */
public class _251_NoSecrets extends Quest implements ScriptFile {
    private static final int GuardPinaps = 30201;
    private static final int[] SelMahumTrainers = {22775, 22776, 22777, 22778};
    private static final int[] SelMahumRecruits = {22780, 22781, 22782, 22783, 22784, 22785};
    private static final int SelMahumTrainingDiary = 15508;
    private static final int SelMahumTrainingTimetable = 15509;

    public _251_NoSecrets() {
        super(false);
        addStartNpc(GuardPinaps);
        addKillId(SelMahumTrainers[0], SelMahumTrainers[1], SelMahumTrainers[2], SelMahumTrainers[3], SelMahumRecruits[0], SelMahumRecruits[1], SelMahumRecruits[2], SelMahumRecruits[3], SelMahumRecruits[4], SelMahumRecruits[5]);
        addQuestItem(SelMahumTrainingDiary, SelMahumTrainingTimetable);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("pinaps_q251_03.htm")) {
            st.setState(STARTED);
            st.setCond(1);
            st.playSound(SOUND_ACCEPT);
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String htmltext = "noquest";
        int cond = st.getCond();
        if (npc.getNpcId() == GuardPinaps)
            if (cond == 0) {
                if (st.getPlayer().getLevel() >= 82)
                    htmltext = "pinaps_q251_01.htm";
                else
                    htmltext = "pinaps_q251_00.htm";
            } else if (cond == 1)
                htmltext = "pinaps_q251_04.htm";
            else if (cond == 2) {
                st.takeAllItems(SelMahumTrainingDiary, SelMahumTrainingTimetable);
                htmltext = "pinaps_q251_05.htm";
                st.setState(COMPLETED);
                st.giveItems(57, 313355);
                st.addExpAndSp(56787, 160578);
                st.playSound(SOUND_FINISH);
                st.exitCurrentQuest(false);
            }

        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        if (cond == 1) {
            if (st.getQuestItemsCount(SelMahumTrainingDiary) < 10 && ArrayUtils.contains(SelMahumTrainers, npc.getNpcId()))
                st.rollAndGive(SelMahumTrainingDiary, 1, 40);
            else if (st.getQuestItemsCount(SelMahumTrainingTimetable) < 5 && ArrayUtils.contains(SelMahumRecruits, npc.getNpcId()))
                st.rollAndGive(SelMahumTrainingTimetable, 1, 25);

            if (st.getQuestItemsCount(SelMahumTrainingDiary) >= 10 && st.getQuestItemsCount(SelMahumTrainingTimetable) >= 5)
                st.setCond(2);
        }
        return null;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }
}