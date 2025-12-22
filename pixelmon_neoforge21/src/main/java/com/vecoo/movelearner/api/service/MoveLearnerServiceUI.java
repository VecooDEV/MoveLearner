package com.vecoo.movelearner.api.service;

import com.pixelmonmod.pixelmon.api.pokemon.LearnMoveController;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.GuiHelpers;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProvider;
import com.vecoo.movelearner.api.currency.CurrencyProviderRegistry;
import com.vecoo.movelearner.ui.pages.SelectMovePage;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoveLearnerServiceUI {
    public static void openPage(@NotNull ServerPlayer player, @Nullable Pokemon pokemon, @NotNull SimpleGui page) {
        val partyStorage = StorageProxy.getPartyNow(player);

        if (partyStorage == null || pokemon == null || partyStorage.get(pokemon.getUUID()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocaleConfig().getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        page.openForce();
    }

    public static void learnMove(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move,
                                 @NotNull MoveFilter filter, @NotNull String search, int page) {
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val partyStorage = StorageProxy.getPartyNow(player);

        if (partyStorage == null || partyStorage.get(pokemon.getUUID()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        val moveset = pokemon.getMoveset();

        if (moveset.hasAttack(move)) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getAlreadyMove()
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%move%", move.getAttackName())));
            new SelectMovePage(player, pokemon, filter, search, page).openForce();
            return;
        }

        CurrencyProvider currencyProvider = CurrencyProviderRegistry.get(serverConfig.getCurrencyType());

        if (currencyProvider == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotValidCurrency()
                    .replace("%currency%", MoveLearner.getInstance().getServerConfig().getCurrencyType())));
            return;
        }

        val price = Utils.getMovePrice(pokemon, move);

        if (!currencyProvider.buy(player, pokemon, move, price)) {
            return;
        }

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), move);
            new SelectMovePage(player, pokemon, filter, search, page).safeOpen(player);
        } else {
            moveset.add(new Attack(move));
            new SelectMovePage(player, pokemon, filter, search, page).openForce();
        }

        currencyProvider.successfulBuyMessage(player, pokemon, move, price);
    }
}
