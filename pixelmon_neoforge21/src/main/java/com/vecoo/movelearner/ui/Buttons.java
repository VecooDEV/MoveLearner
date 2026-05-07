package com.vecoo.movelearner.ui;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.dialogue.DialogueButton;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.ui.api.elements.GuiElementBuilder;
import com.vecoo.extralib.util.TextUtil;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Buttons {
    @NotNull
    public static GuiElementBuilder getPokemonButton(@NotNull Pokemon pokemon, @NotNull ServerPlayer player) {
        return new GuiElementBuilder(SpriteItemHelper.getPhoto(pokemon))
                .setName(pokemon.getTranslatedName())
                .setLore(ButtonLore.getPokemonMovesLore(pokemon, player))
                .removeRarity();
    }

    @NotNull
    public static GuiElementBuilder getMoveButton(@NotNull Pokemon pokemon, @NotNull ImmutableAttack move, @NotNull ServerPlayer player) {
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
    public static GuiElementBuilder getSearchButton() {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();
        List<Component> lore = Arrays.stream(guiConfig.getSearchLore().split("\\\\n"))
                .map(TextUtil::formatMessage)
                .toList();

        return new GuiElementBuilder(guiConfig.getSearchItem())
                .setFormattedName(guiConfig.getSearchName())
                .setLore(lore)
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

    @NotNull
    public static DialogueButton.Builder getDialogueAcceptButton() {
        return DialogueButton.builder()
                .text(TextUtil.formatMessage(MoveLearner.getInstance().getGuiConfig().getSearchAcceptDialogue()));
    }
}