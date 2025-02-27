package l2p.gameserver.skills.skillclasses;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.Skill;
import l2p.gameserver.network.serverpackets.RecipeBookItemList;
import l2p.gameserver.templates.StatsSet;

import java.util.List;

public class Craft extends Skill {
    private final boolean _dwarven;

    public Craft(StatsSet set) {
        super(set);
        _dwarven = set.getBool("isDwarven");
    }

    @Override
    public boolean checkCondition(Creature activeChar, Creature target, boolean forceUse, boolean dontMove, boolean first) {
        Player p = (Player) activeChar;
        if (p.isInStoreMode() || p.isProcessingRequest())
            return false;

        return super.checkCondition(activeChar, target, forceUse, dontMove, first);
    }

    @Override
    public void useSkill(Creature activeChar, List<Creature> targets) {
        activeChar.sendPacket(new RecipeBookItemList((Player) activeChar, _dwarven));
    }
}
