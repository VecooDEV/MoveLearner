package com.vecoo.movelearner.ui;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
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

        for (Attack move : pokemon.getMoveset()) {
            MutableComponent moveLore = UtilChat.formatMessage(guiConfig.getMoveSymbol())
                    .copy()
                    .append(move.getMove().getTranslatedName());

            if (guiConfig.isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
                moveLore.append(UtilChat.formatMessage(guiConfig.getLocalizedMoveLore()
                        .replace("%move%", move.getMove().getAttackName())));
            }

            lore.add(moveLore);
        }

        return lore;
    }

    @NotNull
    public static List<Component> getMoveLore(@NotNull Pokemon pokemon, @NotNull ImmutableAttack move) {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();
        List<Component> lore = new ArrayList<>();

        Component type = move.getAttackType().value().name().copy()
                .withColor(move.getAttackType().value().color().getRGB());

        Component typeLine = UtilChat.formatMessage(guiConfig.getTypeLore())
                .copy()
                .append(type)
                .append(getCategoryIcon(move));

        lore.add(typeLine);
        lore.add(UtilChat.formatMessage(guiConfig.getPowerLore()
                .replace("%amount%", Utils.formatStatMove(move.getBasePower()))));
        lore.add(UtilChat.formatMessage(guiConfig.getAccuracyLore()
                .replace("%amount%", Utils.formatStatMove(move.getAccuracy()))));
        lore.add(UtilChat.formatMessage(guiConfig.getPpLore()
                .replace("%amount%", String.valueOf(move.getPPBase()))
                .replace("%maxAmount%", String.valueOf(move.getPPMax()))));

        int price = Utils.getMovePrice(pokemon, move);

        if (price > 0) {
            lore.add(UtilChat.formatMessage(MoveLearner.getInstance().getConfig().isUseCurrency() ? guiConfig.getPriceCurrencyLore()
                    .replace("%amount%", String.valueOf(price)) : guiConfig.getPriceItemLore()
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
        lore.add(createFilterLine("TM/TR", filter == MoveFilter.TM_TR));

        if (config.isHmMove()) {
            lore.add(createFilterLine("HM", filter == MoveFilter.HM));
        }

        lore.add(createFilterLine("Tutor", filter == MoveFilter.TUTOR));
        lore.add(createFilterLine("Transfer", filter == MoveFilter.TRANSFER));

        if (config.isEggMove()) {
            lore.add(createFilterLine("Egg", filter == MoveFilter.EGG));
        }

        return lore;
    }

    @NotNull
    private static String getCategoryIcon(@NotNull ImmutableAttack move) {
        return switch (move.getAttackCategory()) {
            case PHYSICAL -> " ⚔";
            case SPECIAL -> " ⚡";
            default -> " 🧪";
        };
    }

    @NotNull
    private static Component createFilterLine(@NotNull String text, boolean isActive) {
        return UtilChat.formatMessage(MoveLearner.getInstance().getGuiConfig().getFilterSymbol() + (isActive ? "&f" : "&7") + text);
    }
}