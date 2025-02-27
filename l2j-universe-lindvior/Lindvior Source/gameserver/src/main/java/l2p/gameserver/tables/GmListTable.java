package l2p.gameserver.tables;

import l2p.gameserver.cache.Msg;
import l2p.gameserver.model.GameObjectsStorage;
import l2p.gameserver.model.Player;
import l2p.gameserver.network.serverpackets.L2GameServerPacket;
import l2p.gameserver.network.serverpackets.SystemMessage;

import java.util.ArrayList;
import java.util.List;

public class GmListTable {
    public static List<Player> getAllGMs() {
        List<Player> gmList = new ArrayList<Player>();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate())
            if (player.isGM())
                gmList.add(player);

        return gmList;
    }

    public static List<Player> getAllVisibleGMs() {
        List<Player> gmList = new ArrayList<Player>();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate())
            if (player.isGM() && !player.isInvisible())
                gmList.add(player);

        return gmList;
    }

    public static void sendListToPlayer(Player player) {
        List<Player> gmList = getAllVisibleGMs();
        if (gmList.isEmpty()) {
            player.sendPacket(Msg.THERE_ARE_NOT_ANY_GMS_THAT_ARE_PROVIDING_CUSTOMER_SERVICE_CURRENTLY);
            return;
        }

        player.sendPacket(Msg._GM_LIST_);
        for (Player gm : gmList)
            player.sendPacket(new SystemMessage(SystemMessage.GM_S1).addString(gm.getName()));
    }

    public static void broadcastToGMs(L2GameServerPacket packet) {
        for (Player gm : getAllGMs())
            gm.sendPacket(packet);
    }

    public static void broadcastMessageToGMs(String message) {
        for (Player gm : getAllGMs())
            gm.sendMessage(message);
    }
}