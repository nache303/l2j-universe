/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network.serverpackets.company.party;

import l2p.gameserver.network.serverpackets.L2GameServerPacket;

/**
 * sample
 * <p/>
 * 4b
 * c1 b2 e0 4a
 * 00 00 00 00
 * <p/>
 * <p/>
 * format
 * cdd
 *
 * @version $Revision: 1.1.2.1.2.3 $ $Date: 2005/03/27 15:29:57 $
 */
public class AskJoinParty extends L2GameServerPacket {
    private String _requestorName;
    private int _itemDistribution;

    /**
     * @param int objectId of the target
     * @param int
     */
    public AskJoinParty(String requestorName, int itemDistribution) {
        _requestorName = requestorName;
        _itemDistribution = itemDistribution;
    }

    @Override
    protected final void writeImpl() {
        writeC(0x39);
        writeS(_requestorName);
        writeD(_itemDistribution);
    }
}