/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

public class ExChangeClientEffectInfo extends L2GameServerPacket {
    private int _state;

    public ExChangeClientEffectInfo(int state) {
        _state = state;
    }

    @Override
    protected void writeImpl() {
        writeEx(0xC3);
        writeD(0);
        writeD(_state);
    }
}
