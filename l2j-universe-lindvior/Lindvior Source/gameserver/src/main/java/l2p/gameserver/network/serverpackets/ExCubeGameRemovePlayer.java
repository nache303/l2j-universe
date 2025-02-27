/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.Player;

/**
 * Format: (chd) ddd
 * d: always -1
 * d: player team
 * d: player object id
 */
public class ExCubeGameRemovePlayer extends L2GameServerPacket {
    private int _objectId;
    private boolean _isRedTeam;

    public ExCubeGameRemovePlayer(Player player, boolean isRedTeam) {
        _objectId = player.getObjectId();
        _isRedTeam = isRedTeam;
    }

    @Override
    protected void writeImpl() {
        writeEx(0x98);
        writeD(0x02);

        writeD(0xffffffff);

        writeD(_isRedTeam ? 0x01 : 0x00);
        writeD(_objectId);
    }
}