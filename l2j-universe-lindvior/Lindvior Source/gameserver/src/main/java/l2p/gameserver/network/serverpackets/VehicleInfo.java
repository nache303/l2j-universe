package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.entity.boat.Boat;
import l2p.gameserver.utils.Location;

public class VehicleInfo extends L2GameServerPacket {
    private int _boatObjectId;
    private Location _loc;

    public VehicleInfo(Boat boat) {
        _boatObjectId = boat.getBoatId();
        _loc = boat.getLoc();
    }

    @Override
    protected final void writeImpl() {
        writeC(0x60);
        writeD(_boatObjectId);
        writeD(_loc.x);
        writeD(_loc.y);
        writeD(_loc.z);
        writeD(_loc.h);
    }
}