/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package quests;

import l2p.gameserver.model.Player;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;

public class _10371_GraspThyPower extends Quest implements ScriptFile {

    private static final int gerkenshtein = 33648;

    private static final int[] classesav = {
            88,
            89,
            90,
            91,
            92,
            93,
            94,
            95,
            96,
            97,
            98,
            99,
            100,
            101,
            102,
            103,
            104,
            105,
            106,
            107,
            108,
            109,
            110,
            111,
            112,
            113,
            114,
            115,
            116,
            117,
            118,
            136,
            135,
            134,
            132,
            133};

    // Mobs to hunt
    private static final int Soldier = 23181;
    private static final int Warrior = 23182;
    private static final int Archer = 23183;
    private static final int Shaman = 23184;
    private static final int Bloody = 23185;

    private static final String Soldier_item = "Soldier";
    private static final String Warrior_item = "Warrior";
    private static final String Archer_item = "Archer";
    private static final String Shaman_item = "Shaman";
    private static final String Bloody_item = "Bloody";

    @Override
    public void onLoad() {
    }

    @Override
    public void onReload() {
    }

    @Override
    public void onShutdown() {
    }

    public _10371_GraspThyPower() {
        super(false);
        addStartNpc(gerkenshtein);
        addTalkId(gerkenshtein);

        addKillNpcWithLog(1, Soldier_item, 12, Soldier);
        addKillNpcWithLog(1, Warrior_item, 12, Warrior);
        addKillNpcWithLog(1, Archer_item, 8, Archer);
        addKillNpcWithLog(1, Shaman_item, 8, Shaman);
        addKillNpcWithLog(1, Bloody_item, 5, Bloody);

        addLevelCheck(76, 81);
        addQuestCompletedCheck(_10370_MenacingTimes.class);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("quest_ac")) {
            st.setState(STARTED);
            st.setCond(1);
            st.playSound(SOUND_ACCEPT);
            htmltext = "0-4.htm";
        } else if (event.equalsIgnoreCase("quest_rev")) {
            htmltext = "0-8.htm";
            st.getPlayer().addExpAndSp(22641900, 25729500);
            st.giveItems(57, 484990);
            st.exitCurrentQuest(false);
            st.playSound(SOUND_FINISH);
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        int npcId = npc.getNpcId();
        Player player = st.getPlayer();
        int classid = player.getClassId().getId();
        String htmltext = "noquest";

        if (npcId == gerkenshtein)
            if (st.isCompleted())
                htmltext = "0-c.htm";
            else if (cond == 0) {
                if (isAvailableFor(st.getPlayer()) && ArrayUtils.contains(classesav, classid))
                    htmltext = "start.htm";
                else
                    htmltext = TODO_FIND_HTML;
            } else if (cond == 1)
                htmltext = "0-5.htm";
            else if (cond == 2)
                htmltext = "0-6.htm";
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        boolean doneKill = updateKill(npc, st);

        if (doneKill) {
            st.unset(Soldier_item);
            st.unset(Shaman_item);
            st.unset(Warrior_item);
            st.unset(Bloody_item);
            st.unset(Archer_item);
            st.setCond(2);
        }
        return null;
    }
}