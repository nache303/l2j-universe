package ai;

import l2p.commons.threading.RunnableImpl;
import l2p.commons.util.Rnd;
import l2p.gameserver.ThreadPoolManager;
import l2p.gameserver.ai.DefaultAI;
import l2p.gameserver.data.xml.holder.NpcHolder;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Skill;
import l2p.gameserver.model.World;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.tables.SkillTable;
import l2p.gameserver.templates.npc.NpcTemplate;

import java.util.List;

/**
 * Author: VISTALL
 * Date:  9:03/17.11.2010
 * npc Id : 18601
 */
public class KrateisCubeWatcherRed extends DefaultAI {
    private static final int[][] SKILLS = {{1064, 14}, {1160, 15}, {1164, 19}, {1167, 6}, {1168, 7}};
    private static final int SKILL_CHANCE = 25;

    public KrateisCubeWatcherRed(NpcInstance actor) {
        super(actor);
        AI_TASK_ACTIVE_DELAY = 3000;
    }

    @Override
    protected void onEvtAttacked(Creature attacker, int damage) {
    }

    @Override
    protected void onEvtThink() {
        NpcInstance actor = getActor();
        List<Creature> around = World.getAroundCharacters(actor, 600, 300);
        if (around.isEmpty())
            return;

        for (Creature cha : around)
            if (cha.isPlayer() && !cha.isDead() && Rnd.chance(SKILL_CHANCE)) {
                int rnd = Rnd.get(SKILLS.length);
                Skill skill = SkillTable.getInstance().getInfo(SKILLS[rnd][0], SKILLS[rnd][1]);
                if (skill != null)
                    skill.getEffects(cha, cha, false, false);
            }
    }

    @Override
    public void onEvtDead(Creature killer) {
        final NpcInstance actor = getActor();
        super.onEvtDead(killer);

        actor.deleteMe();
        ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
            @Override
            public void runImpl() throws Exception {
                NpcTemplate template = NpcHolder.getInstance().getTemplate(18602);
                if (template != null) {
                    NpcInstance a = template.getNewInstance();
                    a.setCurrentHpMp(a.getMaxHp(), a.getMaxMp());
                    a.spawnMe(actor.getLoc());
                }
            }
        }, 10000L);
    }
}
