package l2p.gameserver.network.clientpackets;

import l2p.gameserver.Config;
import l2p.gameserver.data.xml.holder.PetitionGroupHolder;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.petition.PetitionMainGroup;
import l2p.gameserver.network.serverpackets.ExResponseShowStepTwo;

/**
 * @author VISTALL
 */
public class RequestExShowStepTwo extends L2GameClientPacket {
    private int _petitionGroupId;

    @Override
    protected void readImpl() {
        _petitionGroupId = readC();
    }

    @Override
    protected void runImpl() {
        Player player = getClient().getActiveChar();
        if (player == null || !Config.EX_NEW_PETITION_SYSTEM)
            return;

        PetitionMainGroup group = PetitionGroupHolder.getInstance().getPetitionGroup(_petitionGroupId);
        if (group == null)
            return;

        player.setPetitionGroup(group);
        player.sendPacket(new ExResponseShowStepTwo(player, group));
    }
}