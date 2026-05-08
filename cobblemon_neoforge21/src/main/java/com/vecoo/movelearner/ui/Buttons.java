package com.vecoo.movelearner.ui;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.common.collect.Lists;
import com.vecoo.extralib.ui.api.elements.GuiElementBuilder;
import com.vecoo.extralib.util.TextUtil;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class Buttons {
    @NotNull
    public static GuiElementBuilder getPokemonButton(@NotNull Pokemon pokemon, @NotNull ServerPlayer player) {
        return new GuiElementBuilder(PokemonItem.from(pokemon))
                .setName(pokemon.getDisplayName(false))
                .setLore(ButtonLore.getPokemonMovesLore(pokemon, player))
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getMoveButton(@NotNull Pokemon pokemon, @NotNull MoveTemplate move, @NotNull ServerPlayer player) {
        return new GuiElementBuilder(Utils.getItemTM(move))
                .setName(ButtonNames.getMoveName(move, player))
                .setLore(ButtonLore.getMoveLore(pokemon, move))
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getFillerButton() {
        return new GuiElementBuilder(MoveLearner.getInstance().getGuiConfig().getFillerItem())
                .setName(Component.empty())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getInformationButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getInformationItem())
                .setFormattedName(guiConfig.getInformationName())
                .setLore(Lists.newArrayList(TextUtil.formatMessage(guiConfig.getInformationLore())))
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getEmptyPokemonButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getEmptyPokemonItem())
                .setFormattedName(guiConfig.getEmptyPokemonName())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getPreviousPageButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getPreviousPageItem())
                .setFormattedName(guiConfig.getPreviousPageName())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getNextPageButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getNextPageItem())
                .setFormattedName(guiConfig.getNextPageName())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getFilterButton(@NotNull MoveFilter filter) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getFilterItem())
                .setFormattedName(guiConfig.getFilterName())
                .setLore(ButtonLore.getFilterLore(filter))
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getBackButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getBackItem())
                .setFormattedName(guiConfig.getBackName())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getCancelButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getCancelItem())
                .setFormattedName(guiConfig.getCancelName())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getComingButton() {
        return new GuiElementBuilder(MoveLearner.getInstance().getGuiConfig().getComingItem())
                .setName(Component.empty())
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getAcceptButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(guiConfig.getAcceptItem())
                .setFormattedName(guiConfig.getAcceptName())
                .removeRarity();
    }
}