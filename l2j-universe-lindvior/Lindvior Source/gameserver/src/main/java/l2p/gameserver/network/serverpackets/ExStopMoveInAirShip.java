package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.Player;
import l2p.gameserver.utils.Location;

public class ExStopMoveInAirShip extends L2GameServerPacket {
    private int char_id, boat_id, char_heading;
    private Location _loc;

    public ExStopMoveInAirShip(Player cha) {
        char_id = cha.getObjectId();
        boat_id = cha.getBoat().getBoatId();
        _loc = cha.getInBoatPosition();
        char_heading = cha.getHeading();
    }

    @Override
    protected final void writeImpl() {
        writeEx449(0x6E);

        writeD(char_id);
        writeD(boat_id);
        writeD(_loc.x);
        writeD(_loc.y);
        writeD(_loc.z);
        writeD(char_heading);
    }
}