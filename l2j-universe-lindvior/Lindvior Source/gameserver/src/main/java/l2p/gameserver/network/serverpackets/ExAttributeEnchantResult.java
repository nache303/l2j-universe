/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

/**
 * @author SYS
 */
public class ExAttributeEnchantResult extends L2GameServerPacket {
    private int _result;

    public ExAttributeEnchantResult(int unknown) {
        _result = unknown;
    }

    @Override
    protected final void writeImpl() {
        writeEx(0x62);
        writeD(_result);
    }
}