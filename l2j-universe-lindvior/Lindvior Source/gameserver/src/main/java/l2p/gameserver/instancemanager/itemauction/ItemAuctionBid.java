package l2p.gameserver.instancemanager.itemauction;

import l2p.gameserver.model.GameObjectsStorage;
import l2p.gameserver.model.Player;

/**
 * @author n0nam3
 */
public final class ItemAuctionBid {
    private final int _charId;
    private long _lastBid;

    public ItemAuctionBid(int charId, long lastBid) {
        _charId = charId;
        _lastBid = lastBid;
    }

    public final int getCharId() {
        return _charId;
    }

    public final long getLastBid() {
        return _lastBid;
    }

    final void setLastBid(long lastBid) {
        _lastBid = lastBid;
    }

    final void cancelBid() {
        _lastBid = -1;
    }

    final boolean isCanceled() {
        return _lastBid == -1;
    }

    final Player getPlayer() {
        return GameObjectsStorage.getPlayer(_charId);
    }
}