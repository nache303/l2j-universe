package npc.model.residences.castle;

import l2p.commons.threading.RunnableImpl;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.World;
import l2p.gameserver.model.entity.events.impl.CastleSiegeEvent;
import l2p.gameserver.model.entity.events.impl.SiegeEvent;
import l2p.gameserver.model.entity.events.objects.SiegeToggleNpcObject;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.network.serverpackets.components.NpcString;
import l2p.gameserver.scripts.Functions;
import l2p.gameserver.templates.npc.NpcTemplate;
import l2p.gameserver.utils.Location;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author VISTALL
 * @date 17:46/12.07.2011
 */
public class CastleMassTeleporterInstance extends NpcInstance {
    private static final long serialVersionUID = 1L;

    private class TeleportTask extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            Functions.npcShout(CastleMassTeleporterInstance.this, NpcString.THE_DEFENDERS_OF_S1_CASTLE_WILL_BE_TELEPORTED_TO_THE_INNER_CASTLE, "#" + getCastle().getNpcStringName().getId());

            for (Player p : World.getAroundPlayers(CastleMassTeleporterInstance.this, 200, 50))
                p.teleToLocation(Location.findPointToStay(_teleportLoc, 10, 100, p.getGeoIndex()));

            _teleportTask = null;
        }
    }

    private Future<?> _teleportTask = null;
    private Location _teleportLoc;

    public CastleMassTeleporterInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
        _teleportLoc = Location.parseLoc(template.getAIParams().getString("teleport_loc"));
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (!canBypassCheck(player, this))
            return;

        if (_teleportTask != null) {
            showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm");
            return;
        }

        _teleportTask = ThreadPoolManager.getInstance().schedule(new TeleportTask(), isAllTowersDead() ? 480000L : 30000L);

        showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm");
    }

    @Override
    public void showChatWindow(Player player, int val, Object... arg) {
        if (_teleportTask != null)
            showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm");
        else {
            if (isAllTowersDead())
                showChatWindow(player, "residence2/castle/gludio_mass_teleporter002.htm");
            else
                showChatWindow(player, "residence2/castle/gludio_mass_teleporter001.htm");
        }
    }

    private boolean isAllTowersDead() {
        SiegeEvent siegeEvent = getEvent(SiegeEvent.class);
        if (siegeEvent == null || !siegeEvent.isInProgress())
            return false;

        List<SiegeToggleNpcObject> towers = siegeEvent.getObjects(CastleSiegeEvent.CONTROL_TOWERS);
        for (SiegeToggleNpcObject t : towers)
            if (t.isAlive())
                return false;

        return true;
    }
}
