package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.Player;
import l2p.gameserver.model.entity.boat.Boat;
import l2p.gameserver.utils.Location;

public class GetOffVehicle extends L2GameServerPacket {
    private int _playerObjectId, _boatObjectId;
    private Location _loc;

    public GetOffVehicle(Player cha, Boat boat, Location loc) {
        _playerObjectId = cha.getObjectId();
        _boatObjectId = boat.getBoatId();
        _loc = loc;
    }

    @Override
    protected final void writeImpl() {
        writeC(0x6f);
        writeD(_playerObjectId);
        writeD(_boatObjectId);
        writeD(_loc.x);
        writeD(_loc.y);
        writeD(_loc.z);
    }
}