/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.model.matching;

import l2p.gameserver.model.Player;
import l2p.gameserver.network.serverpackets.ExClosePartyRoom;
import l2p.gameserver.network.serverpackets.L2GameServerPacket;
import l2p.gameserver.network.serverpackets.company.party.ExPartyRoomMember;
import l2p.gameserver.network.serverpackets.company.party.PartyRoomInfo;
import l2p.gameserver.network.serverpackets.components.SystemMsg;

/**
 * @author VISTALL
 * @date 0:44/12.06.2011
 */
public class PartyMatchingRoom extends MatchingRoom {
    public PartyMatchingRoom(Player leader, int minLevel, int maxLevel, int maxMemberSize, int lootType, String topic) {
        super(leader, minLevel, maxLevel, maxMemberSize, lootType, topic);

        leader.broadcastCharInfo();
    }

    @Override
    public SystemMsg notValidMessage() {
        return SystemMsg.YOU_DO_NOT_MEET_THE_REQUIREMENTS_TO_ENTER_THAT_PARTY_ROOM;
    }

    @Override
    public SystemMsg enterMessage() {
        return SystemMsg.C1_HAS_ENTERED_THE_PARTY_ROOM;
    }

    @Override
    public SystemMsg exitMessage(boolean toOthers, boolean kick) {
        if (toOthers)
            return kick ? SystemMsg.C1_HAS_BEEN_KICKED_FROM_THE_PARTY_ROOM : SystemMsg.C1_HAS_LEFT_THE_PARTY_ROOM;
        else
            return kick ? SystemMsg.YOU_HAVE_BEEN_OUSTED_FROM_THE_PARTY_ROOM : SystemMsg.YOU_HAVE_EXITED_THE_PARTY_ROOM;
    }

    @Override
    public SystemMsg closeRoomMessage() {
        return SystemMsg.THE_PARTY_ROOM_HAS_BEEN_DISBANDED;
    }

    @Override
    public L2GameServerPacket closeRoomPacket() {
        return ExClosePartyRoom.STATIC;
    }

    @Override
    public L2GameServerPacket infoRoomPacket() {
        return new PartyRoomInfo(this);
    }

    @Override
    public L2GameServerPacket addMemberPacket(Player $member, Player active) {
        return membersPacket($member);
    }

    @Override
    public L2GameServerPacket removeMemberPacket(Player $member, Player active) {
        return membersPacket($member);
    }

    @Override
    public L2GameServerPacket updateMemberPacket(Player $member, Player active) {
        return membersPacket($member);
    }

    @Override
    public L2GameServerPacket membersPacket(Player active) {
        return new ExPartyRoomMember(this, active);
    }

    @Override
    public int getType() {
        return PARTY_MATCHING;
    }

    @Override
    public int getMemberType(Player member) {
        return member.equals(_leader) ? ROOM_MASTER : member.getParty() != null && _leader.getParty() == member.getParty() ? PARTY_MEMBER : WAIT_PLAYER;
    }
}
