package l2p.gameserver.network.clientpackets;

import l2p.gameserver.model.Player;

/**
 * @author n0nam3
 * @date 22/08/2010 15:00
 */
public class RequestResetNickname extends L2GameClientPacket {
    @Override
    protected void readImpl() {
        // nothing (trigger)
    }

    @Override
    protected void runImpl() {
        final Player activeChar = getClient().getActiveChar();
        if (activeChar == null)
            return;

        if (activeChar.getTitleColor() != Player.DEFAULT_TITLE_COLOR) {
            activeChar.setTitleColor(Player.DEFAULT_TITLE_COLOR);
            activeChar.broadcastUserInfo(true);
        }
    }
}