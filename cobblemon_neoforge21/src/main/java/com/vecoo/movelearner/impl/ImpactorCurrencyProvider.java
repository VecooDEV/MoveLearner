package com.vecoo.movelearner.impl;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProvider;
import com.vecoo.movelearner.api.events.LearnEvent;
import lombok.val;
import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class ImpactorCurrencyProvider implements CurrencyProvider {
    private static final EconomyService ECONOMY_SERVICE = EconomyService.instance();
    private static final Currency CURRENCY = ECONOMY_SERVICE.currencies().primary();

    @Override
    @NotNull
    public Component lore(int price) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return UtilChat.formatMessage(guiConfig.getPriceLore()
                .replace("%amount%", String.valueOf(price))
                .replace("%currency%", guiConfig.getImpactorCurrency()));
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val account = getAccount(player.getUUID());

        if (account.balance().intValue() < price) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotCurrency()
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getImpactorCurrency())));
            return false;
        }

        if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyCurrency(player, pokemon, move, price)).isCanceled()) {
            return false;
        }

        return account.withdraw(new BigDecimal(price)).successful();
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        if (price > 0) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMove()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getImpactorCurrency())));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())));
        }
    }

    @NotNull
    private Account getAccount(@NotNull UUID playerUUID) {
        if (!ECONOMY_SERVICE.hasAccount(playerUUID).join()) {
            return ECONOMY_SERVICE.account(playerUUID).join();
        }

        return ECONOMY_SERVICE.account(CURRENCY, playerUUID).join();
    }
}

