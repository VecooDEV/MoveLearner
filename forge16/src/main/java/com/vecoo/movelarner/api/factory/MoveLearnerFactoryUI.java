package com.vecoo.movelarner.api.factory;

import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import com.pixelmonmod.pixelmon.api.pokemon.LearnMoveController;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.Moveset;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.api.events.LearnEvent;
import com.vecoo.movelarner.config.LocaleConfig;
import com.vecoo.movelarner.ui.pages.SelectMovePage;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.AtlantisUI;
import de.waterdu.atlantis.ui.api.Page;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;

public class MoveLearnerFactoryUI {
    public static void openPageAndCheck(ServerPlayerEntity player, Pokemon pokemon, Page page) {
        if (StorageProxy.getParty(player.getUUID()).get(pokemon.getUUID()) == null) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getNotPokemon()
                    .replace("%pokemon%", pokemon.getLocalizedName())), Util.NIL_UUID);

            AtlantisUI.close(player);
            return;
        }

        AtlantisUI.open(player, page);
    }

    public static void learnMoveItem(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack, String filter) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocale();

        if (StorageProxy.getParty(player.getUUID()).get(pokemon.getUUID()) == null) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getNotPokemon()
                    .replace("%pokemon%", pokemon.getLocalizedName())), Util.NIL_UUID);

            AtlantisUI.close(player);
            return;
        }

        Moveset moveset = pokemon.getMoveset();

        if (moveset.hasAttack(attack)) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getAlreadyAttack()
                    .replace("%pokemon%", pokemon.getLocalizedName())
                    .replace("%attack%", attack.getAttackName())), Util.NIL_UUID);

            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
            return;
        }

        ItemStack itemStack = Utils.parseItemCustomModel(MoveLearner.getInstance().getConfig().getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getNotValidItem()), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        int amountPrice = Utils.movePrice(pokemon, attack);

        if (MinecraftForge.EVENT_BUS.post(new LearnEvent.BuyItem(player, pokemon, attack, itemStack, amountPrice))) {
            return;
        }

        buyItem(player, pokemon, itemStack, filter, amountPrice);

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), attack);
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""), true);
        } else {
            moveset.add(new Attack(attack));
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
        }

        if (amountPrice > 0) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getBuyAttackItem()
                    .replace("%attack%", attack.getAttackName())
                    .replace("%pokemon%", pokemon.getLocalizedName())
                    .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);
        } else {
            player.sendMessage(TextUtils.asComponent(localeConfig.getBuyAttackFree()
                    .replace("%attack%", attack.getAttackName())
                    .replace("%pokemon%", pokemon.getLocalizedName())), Util.NIL_UUID);
        }
    }

    private static void buyItem(ServerPlayerEntity player, Pokemon pokemon, ItemStack itemStack, String filter, int amountPrice) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocale();

        if (MoveLearner.getInstance().getConfig().isItemStrongTags()) {
            if (Utils.countItemStack(player, itemStack) < amountPrice) {
                player.sendMessage(TextUtils.asComponent(localeConfig.getNotItems()
                        .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);

                AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
                return;
            }

            if (amountPrice > 0) {
                Utils.removeItemStack(player, itemStack, amountPrice);
            }
        } else {
            if (Utils.countItemStackTag(player, itemStack, "CustomModelData") < amountPrice) {
                player.sendMessage(TextUtils.asComponent(localeConfig.getNotItems()
                        .replace("%amount%", String.valueOf(amountPrice))), Util.NIL_UUID);

                AtlantisUI.close(player);
                return;
            }

            if (amountPrice > 0) {
                Utils.removeItemStackTag(player, itemStack, "CustomModelData", amountPrice);
            }
        }
    }

    public static void learnMoveCurrency(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack, String filter) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocale();

        if (StorageProxy.getParty(player.getUUID()).get(pokemon.getUUID()) == null) {
            player.sendMessage(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getNotPokemon()
                    .replace("%pokemon%", pokemon.getLocalizedName())), Util.NIL_UUID);
            AtlantisUI.close(player);
            return;
        }

        Moveset moveset = pokemon.getMoveset();

        if (moveset.hasAttack(attack)) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getAlreadyAttack()
                    .replace("%pokemon%", pokemon.getLocalizedName())
                    .replace("%attack%", attack.getAttackName())), Util.NIL_UUID);
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
            return;
        }

        int price = Utils.movePrice(pokemon, attack);

        if (MinecraftForge.EVENT_BUS.post(new LearnEvent.BuyCurrency(player, pokemon, attack, price))) {
            return;
        }

        buyCurrency(player, pokemon, filter, price);

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), attack);
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""), true);
        } else {
            moveset.add(new Attack(attack));
            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
        }

        if (price > 0) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getBuyAttackCurrency()
                    .replace("%attack%", attack.getAttackName())
                    .replace("%pokemon%", pokemon.getLocalizedName())
                    .replace("%amount%", String.valueOf(price))), Util.NIL_UUID);
        } else {
            player.sendMessage(TextUtils.asComponent(localeConfig.getBuyAttackFree()
                    .replace("%attack%", attack.getAttackName())
                    .replace("%pokemon%", pokemon.getLocalizedName())), Util.NIL_UUID);
        }
    }

    private static void buyCurrency(ServerPlayerEntity player, Pokemon pokemon, String filter, int price) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocale();


        BankAccount bankAccount = BankAccountProxy.getBankAccountUnsafe(player.getUUID());

        if (bankAccount == null) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getError()), Util.NIL_UUID);
            return;
        }
        
        if (bankAccount.getBalance().intValue() < price) {
            player.sendMessage(TextUtils.asComponent(localeConfig.getNotCurrency()
                    .replace("%amount%", String.valueOf(price))), Util.NIL_UUID);

            AtlantisUI.open(player, new SelectMovePage(pokemon, filter, ""));
            return;
        }

        if (price > 0) {
            bankAccount.take(price);
        }
    }
}
