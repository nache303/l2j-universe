/*
 * Copyright Mazaffaka Project (c) 2013.
 */

package l2p.gameserver.network.serverpackets;

public class ExEnchantSkillResult extends L2GameServerPacket {
    public static final L2GameServerPacket SUCCESS = new ExEnchantSkillResult(1);
    public static final L2GameServerPacket FAIL = new ExEnchantSkillResult(0);

    private final int _result;

    public ExEnchantSkillResult(int result) {
        _result = result;
    }

    @Override
    protected void writeImpl() {
        writeEx(0xA8);
        writeD(_result);
    }
}