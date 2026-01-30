package com.vecoo.movelearner.api.service;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.GuiHelpers;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProviderRegistry;
import com.vecoo.movelearner.ui.pages.AcceptPage;
import com.vecoo.movelearner.ui.pages.SelectMovePage;
import com.vecoo.movelearner.util.Utils;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class MoveLearnerServiceUI {
    public static boolean validatePokemon(@NotNull Pokemon oldPokemon, @NotNull ServerPlayer player) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        if (Cobblemon.INSTANCE.getStorage().getParty(player).get(oldPokemon.getUuid()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotPokemon()));
            GuiHelpers.close(player);
            return false;
        }

        return true;
    }

    public static void learnMove(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull AcceptPage acceptPage) {
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        if (!validatePokemon(pokemon, player)) {
            return;
        }

        val moveset = pokemon.getMoveSet();
        val move = acceptPage.getMove();

        if (Utils.isLearnedMove(pokemon, move)) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getAlreadyMove()
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())
                    .replace("%move%", move.getDisplayName().getString())));
            new SelectMovePage(acceptPage.getSelectMovePage()).openForce();
            return;
        }

        val currencyProvider = CurrencyProviderRegistry.get(serverConfig.getCurrencyType());

        if (currencyProvider == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotValidCurrency()
                    .replace("%currency%", MoveLearner.getInstance().getServerConfig().getCurrencyType())));
            return;
        }

        val price = Utils.getMovePrice(pokemon, move);

        if (!currencyProvider.buy(player, pokemon, move, price)) {
            return;
        }

        if (!moveset.hasSpace()) {
            pokemon.getBenchedMoves().add(new BenchedMove(move, 0));
        } else {
            moveset.add(move.create());
        }

        new SelectMovePage(acceptPage.getSelectMovePage()).openForce();
        currencyProvider.successfulBuyMessage(player, pokemon, move, price);
    }
}
