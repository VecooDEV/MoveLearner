package com.vecoo.movelearner.ui;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.currency.CurrencyProviderRegistry;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ButtonLore {
    @NotNull
    public static List<Component> getPokemonMovesLore(@NotNull Pokemon pokemon, @NotNull ServerPlayer player) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();
        List<Component> lore = new ArrayList<>();

        lore.add(UtilChat.formatMessage(guiConfig.getMovesLore()));

        for (Move move : pokemon.getMoveSet().getMoves()) {
            val moveLore = UtilChat.formatMessage(guiConfig.getMoveSymbol())
                    .copy()
                    .append(move.getDisplayName());

            if (guiConfig.isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
                moveLore.append(UtilChat.formatMessage(guiConfig.getLocalizedMoveLore()
                        .replace("%move%", move.getName())));
            }

            lore.add(moveLore);
        }

        return lore;
    }

    @NotNull
    public static List<Component> getMoveLore(@NotNull Pokemon pokemon, @NotNull MoveTemplate move) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();
        List<Component> lore = new ArrayList<>();

        val type = move.getElementalType().getDisplayName().copy()
                .withColor(move.getElementalType().getHue());

        val typeLine = UtilChat.formatMessage(guiConfig.getTypeLore())
                .copy()
                .append(type)
                .append(getCategoryIcon(move));

        lore.add(typeLine);
        lore.add(UtilChat.formatMessage(guiConfig.getPowerLore()
                .replace("%amount%", Utils.formatStatMove(move.getPower()))));
        lore.add(UtilChat.formatMessage(guiConfig.getAccuracyLore()
                .replace("%amount%", Utils.formatStatMove(move.getAccuracy()))));
        lore.add(UtilChat.formatMessage(guiConfig.getPpLore()
                .replace("%amount%", String.valueOf(move.getPp()))
                .replace("%maxAmount%", String.valueOf(move.getMaxPp()))));

        val price = Utils.getMovePrice(pokemon, move);

        if (price > 0) {
            val serverConfig = MoveLearner.getInstance().getServerConfig();
            val currencyProvider = CurrencyProviderRegistry.get(serverConfig.getCurrencyType());

            if (currencyProvider == null) {
                lore.add(UtilChat.formatMessage(guiConfig.getNotValidCurrency()
                        .replace("%currency%", serverConfig.getCurrencyType())));
            } else {
                lore.add(currencyProvider.lore(price));
            }
        } else {
            lore.add(UtilChat.formatMessage(guiConfig.getPriceFreeLore()));
        }

        return lore;
    }

    @NotNull
    public static List<Component> getFilterLore(@NotNull MoveFilter filter) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        List<Component> lore = new ArrayList<>();

        lore.add(createFilterLine(guiConfig.getFilterAll(), filter == MoveFilter.ALL));
        lore.add(createFilterLine(guiConfig.getFilterLevel(), filter == MoveFilter.LEVEL));
        lore.add(createFilterLine(guiConfig.getFilterTM(), filter == MoveFilter.TM));

        if (serverConfig.isLegacyMove()) {
            lore.add(createFilterLine(guiConfig.getFilterLegacy(), filter == MoveFilter.LEGACY));
        }

        lore.add(createFilterLine(guiConfig.getFilterTutor(), filter == MoveFilter.TUTOR));

        if (serverConfig.isSpecialMove()) {
            lore.add(createFilterLine(guiConfig.getFilterSpecial(), filter == MoveFilter.SPECIAL));
        }

        if (serverConfig.isEggMove()) {
            lore.add(createFilterLine(guiConfig.getFilterEgg(), filter == MoveFilter.EGG));
        }

        return lore;
    }

    @NotNull
    private static String getCategoryIcon(@NotNull MoveTemplate Move) {
        return switch (Move.getDamageCategory().getName()) {
            case "PHYSICAL" -> " ⚔";
            case "SPECIAL" -> " ⚡";
            default -> " 🧪";
        };
    }

    @NotNull
    private static Component createFilterLine(@NotNull String text, boolean isActive) {
        return UtilChat.formatMessage(MoveLearner.getInstance().getGuiConfig().getFilterSymbol() + (isActive ? "&f" : "&7") + text);
    }
}