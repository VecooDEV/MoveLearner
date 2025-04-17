package com.vecoo.movelarner.api.factory;

import com.pixelmonmod.pixelmon.api.pokemon.LearnMoveController;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.Moveset;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.config.LocaleConfig;
import com.vecoo.movelarner.ui.pages.SelectMovePage;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.AtlantisUI;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;

public class MoveLearnerFactoryUI {
    public static void learnMove(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack, String filter) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocale();

        if (StorageProxy.getParty(player.getUUID()).get(pokemon.getUUID()) == null) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getError()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        Moveset moveset = pokemon.getMoveset();

        if (moveset.hasAttack(attack)) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getError()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        ItemStack itemStack = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getConfig().getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getError()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        int amountPrice = Utils.movePrice(pokemon, attack);

        if (Utils.countItem(player, itemStack) < amountPrice) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getNotItems()
                    .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        if (amountPrice > 0) {
            Utils.removeItems(player, itemStack, amountPrice);
        }

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), attack);
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""), true);
        } else {
            moveset.add(new Attack(attack));
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
        }

        if (amountPrice > 0) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getBuyAttack()
                    .replace("%attack%", attack.getAttackName())
                    .replace("%pokemon%", pokemon.getLocalizedName())
                    .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);
        } else {
            player.sendMessage(TextUtils.asComponent(localeConfig.getBuyAttackFree()
                    .replace("%attack%", attack.getAttackName())
                    .replace("%pokemon%", pokemon.getLocalizedName())), Util.NIL_UUID);
        }
    }
}
