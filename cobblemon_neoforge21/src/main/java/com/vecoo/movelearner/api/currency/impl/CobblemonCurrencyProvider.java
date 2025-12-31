package com.vecoo.movelearner.api.currency.impl;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProvider;
import com.vecoo.movelearner.api.events.LearnEvent;
import fr.harmex.cobbledollars.common.utils.extensions.PlayerExtensionKt;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class CobblemonCurrencyProvider implements CurrencyProvider {
    @Override
    @NotNull
    public Component lore(int price) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return UtilChat.formatMessage(guiConfig.getPriceLore()
                .replace("%amount%", String.valueOf(price))
                .replace("%currency%", guiConfig.getCobblemonCurrency()));
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val balance = PlayerExtensionKt.getCobbleDollars(player);

        if (balance.intValue() < price) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotCurrency()
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getCobblemonCurrency())));
            return false;
        }

        if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyCurrency(player, pokemon, move, price)).isCanceled()) {
            return false;
        }

        PlayerExtensionKt.setCobbleDollars(player, balance.subtract(new BigDecimal(price).toBigInteger()));
        return true;
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        if (price > 0) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMove()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getCobblemonCurrency())));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())));
        }
    }
}
