/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

public class Ex2ndPasswordVerify extends L2GameServerPacket {
    public static final int PASSWORD_OK = 0x00;
    public static final int PASSWORD_WRONG = 0x01;
    public static final int PASSWORD_BAN = 0x02;

    private int _wrongTentatives, _mode;

    public Ex2ndPasswordVerify(int mode, int wrongTentatives) {
        _mode = mode;
        _wrongTentatives = wrongTentatives;
    }

    @Override
    protected void writeImpl() {
        writeEx(0x10B);
        writeD(_mode);
        writeD(_wrongTentatives);
    }
}
