package npc.model;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.Zone;
import l2p.gameserver.model.base.Race;
import l2p.gameserver.model.instances.WarehouseInstance;
import l2p.gameserver.network.serverpackets.MagicSkillUse;
import l2p.gameserver.network.serverpackets.SystemMessage2;
import l2p.gameserver.network.serverpackets.components.SystemMsg;
import l2p.gameserver.tables.SkillTable;
import l2p.gameserver.templates.npc.NpcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VISTALL
 * @date 17:17/07.07.2011
 */
public class ArenaManagerInstance extends WarehouseInstance {
    /**
     *
     */
    private static final long serialVersionUID = 7274583550143188321L;
    private final static int[][] _arenaBuff = new int[][]{
            // ID, warrior = 0, mage = 1, both = 2
            {6803, 0}, // Arena Haste
            {6804, 2}, // Arena Wind Walk
            {6805, 1}, // Arena Empower
            {6806, 1}, // Arena Acumen
            {6807, 1}, // Arena Concentration
            {6808, 2}, // Arena Might
            {6809, 0}, // Arena Guidance
            //{6810, 0}, // Arena Focus
            {6811, 0}, // Arena Death Whisper
            {6812, 2}, // Arena Berserker Spirit
    };

    public ArenaManagerInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (!canBypassCheck(player, this))
            return;

        if (command.equalsIgnoreCase("ArenaBuff")) {
            // Prevent a cursed weapon weilder of being buffed
            if (player.isCursedWeaponEquipped() || player.isInZone(Zone.ZoneType.battle_zone))
                return;
            int neededmoney = 2000;
            long currentmoney = player.getAdena();
            if (neededmoney > currentmoney) {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            player.reduceAdena(neededmoney, true);
            List<Creature> target = new ArrayList<Creature>();
            target.add(player);
            for (int[] buff : _arenaBuff)
                if (player.isMageClass() && player.getTemplate().getRace() != Race.orc) {
                    if (buff[1] == 1 || buff[1] == 2) {
                        broadcastPacket(new MagicSkillUse(this, player, buff[0], 1, 0, 0));
                        callSkill(SkillTable.getInstance().getInfo(buff[0], 1), target, true);
                    }
                } else if (buff[1] == 0 || buff[1] == 2) {
                    broadcastPacket(new MagicSkillUse(this, player, buff[0], 1, 0, 0));
                    callSkill(SkillTable.getInstance().getInfo(buff[0], 1), target, true);
                }
        } else if (command.equals("CPRecovery")) {
            if (player.isCursedWeaponEquipped() || player.isInZone(Zone.ZoneType.battle_zone))
                return;

            int neededmoney = 1000;
            long currentmoney = player.getAdena();
            if (neededmoney > currentmoney) {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            player.reduceAdena(neededmoney, true);
            player.setCurrentCp(player.getMaxCp());
            player.sendPacket(new SystemMessage2(SystemMsg.S1_CP_HAS_BEEN_RESTORED).addName(player));
        } else if (command.equals("HPRecovery")) {
            if (player.isCursedWeaponEquipped() || player.isInZone(Zone.ZoneType.battle_zone))
                return;

            int neededmoney = 1000;
            long currentmoney = player.getAdena();
            if (neededmoney > currentmoney) {
                player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            player.reduceAdena(neededmoney, true);
            player.setCurrentHp(player.getMaxHp(), false);
            player.sendPacket(new SystemMessage2(SystemMsg.S1_HP_HAS_BEEN_RESTORED).addName(player));
        } else
            super.onBypassFeedback(player, command);
    }
}
