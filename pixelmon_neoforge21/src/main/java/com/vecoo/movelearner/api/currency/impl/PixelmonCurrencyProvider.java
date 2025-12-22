package com.vecoo.movelearner.api.currency.impl;

import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProvider;
import com.vecoo.movelearner.api.events.LearnEvent;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

public class PixelmonCurrencyProvider implements CurrencyProvider {
    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val bankAccount = BankAccountProxy.getBankAccountNow(player.getUUID());

        if (bankAccount == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getError()));
            return false;
        }

        if (bankAccount.getBalance().intValue() < price) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotCurrency()
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getPixelmonCurrency())));
            return false;
        }

        if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyCurrency(player, pokemon, move, price)).isCanceled()) {
            return false;
        }

        return bankAccount.take(price);
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        if (price > 0) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMove()
                    .replace("%move%", move.getAttackName())
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getPixelmonCurrency())));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getAttackName())
                    .replace("%pokemon%", pokemon.getTranslatedName().getString())));
        }
    }
}
