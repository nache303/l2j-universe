package ai.custom;

import l2p.gameserver.ai.Fighter;
import l2p.gameserver.model.Creature;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.scripts.Functions;

/**
 * AI Ekimus
 *
 * @author pchayka
 */
public class GvGBoss extends Fighter {
    boolean phrase1 = false;
    boolean phrase2 = false;
    boolean phrase3 = false;

    public GvGBoss(NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected void onEvtAttacked(Creature attacker, int damage) {
        NpcInstance actor = getActor();

        if (actor.getCurrentHpPercents() < 50 && phrase1 == false) {
            phrase1 = true;
            Functions.npcSay(actor, "Вам не удастся похитить сокровища Геральда!");
        } else if (actor.getCurrentHpPercents() < 30 && phrase2 == false) {
            phrase2 = true;
            Functions.npcSay(actor, "Я тебе череп проломлю!");
        } else if (actor.getCurrentHpPercents() < 5 && phrase3 == false) {
            phrase3 = true;
            Functions.npcSay(actor, "Вы все погибнете в страшных муках! Уничтожу!");
        }

        super.onEvtAttacked(attacker, damage);
    }
}