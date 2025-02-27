/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package npc.model;

import l2p.gameserver.Config;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.base.ClassId;
import l2p.gameserver.model.base.ClassLevel;
import l2p.gameserver.model.instances.AwakeningManagerInstance;
import l2p.gameserver.model.instances.MerchantInstance;
import l2p.gameserver.templates.npc.NpcTemplate;
import l2p.gameserver.utils.HtmlUtils;
import l2p.gameserver.utils.ItemFunctions;

import java.util.StringTokenizer;


@SuppressWarnings("serial")
public final class ClassMasterInstance extends MerchantInstance {
    private static final long serialVersionUID = 1L;

    public ClassMasterInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public String getHtmlPath(int npcId, int val, Player player) {
        String pom;
        if (val == 0)
            pom = "" + npcId;
        else
            pom = npcId + "-" + val;

        return "custom/" + pom + ".htm";
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (!canBypassCheck(player, this))
            return;

        StringTokenizer st = new StringTokenizer(command);
        String cmd = st.nextToken();
        if (cmd.equals("change_class_list")) {
            ClassLevel classLevel = player.getClassId().getClassLevel();
            if (classLevel == ClassLevel.AWAKED) {
                showChatWindow(player, "custom/" + getNpcId() + "-no_last_class.htm");
                return;
            }

            int newClassLvl = player.getClassLevel() + 1;
            if (!Config.ALLOW_CLASS_MASTERS_LIST.containsKey(newClassLvl)) {
                showChatWindow(player, "custom/" + getNpcId() + "-no_class_lvl_" + newClassLvl + ".htm");
                return;
            }

            if (!checkMinLvl(player)) {
                showChatWindow(player, "custom/" + getNpcId() + "-no_player_lvl_" + newClassLvl + ".htm");
                return;
            }

            int[] pay = Config.ALLOW_CLASS_MASTERS_LIST.get(newClassLvl);
            int payItemId = 0;
            int payItemCount = 0;
            if (pay.length >= 2) {
                payItemId = pay[0];
                payItemCount = pay[1];
            }

            if (classLevel == ClassLevel.THIRD) {
                if (payItemId > 0 && payItemCount > 0) {
                    showChatWindow(player, "custom/" + getNpcId() + "-awakening_pay.htm", "<?PAY_ITEM?>", HtmlUtils.htmlItemName(payItemId), "<?PAY_ITEM_COUNT?>", String.valueOf(payItemCount));
                    return;
                }
                showChatWindow(player, "custom/" + getNpcId() + "-awakening.htm");
            } else {
                String availClassList = generateAvailClassList(player.getClassId());
                if (payItemId > 0 && payItemCount > 0) {
                    showChatWindow(player, "custom/" + getNpcId() + "-class_list_pay.htm", "<?AVAIL_CLASS_LIST?>", availClassList, "<?PAY_ITEM?>", HtmlUtils.htmlItemName(payItemId), "<?PAY_ITEM_COUNT?>", String.valueOf(payItemCount));
                    return;
                }
                showChatWindow(player, "custom/" + getNpcId() + "-class_list.htm", "<?AVAIL_CLASS_LIST?>", availClassList);
            }
        } else if (cmd.equals("change_class")) {
            int val = Integer.parseInt(st.nextToken());
            ClassId classId = ClassId.VALUES[val];
            int newClassLvl = classId.getClassLevel().ordinal();
            if (classId == ClassId.INSPECTOR || !classId.childOf(player.getClassId()) || newClassLvl != player.getClassLevel() + 1)
                return;

            if (!Config.ALLOW_CLASS_MASTERS_LIST.containsKey(newClassLvl))
                return;

            if (!checkMinLvl(player))
                return;

            int[] pay = Config.ALLOW_CLASS_MASTERS_LIST.get(newClassLvl);
            if (pay.length >= 2) {
                int payItemId = pay[0];
                int payItemCount = pay[1];
                long notEnoughItemCount = payItemCount - ItemFunctions.getItemCount(player, payItemId);
                if (notEnoughItemCount > 0) {
                    showChatWindow(player, "custom/" + getNpcId() + "-no_item.htm", "<?PAY_ITEM?>", HtmlUtils.htmlItemName(payItemId), "<?NOT_ENOUGH_ITEM_COUNT?>", String.valueOf(notEnoughItemCount));
                    return;
                }
                ItemFunctions.removeItem(player, payItemId, payItemCount, true);
            }

            player.setClassId(val, false);
            player.broadcastUserInfo(true);
            showChatWindow(player, "custom/" + getNpcId() + "-class_changed.htm", "<?CLASS_NAME?>", HtmlUtils.htmlClassName(val));
        } else if (cmd.equals("awake")) {
            int newClassLvl = ClassLevel.AWAKED.ordinal();
            if (!Config.ALLOW_CLASS_MASTERS_LIST.containsKey(newClassLvl))
                return;

            if (!checkMinLvl(player))
                return;

            int[] pay = Config.ALLOW_CLASS_MASTERS_LIST.get(newClassLvl);
            if (pay.length >= 2) {
                int payItemId = pay[0];
                int payItemCount = pay[1];
                long notEnoughItemCount = payItemCount - ItemFunctions.getItemCount(player, payItemId);
                if (notEnoughItemCount > 0) {
                    showChatWindow(player, "custom/" + getNpcId() + "-no_item_awake.htm", "<?PAY_ITEM?>", HtmlUtils.htmlItemName(payItemId), "<?NOT_ENOUGH_ITEM_COUNT?>", String.valueOf(notEnoughItemCount));
                    return;
                }
                ItemFunctions.removeItem(player, payItemId, payItemCount, true);
            }

            ItemFunctions.addItem(player, AwakeningManagerInstance.SCROLL_OF_AFTERLIFE, 1, true);
            player.teleToLocation(AwakeningManagerInstance.TELEPORT_LOC);
            return;
        } else
            super.onBypassFeedback(player, command);
    }

    private String generateAvailClassList(ClassId classId) {
        StringBuilder classList = new StringBuilder();
        for (ClassId cid : ClassId.VALUES) {
            // Инспектор является наследником trooper и warder, но сменить его как профессию нельзя,
            // т.к. это сабкласс. Наследуется с целью получения скилов родителей.
            if (cid == ClassId.INSPECTOR)
                continue;

            if (cid.childOf(classId) && cid.getClassLevel().ordinal() == classId.getClassLevel().ordinal() + 1)
                classList.append("<a action=\"bypass -h npc_").append(getObjectId()).append("_change_class ").append(cid.getId()).append("\">").append(HtmlUtils.htmlClassName(cid.getId())).append("</a><br>");
        }
        return classList.toString();
    }

    private static boolean checkMinLvl(Player player) {
        ClassLevel classLvl = player.getClassId().getClassLevel();
        int level = player.getLevel();
        switch (classLvl) {
            case NONE:
                if (level < 20)
                    return false;
                break;
            case FIRST:
                if (level < 40)
                    return false;
                break;
            case SECOND:
                if (level < 76)
                    return false;
                break;
            case THIRD:
                if (level < 85)
                    return false;
                break;
        }
        return true;
    }
}