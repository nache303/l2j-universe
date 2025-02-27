/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network.clientpackets;

import l2p.commons.util.Rnd;
import l2p.gameserver.Config;
import l2p.gameserver.data.xml.holder.ItemHolder;
import l2p.gameserver.data.xml.holder.RecipeHolder;
import l2p.gameserver.model.Player;
import l2p.gameserver.model.items.ItemInstance;
import l2p.gameserver.network.serverpackets.ActionFail;
import l2p.gameserver.network.serverpackets.RecipeItemMakeInfo;
import l2p.gameserver.network.serverpackets.SystemMessage;
import l2p.gameserver.network.serverpackets.SystemMessage2;
import l2p.gameserver.network.serverpackets.components.SystemMsg;
import l2p.gameserver.templates.item.RecipeTemplate;
import l2p.gameserver.templates.item.RecipeTemplate.RecipeComponent;
import l2p.gameserver.templates.item.type.EtcItemType;
import l2p.gameserver.utils.ItemFunctions;

public class RequestRecipeItemMakeSelf extends L2GameClientPacket {
    private int _recipeId;

    /**
     * packet type id 0xB8
     * format:		cd
     */
    @Override
    protected void readImpl() {
        _recipeId = readD();
    }

    @Override
    protected void runImpl() {
        Player activeChar = getClient().getActiveChar();
        if (activeChar == null)
            return;

        if (activeChar.isActionsDisabled()) {
            activeChar.sendActionFailed();
            return;
        }

        if (activeChar.isInStoreMode()) {
            activeChar.sendActionFailed();
            return;
        }

        if (activeChar.isProcessingRequest()) {
            activeChar.sendActionFailed();
            return;
        }

        if (activeChar.isFishing()) {
            activeChar.sendPacket(SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
            return;
        }

        RecipeTemplate recipe = RecipeHolder.getInstance().getRecipeByRecipeId(_recipeId);

        if (recipe == null || recipe.getMaterials().length == 0) {
            activeChar.sendPacket(SystemMsg.THE_RECIPE_IS_INCORRECT);
            return;
        }

        if (activeChar.getCurrentMp() < recipe.getMpConsume()) {
            activeChar.sendPacket(SystemMsg.NOT_ENOUGH_MP, new RecipeItemMakeInfo(activeChar, recipe, 0));
            return;
        }

        if (!activeChar.findRecipe(_recipeId)) {
            activeChar.sendPacket(SystemMsg.PLEASE_REGISTER_A_RECIPE, ActionFail.STATIC);
            return;
        }

        activeChar.getInventory().writeLock();
        try {
            RecipeComponent[] materials = recipe.getMaterials();

            for (RecipeComponent material : materials) {
                if (material.getCount() == 0)
                    continue;

                if (Config.ALT_GAME_UNREGISTER_RECIPE && ItemHolder.getInstance().getTemplate(material.getItemId()).getItemType() == EtcItemType.RECIPE) {
                    RecipeTemplate rp = RecipeHolder.getInstance().getRecipeByRecipeItem(material.getItemId());
                    if (activeChar.hasRecipe(rp))
                        continue;
                    activeChar.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_MATERIALS_TO_PERFORM_THAT_ACTION, new RecipeItemMakeInfo(activeChar, recipe, 0));
                    return;
                }

                ItemInstance item = activeChar.getInventory().getItemByItemId(material.getItemId());
                if (item == null || item.getCount() < material.getCount()) {
                    activeChar.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_MATERIALS_TO_PERFORM_THAT_ACTION, new RecipeItemMakeInfo(activeChar, recipe, 0));
                    return;
                }
            }

            for (RecipeComponent material : materials) {
                if (material.getCount() == 0)
                    continue;

                if (Config.ALT_GAME_UNREGISTER_RECIPE && ItemHolder.getInstance().getTemplate(material.getItemId()).getItemType() == EtcItemType.RECIPE)
                    activeChar.unregisterRecipe(RecipeHolder.getInstance().getRecipeByRecipeItem(material.getItemId()).getId());
                else {
                    if (!activeChar.getInventory().destroyItemByItemId(material.getItemId(), material.getCount()))
                        continue;//TODO audit
                    activeChar.sendPacket(SystemMessage2.removeItems(material.getItemId(), material.getCount()));
                }
            }
        } finally {
            activeChar.getInventory().writeUnlock();
        }

        activeChar.resetWaitSitTime();
        activeChar.reduceCurrentMp(recipe.getMpConsume(), null);

        RecipeComponent product = recipe.getRandomProduct();
        int itemId = product.getItemId();
        long itemsCount = product.getCount();
        int success = 0;
        if (Rnd.chance(recipe.getSuccessRate())) {
            //TODO [G1ta0] добавить проверку на перевес
            ItemFunctions.addItem(activeChar, itemId, itemsCount, true);
            success = 1;
        } else
            activeChar.sendPacket(new SystemMessage(SystemMessage.S1_MANUFACTURING_FAILURE).addItemName(itemId));

        activeChar.sendPacket(new RecipeItemMakeInfo(activeChar, recipe, success));
    }
}