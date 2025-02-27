package quests;

import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.ScriptFile;

/**
 * @author: pchayka
 * @date: 22.06.2010
 */
public class _699_GuardianoftheSkies extends Quest implements ScriptFile {
    // NPC's
    private static int Lekon = 32557;

    // ITEMS
    private static int VulturesGoldenFeather = 13871;

    // MOB's
    private static int VultureRider1 = 22614;
    private static int VultureRider2 = 22615;
    private static int EliteRider = 25633;
    private static int Valdstone = 25623;

    //Price
    private static int _featherprice = 5000;

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }

    public _699_GuardianoftheSkies() {
        super(false);

        addStartNpc(Lekon);
        addTalkId(Lekon);
        addKillId(VultureRider1, VultureRider2, EliteRider, Valdstone);
        addQuestItem(VulturesGoldenFeather);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        int cond = st.getCond();
        String htmltext = event;

        if (event.equals("lekon_q699_2.htm") && cond == 0) {
            st.setCond(1);
            st.setState(STARTED);
            st.playSound(SOUND_ACCEPT);
        } else if (event.equals("ex_feathers") && cond == 1)
            if (st.getQuestItemsCount(VulturesGoldenFeather) >= 1) {
                st.giveItems(ADENA_ID, st.getQuestItemsCount(VulturesGoldenFeather) * _featherprice);
                st.takeItems(VulturesGoldenFeather, -1);
                htmltext = "lekon_q699_4.htm";
            } else
                htmltext = "lekon_q699_3a.htm";
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String htmltext = "noquest";
        int npcId = npc.getNpcId();
        int cond = st.getCond();

        QuestState GoodDayToFly = st.getPlayer().getQuestState(_10273_GoodDayToFly.class);
        if (npcId == Lekon)
            if (cond == 0) {
                if (st.getPlayer().getLevel() >= 75 && GoodDayToFly != null && GoodDayToFly.isCompleted())
                    htmltext = "lekon_q699_1.htm";
                else {
                    htmltext = "lekon_q699_0.htm";
                    st.exitCurrentQuest(true);
                }
            } else if (cond == 1)
                if (st.getQuestItemsCount(VulturesGoldenFeather) >= 1)
                    htmltext = "lekon_q699_3.htm";
                else
                    htmltext = "lekon_q699_3a.htm";
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();
        int cond = st.getCond();
        if (cond == 1)
            if (npcId == VultureRider1 || npcId == VultureRider2 || npcId == EliteRider) {
                st.giveItems(VulturesGoldenFeather, 1);
                st.playSound(SOUND_ITEMGET);
            } else if (npcId == Valdstone) {
                st.giveItems(VulturesGoldenFeather, 50);
                st.playSound(SOUND_ITEMGET);
            }
        return null;
    }
}