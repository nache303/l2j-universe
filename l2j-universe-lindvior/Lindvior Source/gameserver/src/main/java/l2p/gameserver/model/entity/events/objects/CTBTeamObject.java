package l2p.gameserver.model.entity.events.objects;

import l2p.gameserver.data.xml.holder.NpcHolder;
import l2p.gameserver.idfactory.IdFactory;
import l2p.gameserver.model.entity.events.GlobalEvent;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.instances.residences.clanhall.CTBBossInstance;
import l2p.gameserver.templates.npc.NpcTemplate;
import l2p.gameserver.utils.Location;

/**
 * @author VISTALL
 * @date 18:28/31.03.2011
 */
public class CTBTeamObject implements SpawnableObject {
    /**
     *
     */
    private static final long serialVersionUID = -8484438967164463244L;

    private CTBSiegeClanObject _siegeClan;

    private final NpcTemplate _mobTemplate;
    private final NpcTemplate _flagTemplate;
    private final Location _flagLoc;

    private NpcInstance _flag;
    private CTBBossInstance _mob;

    public CTBTeamObject(int mobTemplate, int flagTemplate, Location flagLoc) {
        _mobTemplate = NpcHolder.getInstance().getTemplate(mobTemplate);
        _flagTemplate = NpcHolder.getInstance().getTemplate(flagTemplate);
        _flagLoc = flagLoc;
    }

    @Override
    public void spawnObject(GlobalEvent event) {
        if (_flag == null) {
            _flag = new NpcInstance(IdFactory.getInstance().getNextId(), _flagTemplate);
            _flag.setCurrentHpMp(_flag.getMaxHp(), _flag.getMaxMp());
            _flag.setHasChatWindow(false);
            _flag.spawnMe(_flagLoc);
        } else if (_mob == null) {
            NpcTemplate template = _siegeClan == null || _siegeClan.getParam() == 0 ? _mobTemplate : NpcHolder.getInstance().getTemplate((int) _siegeClan.getParam());

            _mob = (CTBBossInstance) template.getNewInstance();
            _mob.setCurrentHpMp(_mob.getMaxHp(), _mob.getMaxMp());
            _mob.setMatchTeamObject(this);
            _mob.addEvent(event);

            int x = (int) (_flagLoc.x + 300 * Math.cos(_mob.headingToRadians(_flag.getHeading() - 32768)));
            int y = (int) (_flagLoc.y + 300 * Math.sin(_mob.headingToRadians(_flag.getHeading() - 32768)));

            Location loc = new Location(x, y, _flag.getZ(), _flag.getHeading());
            _mob.setSpawnedLoc(loc);
            _mob.spawnMe(loc);
        } else
            throw new IllegalArgumentException("Cant spawn twice");
    }

    @Override
    public void despawnObject(GlobalEvent event) {
        if (_mob != null) {
            _mob.deleteMe();
            _mob = null;
        }
        if (_flag != null) {
            _flag.deleteMe();
            _flag = null;
        }
        _siegeClan = null;
    }

    @Override
    public void refreshObject(GlobalEvent event) {

    }

    public CTBSiegeClanObject getSiegeClan() {
        return _siegeClan;
    }

    public void setSiegeClan(CTBSiegeClanObject siegeClan) {
        _siegeClan = siegeClan;
    }

    public boolean isParticle() {
        return _flag != null && _mob != null;
    }

    public NpcInstance getFlag() {
        return _flag;
    }
}
