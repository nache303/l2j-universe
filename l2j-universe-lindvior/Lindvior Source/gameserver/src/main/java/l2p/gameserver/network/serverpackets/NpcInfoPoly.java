package l2p.gameserver.network.serverpackets;

import l2p.gameserver.data.xml.holder.NpcHolder;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.base.TeamType;
import l2p.gameserver.templates.npc.NpcTemplate;

public class NpcInfoPoly extends L2GameServerPacket {
    //   ddddddddddddddddddffffdddcccccSSddd dddddccffddddccd
    private Creature _obj;
    private int _x, _y, _z, _heading;
    private int _npcId;
    private boolean _isSummoned, _isRunning, _isInCombat, _isAlikeDead;
    private int _mAtkSpd, _pAtkSpd;
    private int _runSpd, _walkSpd, _swimRunSpd, _swimWalkSpd, _flRunSpd, _flWalkSpd, _flyRunSpd, _flyWalkSpd;
    private int _rhand, _lhand;
    private String _name, _title;
    private double colRadius, colHeight;
    private TeamType _team;
    private int[] _abnormalEffects;

    public NpcInfoPoly(Player cha) {
        _obj = cha;
        _npcId = cha.getPolyId();
        NpcTemplate template = NpcHolder.getInstance().getTemplate(_npcId);
        _rhand = 0;
        _lhand = 0;
        _isSummoned = false;
        colRadius = template.getCollisionRadius();
        colHeight = template.getCollisionHeight();
        _x = _obj.getX();
        _y = _obj.getY();
        _z = _obj.getZ();
        _rhand = template.rhand;
        _lhand = template.lhand;
        _heading = cha.getHeading();
        _mAtkSpd = cha.getMAtkSpd();
        _pAtkSpd = cha.getPAtkSpd();
        _runSpd = cha.getRunSpeed();
        _walkSpd = cha.getWalkSpeed();
        _swimRunSpd = _flRunSpd = _flyRunSpd = _runSpd;
        _swimWalkSpd = _flWalkSpd = _flyWalkSpd = _walkSpd;
        _isRunning = cha.isRunning();
        _isInCombat = cha.isInCombat();
        _isAlikeDead = cha.isAlikeDead();
        _name = cha.getName();
        _title = cha.getTitle();
        _abnormalEffects = cha.getAbnormalEffects();
        _team = cha.getTeam();
    }

    @Override
    protected final void writeImpl() {
        writeC(0x0c);
        writeD(_obj.getObjectId());
        writeD(_npcId + 1000000); // npctype id
        writeD(0x00);
        writeD(_x);
        writeD(_y);
        writeD(_z);
        writeD(_heading);
        writeD(0x00);
        writeD(_mAtkSpd);
        writeD(_pAtkSpd);
        writeD(_runSpd);
        writeD(_walkSpd);
        writeD(_swimRunSpd/*0x32*/); // swimspeed
        writeD(_swimWalkSpd/*0x32*/); // swimspeed
        writeD(_flRunSpd);
        writeD(_flWalkSpd);
        writeD(_flyRunSpd);
        writeD(_flyWalkSpd);

        writeF(1/*_cha.getProperMultiplier()*/);
        writeF(1/*_cha.getAttackSpeedMultiplier()*/);
        writeF(colRadius);
        writeF(colHeight);

        writeD(_rhand); // right hand weapon
        writeD(0);
        writeD(_lhand); // left hand weapon

        writeC(1); // name above char 1=true ... ??
        writeC(_isRunning ? 1 : 0);
        writeC(_isInCombat ? 1 : 0);
        writeC(_isAlikeDead ? 1 : 0);
        writeC(_isSummoned ? 2 : 0); // invisible ?? 0=false  1=true   2=summoned (only works if model has a summon animation)
        writeS(_name);
        writeS(_title);

        writeD(0);
        writeD(0);
        writeD(0000); // hmm karma ??

        writeD(0000); // C2
        writeD(0000); // C2
        writeD(0000); // C2
        writeD(0000); // C2

        writeC(0000); // C2
        writeC(_team.ordinal());

        writeF(colRadius); // тут что-то связанное с colRadius
        writeF(colHeight); // тут что-то связанное с colHeight
        writeD(0x00); // C4
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);

        writeC(1); // show name
        writeC(1); // show title

        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);

        writeH(0x00);
        writeC(0x00);
        writeF(0x00);

        writeDD(_abnormalEffects, true);
    }
}
