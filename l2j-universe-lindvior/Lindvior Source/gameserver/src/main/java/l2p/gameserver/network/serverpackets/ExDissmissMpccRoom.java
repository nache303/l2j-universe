/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

/**
 * @author VISTALL
 */
public class ExDissmissMpccRoom extends L2GameServerPacket {
    public static final L2GameServerPacket STATIC = new ExDissmissMpccRoom();

    @Override
    protected void writeImpl() {
        writeEx(0x9E);
    }
}