package l2p.gameserver.model.entity.residence;

import l2p.commons.dao.JdbcEntityState;
import l2p.gameserver.dao.DominionDAO;
import l2p.gameserver.data.xml.holder.EventHolder;
import l2p.gameserver.data.xml.holder.ResidenceHolder;
import l2p.gameserver.model.GameObjectsStorage;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.entity.events.EventType;
import l2p.gameserver.model.entity.events.impl.DominionSiegeRunnerEvent;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.pledge.Clan;
import l2p.gameserver.network.serverpackets.SystemMessage2;
import l2p.gameserver.network.serverpackets.components.SystemMsg;
import l2p.gameserver.templates.StatsSet;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.TreeIntSet;

/**
 * @author VISTALL
 * @date 15:15/14.02.2011
 */
public class Dominion extends Residence {
    /**
     *
     */
    private static final long serialVersionUID = -4483639125159733279L;
    private IntSet _flags = new TreeIntSet();
    private Castle _castle;
    private int _lordObjectId;

    public Dominion(StatsSet set) {
        super(set);
    }

    @Override
    public void init() {
        initEvent();

        _castle = ResidenceHolder.getInstance().getResidence(Castle.class, getId() - 80);
        _castle.setDominion(this);

        loadData();

        _siegeDate.setTimeInMillis(0);
        if (getOwner() != null) {
            DominionSiegeRunnerEvent runnerEvent = EventHolder.getInstance().getEvent(EventType.MAIN_EVENT, 1);
            runnerEvent.registerDominion(this);
        }
    }

    @Override
    public void rewardSkills() {
        Clan owner = getOwner();
        if (owner != null) {
            if (!_flags.contains(getId()))
                return;

            for (int dominionId : _flags.toArray()) {
                Dominion dominion = ResidenceHolder.getInstance().getResidence(Dominion.class, dominionId);
                for (Skill skill : dominion.getSkills()) {
                    owner.addSkill(skill, false);
                    owner.broadcastToOnlineMembers(new SystemMessage2(SystemMsg.THE_CLAN_SKILL_S1_HAS_BEEN_ADDED).addSkillName(skill));
                }
            }
        }
    }

    @Override
    public void removeSkills() {
        Clan owner = getOwner();
        if (owner != null)
            for (int dominionId : _flags.toArray()) {
                Dominion dominion = ResidenceHolder.getInstance().getResidence(Dominion.class, dominionId);
                for (Skill skill : dominion.getSkills())
                    owner.removeSkill(skill.getId());
            }
    }

    public void addFlag(int dominionId) {
        _flags.add(dominionId);
    }

    public void removeFlag(int dominionId) {
        _flags.remove(dominionId);
    }

    public int[] getFlags() {
        return _flags.toArray();
    }

    @Override
    public ResidenceType getType() {
        return ResidenceType.Dominion;
    }

    @Override
    protected void loadData() {
        DominionDAO.getInstance().select(this);
    }

    @Override
    public void changeOwner(Clan clan) {
        int newLordObjectId;
        if (clan == null) {
            if (_lordObjectId > 0)
                newLordObjectId = 0;
            else
                return;
        } else {
            newLordObjectId = clan.getLeaderId();

            // разсылаем мессагу
            SystemMessage2 message = new SystemMessage2(SystemMsg.CLAN_LORD_C2_WHO_LEADS_CLAN_S1_HAS_BEEN_DECLARED_THE_LORD_OF_THE_S3_TERRITORY).addName(clan.getLeader().getPlayer()).addString(clan.getName()).addResidenceName(getCastle());
            for (Player player : GameObjectsStorage.getAllPlayersForIterate())
                player.sendPacket(message);
        }

        _lordObjectId = newLordObjectId;

        setJdbcState(JdbcEntityState.UPDATED);
        update();

        // обновляем значки в нпц которые принадлежат територии
        for (NpcInstance npc : GameObjectsStorage.getAllNpcsForIterate())
            if (npc.getDominion() == this)
                npc.broadcastCharInfoImpl();
    }

    public int getLordObjectId() {
        return _lordObjectId;
    }

    @Override
    public Clan getOwner() {
        return _castle.getOwner();
    }

    @Override
    public int getOwnerId() {
        return _castle.getOwnerId();
    }

    public Castle getCastle() {
        return _castle;
    }

    @Override
    public void update() {
        DominionDAO.getInstance().update(this);
    }

    public void setLordObjectId(int lordObjectId) {
        _lordObjectId = lordObjectId;
    }
}
