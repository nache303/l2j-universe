/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

public class ExBlockUpSetState extends L2GameServerPacket {
    private int BlockUpStateType = 0; //TODO

    @Override
    protected void writeImpl() {
        writeEx(0x99);
        writeD(BlockUpStateType);
        switch (BlockUpStateType) {
            case 0:
                //dddddd
                break;
            case 1:
                //dd
                break;
            case 2:
                //ddd
                break;
        }
    }
}