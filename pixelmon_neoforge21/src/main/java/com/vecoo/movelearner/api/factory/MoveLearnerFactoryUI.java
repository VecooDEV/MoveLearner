package com.vecoo.movelearner.api.factory;

import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import com.pixelmonmod.pixelmon.api.pokemon.LearnMoveController;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.Moveset;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.item.UtilItem;
import com.vecoo.extralib.player.UtilPlayer;
import com.vecoo.extralib.ui.api.GuiHelpers;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.events.LearnEvent;
import com.vecoo.movelearner.config.LocaleConfig;
import com.vecoo.movelearner.ui.pages.AcceptPage;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoveLearnerFactoryUI {
    public static void openPage(@NotNull ServerPlayer player, @Nullable Pokemon pokemon, @NotNull SimpleGui page) {
        PlayerPartyStorage partyStorage = StorageProxy.getPartyNow(player);

        if (partyStorage == null || pokemon == null || partyStorage.get(pokemon.getUUID()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocaleConfig().getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        page.openForce();
    }

    public static void learnMoveItem(@NotNull ServerPlayer player, @NotNull AcceptPage page) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocaleConfig();
        PlayerPartyStorage partyStorage = StorageProxy.getPartyNow(player);
        Pokemon pokemon = page.getPreviousPage().getPokemon();

        if (partyStorage == null || pokemon == null || partyStorage.get(pokemon.getUUID()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        Moveset moveset = pokemon.getMoveset();
        ImmutableAttack move = page.getMove();

        if (moveset.hasAttack(move)) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getAlreadyMove()
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%move%", move.getAttackName())));
            page.getPreviousPage().openForce();
            return;
        }

        ItemStack itemStack = UtilItem.parseItemCustomModel(MoveLearner.getInstance().getConfig().getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotValidItem()));
            GuiHelpers.close(player);
            return;
        }

        int price = Utils.getMovePrice(pokemon, move);

        if (!buyItem(player, pokemon, move, itemStack, price)) {
            return;
        }

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), move);
            page.getPreviousPage().safeOpen(player);
        } else {
            moveset.add(new Attack(move));
            page.getPreviousPage().openForce();
        }

        if (price > 0) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveItem()
                    .replace("%move%", move.getAttackName())
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%amount%", String.valueOf(price))));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getAttackName())
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())));
        }
    }

    public static void learnMoveItem(@NotNull ServerPlayer player, @NotNull ImmutableAttack move, @NotNull Pokemon pokemon,
                                     @NotNull MoveFilter filter, @NotNull String search, int price) {
        learnMoveItem(player, new AcceptPage(player, move, pokemon, filter, search, price));
    }

    public static void learnMoveCurrency(@NotNull ServerPlayer player, @NotNull AcceptPage page) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocaleConfig();
        PlayerPartyStorage partyStorage = StorageProxy.getPartyNow(player);
        Pokemon pokemon = page.getPreviousPage().getPokemon();

        if (partyStorage == null || pokemon == null || partyStorage.get(pokemon.getUUID()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        Moveset moveset = pokemon.getMoveset();
        ImmutableAttack move = page.getMove();

        if (moveset.hasAttack(move)) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getAlreadyMove()
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%move%", move.getAttackName())));
            page.getPreviousPage().openForce();
            return;
        }

        int price = Utils.getMovePrice(pokemon, move);

        if (!buyCurrency(player, pokemon, move, price)) {
            return;
        }

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), move);
            page.getPreviousPage().safeOpen(player);
        } else {
            moveset.add(new Attack(move));
            page.getPreviousPage().openForce();
        }

        if (price > 0) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveCurrency()
                    .replace("%move%", move.getAttackName())
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%amount%", String.valueOf(price))));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getAttackName())
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())));
        }
    }

    public static void learnMoveCurrency(@NotNull ServerPlayer player, @NotNull ImmutableAttack move, @NotNull Pokemon pokemon,
                                         @NotNull MoveFilter filter, @NotNull String search, int price) {
        learnMoveCurrency(player, new AcceptPage(player, move, pokemon, filter, search, price));
    }

    private static boolean buyItem(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move,
                                   @NotNull ItemStack itemStack, int amount) {
        if (MoveLearner.getInstance().getConfig().isItemStrongTags()) {
            if (UtilPlayer.countItemStack(player, itemStack) < amount) {
                sendNotItemsMessage(player, amount);
                return false;
            }

            if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyItem(player, pokemon, move, itemStack, amount)).isCanceled()) {
                return false;
            }

            UtilPlayer.removeItemStack(player, itemStack, amount);
        } else {
            if (UtilPlayer.countItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA) < amount) {
                sendNotItemsMessage(player, amount);
                return false;
            }

            if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyItem(player, pokemon, move, itemStack, amount)).isCanceled()) {
                return false;
            }

            UtilPlayer.removeItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA, amount);
        }

        return true;
    }

    private static boolean buyCurrency(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move, int price) {
        BankAccount bankAccount = BankAccountProxy.getBankAccountNow(player.getUUID());

        if (bankAccount == null) {
            player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocaleConfig().getError()));
            return false;
        }

        if (bankAccount.getBalance().intValue() < price) {
            sendNotCurrencyMessage(player, price);
            return false;
        }

        if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyCurrency(player, pokemon, move, price)).isCanceled()) {
            return false;
        }

        return bankAccount.take(price);
    }

    private static void sendNotItemsMessage(@NotNull ServerPlayer player, int amount) {
        player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocaleConfig().getNotItems()
                .replace("%amount%", String.valueOf(amount))));
    }

    private static void sendNotCurrencyMessage(@NotNull ServerPlayer player, int amount) {
        player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocaleConfig().getNotCurrency()
                .replace("%amount%", String.valueOf(amount))));
    }
}
