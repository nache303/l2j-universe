package l2p.gameserver.network.serverpackets;

import l2p.gameserver.model.entity.Hero;
import l2p.gameserver.model.entity.olympiad.Olympiad;
import l2p.gameserver.templates.StatsSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


/**
 * Format: (ch) d [SdSdSdd]
 * d: size
 * [
 * S: hero name
 * d: hero class ID
 * S: hero clan name
 * d: hero clan crest id
 * S: hero ally name
 * d: hero Ally id
 * d: count
 * ]
 */
public class ExHeroList extends L2GameServerPacket {
    private Map<Integer, StatsSet> _heroList;

    public ExHeroList() {
        _heroList = Hero.getInstance().getHeroes();
    }

    @Override
    protected final void writeImpl() {
        writeEx449(0x79);

        writeD(_heroList.size());
        for (StatsSet hero : _heroList.values()) {
            writeS(hero.getString(Olympiad.CHAR_NAME));
            writeD(hero.getInteger(Olympiad.CLASS_ID));
            writeS(hero.getString(Hero.CLAN_NAME, StringUtils.EMPTY));
            writeD(hero.getInteger(Hero.CLAN_CREST, 0));
            writeS(hero.getString(Hero.ALLY_NAME, StringUtils.EMPTY));
            writeD(hero.getInteger(Hero.ALLY_CREST, 0));
            writeD(hero.getInteger(Hero.COUNT));
        }
    }
}