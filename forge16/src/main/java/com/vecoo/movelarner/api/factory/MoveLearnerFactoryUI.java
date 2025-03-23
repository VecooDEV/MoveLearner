package com.vecoo.movelarner.api.factory;

import com.pixelmonmod.pixelmon.api.pokemon.LearnMoveController;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.ui.pages.SelectMovePage;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.AtlantisUI;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

public class MoveLearnerFactoryUI {
    public static void learnMove(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack) {
        if (StorageProxy.getParty(player.getUUID()).get(pokemon.getUUID()) == null) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getError()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        if (pokemon.getMoveset().hasAttack(attack)) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getError()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        ItemStack itemStack = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getConfig().getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getError()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        int amountPrice = Utils.movePrice(pokemon, attack);

        if (Utils.countItemStack(player, itemStack) < amountPrice) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getNotItems()
                    .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        int totalRemoved = 0;
        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack stack = player.inventory.getItem(i);
            if (stack.getItem().equals(itemStack.getItem())) {
                if (itemStack.getTag() == null || (stack.getTag() != null && stack.getTag().equals(itemStack.getTag()))) {
                    int toRemove = Math.min(stack.getCount(), amountPrice - totalRemoved);
                    stack.shrink(toRemove);
                    totalRemoved += toRemove;

                    if (totalRemoved >= amountPrice) {
                        break;
                    }
                }
            }
        }

        if (pokemon.getMoveset().size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), attack);
            AtlantisUI.open(player, new SelectMovePage(pokemon), true);
        } else {
            pokemon.getMoveset().add(new Attack(attack));
            AtlantisUI.update(player, new SelectMovePage(pokemon));
        }

        player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getBuyAttack()
                .replace("%attack%", attack.getAttackName())
                .replace("%pokemon%", pokemon.getFormattedDisplayName().getString())
                .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);
    }
}
