package npc.model.residences.castle;

import l2p.gameserver.model.Creature;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.templates.npc.NpcTemplate;

/**
 * @Author: Death
 * @Date: 17/9/2007
 * @Time: 19:11:50
 * <p/>
 * Этот инстанс просто для отрисовки умершей вышки на месте оригинальной на осаде
 * Фэйковый инстанс неуязвим.
 */
public class CastleFakeTowerInstance extends NpcInstance {
    private static final long serialVersionUID = 1L;

    public CastleFakeTowerInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    /**
     * Фэйковые вышки нельзя атаковать
     */
    @Override
    public boolean isAutoAttackable(Creature player) {
        return false;
    }

    /**
     * Вышки не умеют говорить
     */
    @Override
    public void showChatWindow(Player player, int val, Object... arg) {
    }

    /**
     * Вышки не умеют говорить
     */
    @Override
    public void showChatWindow(Player player, String filename, Object... replace) {
    }

    @Override
    public boolean hasRandomAnimation() {
        return false;
    }

    /**
     * Фэйковые вышки неуязвимы
     *
     * @return true
     */
    @Override
    public boolean isInvul() {
        return true;
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
    public boolean isLethalImmune() {
        return true;
    }
}