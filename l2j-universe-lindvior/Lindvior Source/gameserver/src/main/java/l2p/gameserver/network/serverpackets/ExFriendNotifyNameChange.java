/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

public class ExFriendNotifyNameChange extends L2GameServerPacket {
    public ExFriendNotifyNameChange() {
        //
    }

    @Override
    protected final void writeImpl() {
        writeEx(0xF1);
        //TODO: [K1mel]
    }
}
