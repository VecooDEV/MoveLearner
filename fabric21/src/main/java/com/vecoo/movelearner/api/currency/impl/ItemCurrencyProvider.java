package com.vecoo.movelearner.api.currency.impl;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.item.UtilItem;
import com.vecoo.extralib.player.UtilPlayer;
import com.vecoo.extralib.ui.api.GuiHelpers;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProvider;
import lombok.val;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ItemCurrencyProvider implements CurrencyProvider {
    @Override
    @NotNull
    public Component lore(int price) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return UtilChat.formatMessage(guiConfig.getPriceLore()
                .replace("%amount%", String.valueOf(price))
                .replace("%currency%", guiConfig.getItemCurrency()));
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val itemStack = UtilItem.parseItemCustomModel(serverConfig.getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotValidItem()));
            GuiHelpers.close(player);
            return false;
        }

        if (serverConfig.isItemStrongTags()) {
            if (UtilPlayer.countItemStack(player, itemStack) < price) {
                player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotCurrency()
                        .replace("%amount%", String.valueOf(price))
                        .replace("%currency%", localeConfig.getItemCurrency())));
                return false;
            }

            UtilPlayer.removeItemStack(player, itemStack, price);
        } else {
            if (UtilPlayer.countItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA) < price) {
                player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotCurrency()
                        .replace("%amount%", String.valueOf(price))
                        .replace("%currency%", localeConfig.getItemCurrency())));
                return false;
            }

            UtilPlayer.removeItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA, price);
        }

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
                    .replace("%currency%", localeConfig.getItemCurrency())));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())));
        }
    }
}
