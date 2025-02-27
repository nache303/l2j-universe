/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package quests;

import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.ScriptFile;

public class _10321_QualificationsOfTheSeeker extends Quest implements ScriptFile {
    private static final int TEODOR = 32975;
    private static final int SHENON = 32974;

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }

    public _10321_QualificationsOfTheSeeker() {
        super(false);
        addStartNpc(TEODOR);
        addTalkId(SHENON);
        addTalkId(TEODOR);

        addLevelCheck(1, 20);
        addQuestCompletedCheck(_10320_LetsGototheCentralSquare.class);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("quest_ac")) {
            st.setState(STARTED);
            st.setCond(1);
            st.playSound(SOUND_ACCEPT);
            htmltext = "0-2.htm";
        }
        if (event.equalsIgnoreCase("qet_rev")) {
            htmltext = "1-2.htm";
            st.getPlayer().addExpAndSp(40, 500);
            st.giveItems(57, 5000);
            st.playSound(SOUND_FINISH);
            st.exitCurrentQuest(false);
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        int npcId = npc.getNpcId();
        String htmltext = "noquest";

        if (npcId == TEODOR) {
            if (st.isCompleted())
                htmltext = "0-c.htm";
            else if (cond == 0 && isAvailableFor(st.getPlayer()))
                htmltext = "start.htm";
            else if (cond == 1)
                htmltext = "0-3.htm";

            else
                htmltext = "noqu.htm";
        } else if (npcId == SHENON)
            if (st.isCompleted())
                htmltext = "1-c.htm";
            else if (cond == 0)
                htmltext = "1-nc.htm";
            else if (cond == 1)
                htmltext = "1-1.htm";
        return htmltext;
    }
}