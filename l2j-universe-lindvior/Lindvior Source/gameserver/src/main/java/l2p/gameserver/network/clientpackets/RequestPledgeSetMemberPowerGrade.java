package l2p.gameserver.network.clientpackets;

import l2p.gameserver.model.Player;
import l2p.gameserver.model.pledge.Clan;
import l2p.gameserver.model.pledge.UnitMember;
import l2p.gameserver.network.serverpackets.components.CustomMessage;

public class RequestPledgeSetMemberPowerGrade extends L2GameClientPacket {
    // format: (ch)Sd
    private int _powerGrade;
    private String _name;

    @Override
    protected void readImpl() {
        _name = readS(16);
        _powerGrade = readD();
    }

    @Override
    protected void runImpl() {
        Player activeChar = getClient().getActiveChar();
        if (activeChar == null)
            return;

        if (_powerGrade < Clan.RANK_FIRST || _powerGrade > Clan.RANK_LAST)
            return;

        Clan clan = activeChar.getClan();
        if (clan == null)
            return;

        if ((activeChar.getClanPrivileges() & Clan.CP_CL_MANAGE_RANKS) == Clan.CP_CL_MANAGE_RANKS) {
            UnitMember member = activeChar.getClan().getAnyMember(_name);
            if (member != null) {
                if (Clan.isAcademy(member.getPledgeType())) {
                    activeChar.sendMessage("You cannot change academy member grade.");
                    return;
                }
                if (_powerGrade > 5)
                    member.setPowerGrade(clan.getAffiliationRank(member.getPledgeType()));
                else
                    member.setPowerGrade(_powerGrade);
                if (member.isOnline())
                    member.getPlayer().sendUserInfo();
            } else
                activeChar.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestPledgeSetMemberPowerGrade.NotBelongClan", activeChar));
        } else
            activeChar.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.RequestPledgeSetMemberPowerGrade.HaveNotAuthority", activeChar));
    }
}