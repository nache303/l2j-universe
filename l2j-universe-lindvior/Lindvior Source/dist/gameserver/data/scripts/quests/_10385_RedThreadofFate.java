/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package quests;

import l2p.gameserver.ai.CtrlEvent;
import l2p.gameserver.listener.actor.OnMagicUseListener;
import l2p.gameserver.listener.actor.player.OnSocialActionListener;
import l2p.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2p.gameserver.model.*;
import l2p.gameserver.model.actor.listener.CharListenerList;
import l2p.gameserver.model.entity.Reflection;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.quest.Quest;
import l2p.gameserver.model.quest.QuestState;
import l2p.gameserver.network.clientpackets.RequestActionUse.Action;
import l2p.gameserver.network.serverpackets.ExShowScreenMessage;
import l2p.gameserver.network.serverpackets.components.NpcString;
import l2p.gameserver.network.serverpackets.components.SceneMovie;
import l2p.gameserver.scripts.ScriptFile;
import l2p.gameserver.utils.ItemFunctions;
import l2p.gameserver.utils.Location;
import l2p.gameserver.utils.NpcUtils;
import l2p.gameserver.utils.ReflectionUtils;

public class _10385_RedThreadofFate extends Quest
        implements ScriptFile, OnSocialActionListener, OnMagicUseListener {
    public static final int INSTANCE_ID = 241;
    //NPC
    private static final int Rean = 33491;
    private static final int Morelin = 30925;
    private static final int Lania = 33783;
    private static final int HeineWaterSource = 33784;
    private static final int Ladyofthelike = 31745;
    private static final int Nerupa = 30370;
    private static final int Innocentin = 31328;
    private static final int Enfeux = 31519;
    private static final int Vulkan = 31539;
    private static final int Urn = 31149;
    private static final int Wesley = 30166;
    private static final int DesertedDwarvenHouse = 33788;
    private static final int PaagrioTemple = 33787;
    private static final int AltarofShilen = 33785;
    private static final int ShilensMessenger = 27492;  //Kill me
    private static final int CaveofSouls = 33789;
    private static final int MotherTree = 33786;
    private static final int Darin = 33748;
    private static final int Roxxy = 33749;
    private static final int BiotinHighPriest = 30031;
    private static final int MysteriousDarkKnight = 33751;
    //Items
    private static final int MysteriosLetter = 36072;
    private static final int Waterfromthegardenofeva = 36066;
    private static final int Clearestwater = 36067;
    private static final int Brightestlight = 36068;
    private static final int Purestsoul = 36069;
    private static final int Vulkangold = 36113;
    private static final int Vulkansilver = 36114;
    private static final int Vulkanfire = 36115;
    private static final int Fiercestflame = 36070;
    private static final int Fondestheart = 36071;
    private static final int SOEDwarvenvillage = 20376;
    private static final int DimensionalDiamond = 7562;
    //Skills
    private static final int water = 9579;
    private static final int light = 9580;
    private static final int soul = 9581;
    private static final int flame = 9582;
    private static final int love = 9583;

    private ZoneListener _zoneListener;

    public _10385_RedThreadofFate() {
        super(false);

        addStartNpc(Rean);
        addTalkId(Morelin, Lania, HeineWaterSource, Ladyofthelike, Nerupa, Innocentin, Enfeux, Vulkan, Urn, Wesley, DesertedDwarvenHouse, PaagrioTemple, AltarofShilen, ShilensMessenger, CaveofSouls, MotherTree, Darin, Roxxy, BiotinHighPriest, MysteriousDarkKnight);

        addQuestItem(MysteriosLetter, Waterfromthegardenofeva, Clearestwater, Brightestlight, Purestsoul, Vulkangold, Vulkansilver, Vulkanfire, Fiercestflame, Fondestheart, SOEDwarvenvillage, DimensionalDiamond);

        addKillId(ShilensMessenger);

        addLevelCheck(85, 99);
        addQuestCompletedCheck(_10338_SeizeYourDestiny.class);

        _zoneListener = new ZoneListener();
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onLoad() {
        CharListenerList.addGlobal(this);
    }

    @Override
    public void onReload() {
        CharListenerList.removeGlobal(this);
        CharListenerList.addGlobal(this);
    }

    @Override
    public void onMagicUse(Creature actor, Skill skill, Creature target, boolean alt) {
        if (actor == null || !actor.isPlayer() || target == null || !target.isNpc()) {
            return;
        }
        QuestState st = ((Player) actor).getQuestState(_10385_RedThreadofFate.class);

        if (st == null)
            return;
        NpcInstance npc = (NpcInstance) target;
        Player player = st.getPlayer();
        int cond = st.getCond();
        int npcId = npc.getNpcId();
        switch (skill.getId()) {
            case water:
                if (npcId == MotherTree && cond == 18) {
                    ItemFunctions.removeItem(st.getPlayer(), Clearestwater, 1, true);
                    enterInstance(st.getPlayer());
                    st.setCond(19);
                }
                break;
            case light:
                if (npcId == AltarofShilen && cond == 16) {
                    ItemFunctions.removeItem(st.getPlayer(), Brightestlight, 1, true);
                    player.sendPacket(new ExShowScreenMessage(NpcString.YOU_MUST_DESTROY_THE_MESSENGER_SHILEN, 4500, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, new String[0]));
                    NpcInstance mob = st.addSpawn(ShilensMessenger, 28760, 11032, -4252);
                    mob.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, player, 100000);
                }
                break;
            case soul:
                if (npcId == CaveofSouls && cond == 17) {
                    ItemFunctions.removeItem(st.getPlayer(), Purestsoul, 1, true);
                    st.setCond(18);
                }
                break;
            case flame:
                if (npcId == PaagrioTemple && cond == 15) {
                    ItemFunctions.removeItem(st.getPlayer(), Fiercestflame, 1, true);
                    st.setCond(16);
                }
                break;
            case love:
                if (npcId == DesertedDwarvenHouse && cond == 14) {
                    ItemFunctions.removeItem(st.getPlayer(), Fondestheart, 1, true);
                    st.setCond(15);
                }
                break;
        }
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();
        int cond = st.getCond();

        Player player = st.getPlayer();
        if (player == null) {
            return null;
        }
        if (npcId == ShilensMessenger && cond == 16) {
            st.setCond(17);
            st.playSound("ItemSound.quest_itemget");
        }

        return null;
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        Player player = st.getPlayer();
        String htmltext = event;
        int cond = st.getCond();
        if (cond == 0 && event.equalsIgnoreCase("Rean_q10385_03.htm")) {
            st.setState(2);
            st.setCond(1);
            st.giveItems(MysteriosLetter, 1);
            st.playSound("ItemSound.quest_accept");
        } else if (cond == 1 && event.equalsIgnoreCase("Morelin_q10385_02.htm")) {
            st.setCond(2);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 2 && event.equalsIgnoreCase("Lania_q10385_02.htm")) {
            st.setCond(3);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 6 && event.equalsIgnoreCase("Ladyofthelike_q10385_03.htm")) {
            st.takeItems(Waterfromthegardenofeva, 1);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 6 && event.equalsIgnoreCase("Ladyofthelike_q10385_06.htm")) {
            st.giveItems(Clearestwater, 1);
            st.setCond(7);
            player.teleToLocation(new Location(172440, 90312, -2011));
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 7 && event.equalsIgnoreCase("Nerupa_q10385_04.htm")) {
            st.setCond(8);
            st.giveItems(Brightestlight, 1);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 8 && event.equalsIgnoreCase("Enfeux_q10385_02.htm")) {
            st.giveItems(Purestsoul, 1);
            st.setCond(9);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 9 && event.equalsIgnoreCase("Innocentin_q10385_02.htm")) {
            st.setCond(10);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 10 && event.equalsIgnoreCase("Vulkan_q10385_04.htm")) {
            st.giveItems(Vulkangold, 1);
            st.giveItems(Vulkansilver, 1);
            st.giveItems(Vulkanfire, 1);
            st.setCond(11);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 13 && event.equalsIgnoreCase("Wesley_q10385_03.htm")) {
            player.teleToLocation(new Location(180168, -111720, -5856));
        } else if (cond == 13 && event.equalsIgnoreCase("Vulkan_q10385_08.htm")) {
            st.giveItems(Fiercestflame, 1);
            st.giveItems(Fondestheart, 1);
            st.giveItems(SOEDwarvenvillage, 1);
            st.setCond(14);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 11 && event.equalsIgnoreCase("Urn_q10385_02.htm")) {
            st.takeItems(Vulkangold, 1);
            st.takeItems(Vulkansilver, 1);
            st.takeItems(Vulkanfire, 1);
            st.setCond(12);
            st.playSound("ItemSound.quest_middle");
        } else if (cond == 19 && htmltext.equalsIgnoreCase("Darin_q10385_03.htm")) {
            player.sendPacket(new ExShowScreenMessage(NpcString.TALK_TO_ROXXIE, 4500, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, new String[0]));
        }
        if (cond == 19 && event.equalsIgnoreCase("Roxxy_q10385_02.htm")) {
            st.setCond(20);
            st.playSound("ItemSound.quest_middle");
        }

        if (cond == 20 && event.equalsIgnoreCase("Biotin_q10385_03.htm")) {
            player.sendPacket(new ExShowScreenMessage(NpcString.GET_OUT_OF_THE_TEMPLE, 4500, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, new String[0]));
            st.setCond(21);
            st.playSound("ItemSound.quest_middle");
        }
        if (cond == 21 && event.equalsIgnoreCase("showMovie")) {
            st.getPlayer().showQuestMovie(SceneMovie.sc_sub_quest);
            st.startQuestTimer("timer1", 25000);
            htmltext = null;
        } else if (event.equalsIgnoreCase("timer1")) {
            st.setCond(22);
            st.cancelQuestTimer("timer1");
            player.teleToLocation(-113656, 246040, -3724, 0);
            htmltext = null;
        } else if (cond == 22 && event.equalsIgnoreCase("Rean_q10385_05.htm")) {
            st.giveItems(DimensionalDiamond, 40);
            st.setState(3);
            st.exitCurrentQuest(false);
            st.playSound("ItemSound.quest_finish");
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        int npcId = npc.getNpcId();
        String htmltext = "noquest";
        switch (npcId) {
            case Rean:
                if (cond == 22)
                    htmltext = "Rean_q10385_04.htm";
                else if (cond == 0)
                    htmltext = "Rean_q10385_01.htm";
                else if (cond > 22)
                    htmltext = "Rean_q10385_0.htm";
                break;
            case Morelin:
                if (cond == 1)
                    htmltext = "Morelin_q10385_01.htm";
                break;
            case Lania:
                if (cond == 4) {
                    htmltext = "Lania_q10385_03.htm";
                    st.setCond(5);
                    st.playSound("ItemSound.quest_middle");
                } else if (cond == 2) {
                    htmltext = "Lania_q10385_01.htm";
                } else if (cond == 3) {
                    htmltext = "Lania_q10385_02.htm";
                }
                break;
            case HeineWaterSource:
                if (cond == 5) {
                    htmltext = "HeineWaterSource_q10385_01.htm";
                    st.giveItems(Waterfromthegardenofeva, 1);
                    st.setCond(6);
                    st.playSound("ItemSound.quest_middle");
                }
                break;
            case Ladyofthelike:
                if (cond == 6)
                    htmltext = "Ladyofthelike_q10385_01.htm";
                break;
            case Nerupa:
                if (cond == 7)
                    htmltext = "Nerupa_q10385_01.htm";
                break;
            case Enfeux:
                if (cond == 8)
                    htmltext = "Enfeux_q10385_01.htm";
                break;
            case Innocentin:
                if (cond == 9)
                    htmltext = "Innocentin_q10385_01.htm";
                break;
            case Vulkan:
                if (cond == 10)
                    htmltext = "Vulkan_q10385_01.htm";
                else if (cond == 13)
                    htmltext = "Vulkan_q10385_05.htm";
                break;
            case Urn:
                if (cond == 11)
                    htmltext = "Urn_q10385_01.htm";
                break;
            case Wesley:
                if (cond == 12) {
                    htmltext = "Wesley_q10385_01.htm";
                    st.setCond(13);
                    st.playSound("ItemSound.quest_middle");
                }
                break;
            case Darin:
                if (cond == 19)
                    htmltext = "Darin_q10385_01.htm";
                break;
            case Roxxy:
                if (cond == 19)
                    htmltext = "Roxxy_q10385_01.htm";
                break;
            case BiotinHighPriest:
                if (cond == 20) {
                    htmltext = "Biotin_q10385_01.htm";
                }
                break;
        }
        return htmltext;
    }

    private void enterInstance(Player player) {
        Reflection r = player.getActiveReflection();
        if (r != null) {
            if (player.canReenterInstance(INSTANCE_ID))
                player.teleToLocation(r.getTeleportLoc(), r);
        } else if (player.canEnterInstance(INSTANCE_ID)) {
            Reflection newInstance = ReflectionUtils.enterReflection(player, INSTANCE_ID);

            newInstance.getZone("[Q10385_EXIT_1]").addListener(this._zoneListener);
            newInstance.getZone("[Q10385_EXIT_2]").addListener(this._zoneListener);
        }
    }

    @Override
    public void onSocialAction(Player player, GameObject target, Action action) {
        QuestState st = player.getQuestState(getName());
        NpcInstance npc = (NpcInstance) target;

        int npcId = npc.getNpcId();
        int cond = st.getCond();

        if (cond == 3 && npcId == Lania && action.equals(Action.ACTION26)) {
            st.setCond(4);
            st.playSound("ItemSound.quest_middle");
        }
    }

    public class ZoneListener
            implements OnZoneEnterLeaveListener {
        public ZoneListener() {
        }

        public void onZoneEnter(Zone zone, Creature actor) {
        }

        public void onZoneLeave(Zone zone, Creature actor) {
            if (!actor.isPlayer()) {
                return;
            }
            Player player = actor.getPlayer();
            QuestState st = player.getQuestState(_10385_RedThreadofFate.class);

            if (st.getCond() == 21) {
                Location loc = zone.getName().contains("Q10385_EXIT_1") ? new Location(210632, 15576, -3754) : new Location(210632, 15576, -3754);
                if (player.getReflection().getAllByNpcId(MysteriousDarkKnight, true).isEmpty())
                    NpcUtils.spawnSingle(MysteriousDarkKnight, loc, player.getReflection());
            }
        }
    }
}
