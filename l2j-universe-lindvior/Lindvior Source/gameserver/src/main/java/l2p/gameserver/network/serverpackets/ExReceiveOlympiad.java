package l2p.gameserver.network.serverpackets;

import l2p.gameserver.Config;
import l2p.gameserver.model.base.ClassId;
import l2p.gameserver.model.entity.olympiad.Olympiad;
import l2p.gameserver.model.entity.olympiad.OlympiadGame;
import l2p.gameserver.model.entity.olympiad.OlympiadManager;
import l2p.gameserver.model.entity.olympiad.TeamMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author VISTALL @date 0:50/09.04.2011
 */
public abstract class ExReceiveOlympiad extends L2GameServerPacket {
    private final int _type;

    public ExReceiveOlympiad(int type) {
        _type = type;
    }

    @Override
    protected void writeImpl() {
        writeEx(0xD5);
        writeD(_type);
    }

    public static class MatchList extends ExReceiveOlympiad {
        private List<ArenaInfo> _arenaList = Collections.emptyList();

        public MatchList() {
            super(0);

            OlympiadManager manager = Olympiad._manager;

            if (manager != null) {
                _arenaList = new ArrayList<ArenaInfo>();

                for (int i = 0; i < Olympiad.STADIUMS.length; i++) {
                    OlympiadGame game = manager.getOlympiadInstance(i);

                    if ((game != null) && (game.getState() > 0)) {
                        _arenaList.add(new ArenaInfo(i, game.getState(), Olympiad.getCompetitionType() /* .getType() */.ordinal(), game.getTeamName1(), game.getTeamName2()));
                    }
                }
            }
        }

        @Override
        protected void writeImpl() {
            super.writeImpl();
            writeD(_arenaList.size());
            writeD(0x00); // unknown

            for (ArenaInfo arena : _arenaList) {
                writeD(arena._id);
                writeD(arena._matchType);
                writeD(arena._status);
                writeS(arena._name1);
                writeS(arena._name2);
            }
        }

        private static class ArenaInfo {
            int _id;
            int _status;
            int _matchType;
            String _name1;
            String _name2;

            public ArenaInfo(int id, int status, int match_type, String name1, String name2) {
                _id = id;
                _status = status;
                _matchType = match_type;
                _name1 = name1;
                _name2 = name2;
            }
        }
    }

    public static class MatchResult extends ExReceiveOlympiad {
        private final List<PlayerInfo>[] _players = new ArrayList[2];
        private final String _name;
        private final boolean _tie;

        public MatchResult(boolean tie, String name) {
            super(1);

            _tie = tie;
            _name = name;
        }

        public void addPlayer(int team, TeamMember member, int gameResultPoints) {
            int points = Config.OLYMPIAD_OLDSTYLE_STAT ? 0 : member.getStat().getInteger(Olympiad.POINTS, 0);
            PlayerInfo playerInfo = new PlayerInfo(member.getName(), member.getClanName(), member.getClassId(), points, gameResultPoints, (int) member.getDamage());

            if (_players[team] == null) {
                _players[team] = new ArrayList<PlayerInfo>(2);
            }

            _players[team].add(playerInfo);
        }

        @Override
        protected void writeImpl() {
            super.writeImpl();
            writeD(_tie);
            writeS(_name);

            for (int i = 0; i < _players.length; i++) {
                writeD(i + 1);

                List<PlayerInfo> players = (_players[i] == null) ? Collections.<PlayerInfo>emptyList() : _players[i];

                writeD(players.size());

                for (PlayerInfo playerInfo : players) {
                    writeS(playerInfo._name);
                    writeS(playerInfo._clanName);
                    writeD(0x00);
                    writeD(ClassId.VALUES[playerInfo._classId].ordinal());
                    writeD(playerInfo._damage);
                    writeD(playerInfo._currentPoints);
                    writeD(playerInfo._gamePoints);
                }
            }
        }

        private static class PlayerInfo {
            int _classId;
            int _currentPoints;
            int _gamePoints;
            int _damage;
            String _name;
            String _clanName;

            public PlayerInfo(String name, String clanName, int classId, int currentPoints, int gamePoints, int damage) {
                _name = name;
                _clanName = clanName;
                _classId = classId;
                _currentPoints = currentPoints;
                _gamePoints = gamePoints;
                _damage = damage;
            }
        }
    }
}
