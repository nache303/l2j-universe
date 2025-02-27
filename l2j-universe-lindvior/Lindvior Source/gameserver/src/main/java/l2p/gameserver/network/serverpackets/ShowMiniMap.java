package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.Player;

public class ShowMiniMap extends L2GameServerPacket {
    private int _mapId;

    public ShowMiniMap(Player player, int mapId) {
        _mapId = mapId;
    }

    @Override
    protected final void writeImpl() {
        writeC(0xa3);
        writeD(_mapId);
        writeC(0x00);
    }
}