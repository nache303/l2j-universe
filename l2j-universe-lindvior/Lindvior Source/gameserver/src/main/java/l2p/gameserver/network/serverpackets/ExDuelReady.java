/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.entity.events.impl.DuelEvent;

public class ExDuelReady extends L2GameServerPacket {
    private int _duelType;

    public ExDuelReady(DuelEvent event) {
        _duelType = event.getDuelType();
    }

    @Override
    protected final void writeImpl() {
        writeEx(0x4E);
        writeD(_duelType);
    }
}