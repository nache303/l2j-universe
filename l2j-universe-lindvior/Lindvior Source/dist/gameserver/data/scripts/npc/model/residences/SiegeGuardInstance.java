package npc.model.residences;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.base.Experience;
import l2p.gameserver.model.entity.events.impl.SiegeEvent;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.pledge.Clan;
import l2p.gameserver.model.reward.RewardItem;
import l2p.gameserver.model.reward.RewardList;
import l2p.gameserver.model.reward.RewardType;
import l2p.gameserver.stats.Stats;
import l2p.gameserver.templates.npc.NpcTemplate;

import java.util.List;
import java.util.Map;

public class SiegeGuardInstance extends NpcInstance {
    private static final long serialVersionUID = 1L;

    public SiegeGuardInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
        setHasChatWindow(false);
    }

    @Override
    public boolean isSiegeGuard() {
        return true;
    }

    @Override
    public int getAggroRange() {
        return 1200;
    }

    @Override
    public boolean isAutoAttackable(Creature attacker) {
        Player player = attacker.getPlayer();
        if (player == null)
            return false;
        SiegeEvent<?, ?> siegeEvent = getEvent(SiegeEvent.class);
        SiegeEvent<?, ?> siegeEvent2 = attacker.getEvent(SiegeEvent.class);
        Clan clan = player.getClan();
        if (siegeEvent == null)
            return false;
        if (clan != null && siegeEvent == siegeEvent2 && siegeEvent.getSiegeClan(SiegeEvent.DEFENDERS, clan) != null)
            return false;
        return true;
    }

    @Override
    public boolean hasRandomAnimation() {
        return false;
    }

    @Override
    public boolean isInvul() {
        return false;
    }

    @Override
    protected void onDeath(Creature killer) {
        SiegeEvent<?, ?> siegeEvent = getEvent(SiegeEvent.class);
        if (killer != null) {
            Player player = killer.getPlayer();
            if (siegeEvent != null && player != null) {
                Clan clan = player.getClan();
                SiegeEvent<?, ?> siegeEvent2 = killer.getEvent(SiegeEvent.class);
                if (clan != null && siegeEvent == siegeEvent2 && siegeEvent.getSiegeClan(SiegeEvent.DEFENDERS, clan) == null) {
                    Creature topdam = getAggroList().getTopDamager();
                    if (topdam == null)
                        topdam = killer;

                    for (Map.Entry<RewardType, RewardList> entry : getTemplate().getRewards().entrySet())
                        rollRewards(entry, killer, topdam);
                }
            }
        }
        super.onDeath(killer);
    }

    public void rollRewards(Map.Entry<RewardType, RewardList> entry, final Creature lastAttacker, Creature topDamager) {
        RewardList list = entry.getValue();

        final Player activePlayer = topDamager.getPlayer();

        if (activePlayer == null)
            return;

        final int diff = calculateLevelDiffForDrop(topDamager.getLevel());
        double mod = calcStat(Stats.REWARD_MULTIPLIER, 1., topDamager, null);
        mod *= Experience.penaltyModifier(diff, 9);

        List<RewardItem> rewardItems = list.roll(activePlayer, mod, false, true);

        for (RewardItem drop : rewardItems)
            dropItem(activePlayer, drop.itemId, drop.count);
    }

    @Override
    public boolean isFearImmune() {
        return true;
    }

    @Override
    public boolean isParalyzeImmune() {
        return true;
    }

    @Override
    public Clan getClan() {
        return null;
    }
}