package com.vecoo.movelearner.impl;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.ui.api.GuiHelpers;
import com.vecoo.extralib.util.ItemUtil;
import com.vecoo.extralib.util.PlayerUtil;
import com.vecoo.extralib.util.TextUtil;
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

        return TextUtil.formatMessage(guiConfig.getPriceLore()
                .replace("%amount%", String.valueOf(price))
                .replace("%currency%", guiConfig.getItemCurrency()));
    }

    @Override
    public boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();
        val itemStack = ItemUtil.parseItemCustomModel(serverConfig.getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getNotValidItem()));
            GuiHelpers.close(player);
            return false;
        }

        if (serverConfig.isItemStrongTags()) {
            if (PlayerUtil.countItemStack(player, itemStack) < price) {
                player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getNotCurrency()
                        .replace("%amount%", String.valueOf(price))
                        .replace("%currency%", localeConfig.getItemCurrency())));
                return false;
            }

            PlayerUtil.removeItemStack(player, itemStack, price);
        } else {
            if (PlayerUtil.countItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA) < price) {
                player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getNotCurrency()
                        .replace("%amount%", String.valueOf(price))
                        .replace("%currency%", localeConfig.getItemCurrency())));
                return false;
            }

            PlayerUtil.removeItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA, price);
        }

        return true;
    }

    @Override
    public void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move, int price) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        if (price > 0) {
            player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getBuyMove()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())
                    .replace("%amount%", String.valueOf(price))
                    .replace("%currency%", localeConfig.getItemCurrency())));
        } else {
            player.sendSystemMessage(TextUtil.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getDisplayName().getString())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())));
        }
    }
}
