package l2p.gameserver.network.clientpackets;

import l2p.gameserver.model.Player;
import l2p.gameserver.model.entity.boat.Boat;
import l2p.gameserver.utils.Location;

// format: cddddd
public class CannotMoveAnymoreInVehicle extends L2GameClientPacket {
    private Location _loc = new Location();
    private int _boatid;

    @Override
    protected void readImpl() {
        _boatid = readD();
        _loc.x = readD();
        _loc.y = readD();
        _loc.z = readD();
        _loc.h = readD();
    }

    @Override
    protected void runImpl() {
        Player player = getClient().getActiveChar();
        if (player == null)
            return;

        Boat boat = player.getBoat();
        if (boat != null && boat.getBoatId() == _boatid) {
            player.setInBoatPosition(_loc);
            player.setHeading(_loc.h);
            player.broadcastPacket(boat.inStopMovePacket(player));
        }
    }
}