package com.vecoo.movelearner.ui;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.ServerConfig;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ButtonLore {
    @NotNull
    public static List<Component> getPokemonMovesLore(@NotNull Pokemon pokemon, @NotNull ServerPlayer player) {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();
        List<Component> lore = new ArrayList<>();

        lore.add(UtilChat.formatMessage(guiConfig.getMovesLore()));

        for (Move move : pokemon.getMoveSet().getMoves()) {
            MutableComponent moveLore = UtilChat.formatMessage(guiConfig.getMoveSymbol())
                    .copy()
                    .append(move.getName());

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
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();
        List<Component> lore = new ArrayList<>();

        Component type = move.getElementalType().getDisplayName().copy()
                .withColor(move.getElementalType().getHue());

        Component typeLine = UtilChat.formatMessage(guiConfig.getTypeLore())
                .copy()
                .append(type)
                .append(getCategoryIcon(move));

        lore.add(typeLine);
        lore.add(UtilChat.formatMessage(guiConfig.getPowerLore()
                .replace("%amount%", Utils.getStatMoveFormat(move.getPower()))));
        lore.add(UtilChat.formatMessage(guiConfig.getAccuracyLore()
                .replace("%amount%", Utils.getStatMoveFormat(move.getAccuracy()))));
        lore.add(UtilChat.formatMessage(guiConfig.getPpLore()
                .replace("%amount%", String.valueOf(move.getPp()))
                .replace("%maxAmount%", String.valueOf(move.getMaxPp()))));

        int price = Utils.getMovePrice(pokemon, move);

        if (price > 0) {
            lore.add(UtilChat.formatMessage(guiConfig.getPriceItemLore()
                    .replace("%amount%", String.valueOf(price))));
        } else {
            lore.add(UtilChat.formatMessage(guiConfig.getPriceFreeLore()));
        }

        return lore;
    }

    @NotNull
    public static List<Component> getFilterLore(@NotNull MoveFilter filter) {
        ServerConfig config = MoveLearner.getInstance().getConfig();

        List<Component> lore = new ArrayList<>();

        lore.add(createFilterLine("All", filter == MoveFilter.ALL));
        lore.add(createFilterLine("Level", filter == MoveFilter.LEVEL));
        lore.add(createFilterLine("TM", filter == MoveFilter.TM));

        if (config.isLegacyMove()) {
            lore.add(createFilterLine("Legacy", filter == MoveFilter.LEGACY));
        }

        lore.add(createFilterLine("Tutor", filter == MoveFilter.TUTOR));

        if (config.isSpecialMove()) {
            lore.add(createFilterLine("Special", filter == MoveFilter.SPECIAL));
        }

        if (config.isEggMove()) {
            lore.add(createFilterLine("Egg", filter == MoveFilter.EGG));
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