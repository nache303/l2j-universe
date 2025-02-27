package services.petevolve;

import l2p.commons.dao.JdbcEntityState;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.Summon;
import l2p.gameserver.model.instances.NpcInstance;
import l2p.gameserver.model.items.ItemInstance;
import l2p.gameserver.scripts.Functions;
import l2p.gameserver.tables.PetDataTable;
import l2p.gameserver.tables.PetDataTable.L2Pet;

/**
 * User: darkevil
 * Date: 16.05.2008
 * Time: 12:19:36
 */
public class wolfevolve extends Functions {
    private static final int WOLF = PetDataTable.PET_WOLF_ID; //Чтоб было =), проверка на Wolf
    private static final int WOLF_COLLAR = L2Pet.WOLF.getControlItemId(); // Ошейник Wolf
    private static final int GREAT_WOLF_NECKLACE = L2Pet.GREAT_WOLF.getControlItemId(); // Ожерелье Great Wolf

    public void evolve() {
        Player player = getSelf();
        NpcInstance npc = getNpc();
        if (player == null || npc == null)
            return;
        Summon pl_pet = player.getSummonList().getPet();
        if (player.getInventory().getItemByItemId(WOLF_COLLAR) == null) {
            show("scripts/services/petevolve/no_item.htm", player, npc);
            return;
        }
        if (pl_pet == null || pl_pet.isDead()) {
            show("scripts/services/petevolve/evolve_no.htm", player, npc);
            return;
        }
        if (pl_pet.getNpcId() != WOLF) {
            show("scripts/services/petevolve/no_wolf.htm", player, npc);
            return;
        }
        if (pl_pet.getLevel() < 55) {
            show("scripts/services/petevolve/no_level.htm", player, npc);
            return;
        }

        int controlItemId = pl_pet.getControlItemObjId();
        player.getSummonList().unsummonPet();

        ItemInstance control = player.getInventory().getItemByObjectId(controlItemId);
        control.setItemId(GREAT_WOLF_NECKLACE);
        control.setEnchantLevel(L2Pet.GREAT_WOLF.getMinLevel());
        control.setJdbcState(JdbcEntityState.UPDATED);
        control.update();
        player.sendItemList(false);

        show("scripts/services/petevolve/yes_wolf.htm", player, npc);
    }
}