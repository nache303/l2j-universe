package services;

import l2p.commons.util.Rnd;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.scripts.Functions;
import l2p.gameserver.utils.Location;

/**
 * Используется для телепорта на Fantasy Isle и обратно.
 *
 * @Author: SYS
 * @Date: 01/07/2008
 */
public class TeleToFantasyIsle extends Functions {
    public static final Location[] POINTS = {
            new Location(-60695, -56896, -2032),
            new Location(-59716, -55920, -2032),
            new Location(-58752, -56896, -2032),
            new Location(-59716, -57864, -2032)};

    public void toFantasyIsle() {
        Player player = getSelf();

        if (!NpcInstance.canBypassCheck(player, player.getLastNpc()))
            return;

        player.setVar("backCoords", player.getLoc().toXYZString(), -1);
        player.teleToLocation(POINTS[Rnd.get(POINTS.length)]);
    }

    public void fromFantasyIsle() {
        Player player = getSelf();
        NpcInstance npc = getNpc();
        if (player == null || npc == null)
            return;

        if (!NpcInstance.canBypassCheck(player, npc))
            return;

        String var = player.getVar("backCoords");
        if (var == null || var.equals("")) {
            teleOut();
            return;
        }
        player.teleToLocation(Location.parseLoc(var));
    }

    public void teleOut() {
        Player player = getSelf();
        NpcInstance npc = getNpc();
        if (player == null || npc == null)
            return;

        player.teleToLocation(-44316, -113136, -80); //Orc Village
        show(player.isLangRus() ? "Я не знаю, как Вы попали сюда, но я могу Вас отправить в ближайший город." : "I don't know from where you came here, but I can teleport you the nearest town.", player, npc);
    }
}