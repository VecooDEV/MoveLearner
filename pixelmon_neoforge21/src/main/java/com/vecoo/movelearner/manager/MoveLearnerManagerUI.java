package com.vecoo.movelearner.manager;

import com.pixelmonmod.pixelmon.api.pokemon.LearnMoveController;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.vecoo.extralib.ui.api.GuiHelpers;
import com.vecoo.extralib.util.TextUtil;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProviderRegistry;
import com.vecoo.movelearner.ui.pages.AcceptPage;
import com.vecoo.movelearner.ui.pages.SelectMovePage;
import com.vecoo.movelearner.util.Utils;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class MoveLearnerManagerUI {
    public static boolean validatePokemon(@NotNull Pokemon oldPokemon, @NotNull ServerPlayer player) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val partyStorage = StorageProxy.getPartyNow(player);

        if (partyStorage == null || partyStorage.get(oldPokemon.getUUID()) == null) {
            player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getNotPokemon()));
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

        val moveset = pokemon.getMoveset();
        val move = acceptPage.getMove();

        if (moveset.hasAttack(move)) {
            player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getAlreadyMove()
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%move%", move.getAttackName())));
            new SelectMovePage(acceptPage.getSelectMovePage()).openForce();
            return;
        }

        val currencyProvider = CurrencyProviderRegistry.get(serverConfig.getCurrencyType());

        if (currencyProvider == null) {
            player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getNotValidCurrency()
                    .replace("%currency%", MoveLearner.getInstance().getServerConfig().getCurrencyType())));
            return;
        }

        val price = Utils.getMovePrice(pokemon, move);

        if (!currencyProvider.buy(player, pokemon, move, price)) {
            return;
        }

        if (moveset.size() >= 4) {
            LearnMoveController.sendLearnMove(player, pokemon.getUUID(), move);
            new SelectMovePage(acceptPage.getSelectMovePage()).safeOpen(player);
        } else {
            moveset.add(new Attack(move));
            new SelectMovePage(acceptPage.getSelectMovePage()).openForce();
        }

        currencyProvider.successfulBuyMessage(player, pokemon, move, price);
    }
}
