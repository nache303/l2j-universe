/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.mail.Mail;

public class ExChangePostState extends L2GameServerPacket {
    private boolean _receivedBoard;
    private Mail[] _mails;
    private int _changeId;

    public ExChangePostState(boolean receivedBoard, int type, Mail... n) {
        _receivedBoard = receivedBoard;
        _mails = n;
        _changeId = type;
    }

    @Override
    protected void writeImpl() {
        writeEx(0xB4);
        writeD(_receivedBoard ? 1 : 0);
        writeD(_mails.length);
        for (Mail mail : _mails) {
            writeD(mail.getMessageId()); // postId
            writeD(_changeId); // state
        }
    }
}