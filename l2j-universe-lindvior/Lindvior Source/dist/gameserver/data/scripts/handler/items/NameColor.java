package handler.items;

import l2p.gameserver.model.Player;
import l2p.gameserver.model.items.ItemInstance;
import l2p.gameserver.network.serverpackets.ExChangeNicknameNColor;

public class NameColor extends SimpleItemHandler {
    private static final int[] ITEM_IDS = new int[]{13021, 13307};

    @Override
    public int[] getItemIds() {
        return ITEM_IDS;
    }

    @Override
    protected boolean useItemImpl(Player player, ItemInstance item, boolean ctrl) {
        player.sendPacket(new ExChangeNicknameNColor(item.getObjectId()));
        return true;
    }
}
