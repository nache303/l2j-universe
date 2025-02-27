package l2p.gameserver.network.clientpackets;

import l2p.gameserver.model.Player;

/**
 * Format: (c) S
 * S: pledge name?
 */
public class RequestPledgeExtendedInfo extends L2GameClientPacket {
    @SuppressWarnings("unused")
    private String _name;

    @Override
    protected void readImpl() {
        _name = readS(16);
    }

    @Override
    protected void runImpl() {
        Player activeChar = getClient().getActiveChar();
        if (activeChar == null)
            return;
        if (activeChar.isGM())
            activeChar.sendMessage("RequestPledgeExtendedInfo");

        // TODO this
    }
}