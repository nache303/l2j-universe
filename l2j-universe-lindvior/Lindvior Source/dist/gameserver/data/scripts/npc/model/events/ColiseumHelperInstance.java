package npc.model.events;

import l2p.commons.util.Rnd;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.templates.npc.NpcTemplate;
import l2p.gameserver.utils.Location;
import services.TeleToFantasyIsle;

/**
 * @author VISTALL
 * @date 23:26/16.06.2011
 */
public class ColiseumHelperInstance extends NpcInstance {
    /**
     *
     */
    private static final long serialVersionUID = 7467965714799578207L;
    private Location[][] LOCS = new Location[][]{
            {new Location(-84451, -45452, -10728), new Location(-84580, -45587, -10728)},
            {new Location(-86154, -50429, -10728), new Location(-86118, -50624, -10728)},
            {new Location(-82009, -53652, -10728), new Location(-81802, -53665, -10728)},
            {new Location(-77603, -50673, -10728), new Location(-77586, -50503, -10728)},
            {new Location(-79186, -45644, -10728), new Location(-79309, -45561, -10728)}};

    public ColiseumHelperInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (!canBypassCheck(player, this))
            return;

        if (command.equals("teleOut"))
            player.teleToLocation(TeleToFantasyIsle.POINTS[Rnd.get(TeleToFantasyIsle.POINTS.length)]);
        else if (command.startsWith("coliseum")) {
            int a = Integer.parseInt(String.valueOf(command.charAt(9)));
            Location[] locs = LOCS[a];

            player.teleToLocation(locs[Rnd.get(locs.length)]);
        } else
            super.onBypassFeedback(player, command);
    }

    @Override
    public void showChatWindow(Player player, int val, Object... arg) {
        showChatWindow(player, "events/guide_gcol001.htm");
    }
}
