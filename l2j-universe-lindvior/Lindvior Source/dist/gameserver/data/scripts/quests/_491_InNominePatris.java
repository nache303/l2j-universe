package quests;

import l2p.commons.util.Rnd;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;

//By Darvin la2era.ru

public class _491_InNominePatris extends Quest implements ScriptFile {
    //Шанс дропа
    private static final int chance = 50;
    //Квест итем
    private static final int Fragment = 34768;
    //Монстры
    private static final int[] mobstohunt = {23181, 23182, 23183, 23184};
    //НПСы
    private static final int sirik = 33649;

    private static final int[] classesav = {88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98,
            99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 136, 135, 134
            , 132, 133};

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }

    public _491_InNominePatris() {
        super(PARTY_ONE);
        addStartNpc(sirik);
        addTalkId(sirik);
        addKillId(mobstohunt);
        addQuestItem(Fragment);

        addLevelCheck(76, 81);

    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("quest_ac")) {
            st.setState(STARTED);
            st.setCond(1);
            st.playSound(SOUND_ACCEPT);
            htmltext = "0-4.htm";
        }
        if (event.equalsIgnoreCase("qet_rev")) {
            htmltext = "0-7.htm";
            st.takeAllItems(Fragment);
            st.exitCurrentQuest(this);
            st.playSound(SOUND_FINISH);
            if (Rnd.chance(50))
                st.getPlayer().addExpAndSp(19000000, 21328000);
            else
                st.getPlayer().addExpAndSp(14000000, 15171500);

        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        int npcId = npc.getNpcId();
        String htmltext = "noquest";
        Player player = st.getPlayer();
        int classid = player.getClassId().getId();

        if (npcId == sirik) {
            if (cond == 0 && ArrayUtils.contains(classesav, classid)) {
                if (isAvailableFor(st.getPlayer())) {
                    if (st.isNowAvailableByTime())
                        htmltext = "start.htm";
                    else
                        htmltext = "0-c.htm";
                } else
                    htmltext = "0-nc.htm";
            } else if (cond == 1)
                htmltext = "0-5.htm";
            else if (cond == 2)
                htmltext = "0-6.htm";
        }
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();

        if (st.getCond() == 1 && ArrayUtils.contains(mobstohunt, npcId) && st.getQuestItemsCount(Fragment) < 50) {
            st.rollAndGive(Fragment, 1, chance);
            st.playSound(SOUND_ITEMGET);
        }
        if (st.getQuestItemsCount(Fragment) >= 50) {
            st.setCond(2);
            st.playSound(SOUND_MIDDLE);
        }
        return null;
    }

}