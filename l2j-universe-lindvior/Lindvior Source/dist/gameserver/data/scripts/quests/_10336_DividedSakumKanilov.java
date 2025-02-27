/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package quests;

import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.ScriptFile;

public class _10336_DividedSakumKanilov extends Quest implements ScriptFile {

    private static final int guild = 31795;
    private static final int jena = 33509;
    private static final int kanilov = 27451;
    private int killedkanilov;

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }

    public _10336_DividedSakumKanilov() {
        super(false);
        addStartNpc(jena);
        addTalkId(jena);
        addTalkId(guild);
        addKillId(kanilov);

        addLevelCheck(27, 40);
        addQuestCompletedCheck(_10335_RequesttoFindSakum.class);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("quest_ac")) {
            st.setState(STARTED);
            st.setCond(1);
            st.playSound(SOUND_ACCEPT);
            htmltext = "0-3.htm";
        }
        if (event.equalsIgnoreCase("qet_rev")) {
            htmltext = "1-3.htm";
            st.takeAllItems(17584);
            st.getPlayer().addExpAndSp(350000, 150000);
            st.giveItems(57, 100000);
            st.giveItems(955, 3);
            st.exitCurrentQuest(false);
            st.playSound(SOUND_FINISH);
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        int npcId = npc.getNpcId();
        String htmltext = "noquest";

        if (npcId == jena) {
            if (st.isCompleted())
                htmltext = TODO_FIND_HTML;
            else if (cond == 0 && isAvailableFor(st.getPlayer()))
                htmltext = "0-1.htm";
            else if (cond == 1)
                htmltext = "0-4.htm";
            else if (cond == 2) {
                htmltext = "0-5.htm";
                st.setCond(3);
                st.giveItems(17584, 1, false);
            } else if (cond == 3)
                htmltext = "0-6.htm";
        } else if (npcId == guild)
            if (st.isCompleted())
                htmltext = "1-c.htm";
            else if (cond == 0)
                htmltext = TODO_FIND_HTML;
            else if (cond == 1)
                htmltext = TODO_FIND_HTML;
            else if (cond == 2)
                htmltext = TODO_FIND_HTML;
            else if (cond == 3)
                htmltext = "1-1.htm";
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();
        if (npcId == kanilov && st.getCond() == 1) {
            ++killedkanilov;
            if (killedkanilov >= 1) {
                st.setCond(2);
                st.playSound(SOUND_MIDDLE);
                killedkanilov = 0;
            }
        }
        return null;
    }

}
