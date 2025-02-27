package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.items.ItemInstance;
import l2p.gameserver.model.mail.Mail;
import l2p.gameserver.network.clientpackets.RequestExCancelSentPost;
import l2p.gameserver.network.clientpackets.RequestExRequestSentPost;

/**
 * Просмотр собственного отправленного письма. Шлется в ответ на {@link RequestExRequestSentPost}.
 * При нажатии на кнопку Cancel клиент шлет {@link RequestExCancelSentPost}.
 *
 * @see ExReplyReceivedPost
 */
public class ExReplySentPost extends L2GameServerPacket {
    private final Mail mail;

    public ExReplySentPost(Mail mail) {
        this.mail = mail;
    }

    // ddSSS dx[hddQdddhhhhhhhhhh] Qd
    @Override
    protected void writeImpl() {
        writeEx449(0xAD);

        writeD(mail.getType().ordinal());

        // Type = Normal
        writeD(mail.getMessageId());

        writeD(0x00);// unknown1

        writeS(mail.getSenderName()); // от кого
        writeS(mail.getTopic()); // топик
        writeS(mail.getBody()); // тело

        writeD(mail.getAttachments().size()); // количество приложенных вещей
        for (ItemInstance item : mail.getAttachments()) {
            writeItemInfo(item);
            writeD(item.getObjectId());
        }

        writeQ(mail.getPrice()); // для писем с оплатой - цена
        writeD(0x00); //unk
        writeD(mail.isReturnable());
        writeD(mail.getReceiverId());
    }
}