package l2p.gameserver.handler.voicecommands.impl;

import l2p.gameserver.data.htm.HtmCache;
import l2p.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.World;
import l2p.gameserver.model.base.Experience;
import l2p.gameserver.network.serverpackets.RadarControl;
import l2p.gameserver.network.serverpackets.components.CustomMessage;
import l2p.gameserver.scripts.Functions;

/**
 * @Author: Abaddon
 */
public class Help extends Functions implements IVoicedCommandHandler {
    private String[] _commandList = new String[]{"help", "exp", "whereis"};

    @Override
    public boolean useVoicedCommand(String command, Player activeChar, String args) {
        command = command.intern();
        if (command.equalsIgnoreCase("help"))
            return help(command, activeChar, args);
        if (command.equalsIgnoreCase("whereis"))
            return whereis(command, activeChar, args);
        if (command.equalsIgnoreCase("exp"))
            return exp(command, activeChar, args);

        return false;
    }

    private boolean exp(String command, Player activeChar, String args) {
        if (activeChar.getLevel() >= (activeChar.isBaseClassActive() ? Experience.getMaxSubLevel() : Experience.getMaxLevel()))
            activeChar.sendMessage(new CustomMessage("voicedcommandhandlers.Help.MaxLevel", activeChar));
        else {
            long exp = Experience.LEVEL[activeChar.getLevel() + 1] - activeChar.getExp();
            activeChar.sendMessage(new CustomMessage("voicedcommandhandlers.Help.ExpLeft", activeChar).addNumber(exp));
        }
        return true;
    }

    private boolean whereis(String command, Player activeChar, String args) {
        Player friend = World.getPlayer(args);
        if (friend == null)
            return false;

        if (friend.getParty() == activeChar.getParty() || friend.getClan() == activeChar.getClan()) {
            RadarControl rc = new RadarControl(0, 1, friend.getLoc());
            activeChar.sendPacket(rc);
            return true;
        }

        return false;
    }

    private boolean help(String command, Player activeChar, String args) {
        String dialog = HtmCache.getInstance().getNotNull("command/help.htm", activeChar);
        show(dialog, activeChar);
        return true;
    }

    @Override
    public String[] getVoicedCommandList() {
        return _commandList;
    }
}