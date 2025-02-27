/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

/**
 * @author Darvin
 * @data 08.06.2012
 */
public class ExCallToChangeClass extends L2GameServerPacket {
    private int classId;
    private boolean showMsg;

    public ExCallToChangeClass(int classId, boolean showMsg) {
        this.classId = classId;
        this.showMsg = showMsg;
    }

    @Override
    protected void writeImpl() {
        writeEx(0xFE);
        writeD(classId); // New Class Id
        writeD(showMsg); // Show Message
    }
}
