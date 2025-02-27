/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

public class ExAutoSoulShot extends L2GameServerPacket {
    private final int _itemId;
    private final boolean _type;

    public ExAutoSoulShot(int itemId, boolean type) {
        _itemId = itemId;
        _type = type;
    }

    @Override
    protected final void writeImpl() {
        writeEx(0x0C);

        writeD(_itemId);
        writeD(_type ? 1 : 0);
    }
}