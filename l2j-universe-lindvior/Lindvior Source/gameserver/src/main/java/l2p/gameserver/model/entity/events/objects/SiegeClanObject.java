package l2p.gameserver.model.entity.events.objects;

import l2p.gameserver.model.Player;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.entity.events.impl.SiegeEvent;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.pledge.Clan;
import l2p.gameserver.network.serverpackets.L2GameServerPacket;
import l2p.gameserver.network.serverpackets.components.IStaticPacket;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author VISTALL
 * @date 9:47/24.02.2011
 */
public class SiegeClanObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8959401815164558076L;

    public static class SiegeClanComparatorImpl implements Comparator<SiegeClanObject> {
        private static final SiegeClanComparatorImpl _instance = new SiegeClanComparatorImpl();

        public static SiegeClanComparatorImpl getInstance() {
            return _instance;
        }

        @Override
        public int compare(SiegeClanObject o1, SiegeClanObject o2) {
            return o2.getParam() < o1.getParam() ? -1 : o2.getParam() == o1.getParam() ? 0 : 1;
        }
    }

    private String _type;
    private Clan _clan;
    private NpcInstance _flag;
    private final long _date;

    public SiegeClanObject(String type, Clan clan, long param) {
        this(type, clan, 0, System.currentTimeMillis());
    }

    public SiegeClanObject(String type, Clan clan, long param, long date) {
        _type = type;
        _clan = clan;
        _date = date;
    }

    public int getObjectId() {
        return _clan.getClanId();
    }

    public Clan getClan() {
        return _clan;
    }

    public NpcInstance getFlag() {
        return _flag;
    }

    public void deleteFlag() {
        if (_flag != null) {
            _flag.deleteMe();
            _flag = null;
        }
    }

    public void setFlag(NpcInstance npc) {
        _flag = npc;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getType() {
        return _type;
    }

    public void broadcast(IStaticPacket... packet) {
        getClan().broadcastToOnlineMembers(packet);
    }

    public void broadcast(L2GameServerPacket... packet) {
        getClan().broadcastToOnlineMembers(packet);
    }

    @SuppressWarnings("rawtypes")
    public void setEvent(boolean start, SiegeEvent event) {
        if (start)
            for (Player player : _clan.getOnlineMembers(0)) {
                player.addEvent(event);
                player.broadcastCharInfo();
            }
        else
            for (Player player : _clan.getOnlineMembers(0)) {
                player.removeEvent(event);
                player.getEffectList().stopEffect(Skill.SKILL_BATTLEFIELD_DEATH_SYNDROME);
                player.broadcastCharInfo();
            }
    }

    public boolean isParticle(Player player) {
        return true;
    }

    public long getParam() {
        return 0;
    }

    public long getDate() {
        return _date;
    }
}
