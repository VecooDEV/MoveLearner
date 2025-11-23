package com.vecoo.movelearner.ui;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.google.common.collect.Lists;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.item.UtilItem;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class Buttons {
    @NotNull
    public static GuiElementBuilder getFillerButton() {
        return new GuiElementBuilder(UtilItem.parseItemCustomModel(MoveLearner.getInstance().getGuiConfig().getFillerItem()))
                .setName(UtilChat.formatMessage(""));
    }

    @NotNull
    public static GuiElementBuilder getInformationButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getInformationItem()))
                .setName(UtilChat.formatMessage(guiConfig.getInformationName()))
                .setLore(Lists.newArrayList(UtilChat.formatMessage(guiConfig.getInformationLore())));
    }

    @NotNull
    public static GuiElementBuilder getPokemonButton(@NotNull Pokemon pokemon, @NotNull ServerPlayer player) {
        return new GuiElementBuilder(PokemonItem.from(pokemon))
                .setName(pokemon.getDisplayName(false))
                .setLore(ButtonLore.getPokemonMovesLore(pokemon, player));
    }

    @NotNull
    public static GuiElementBuilder getEmptyPokemonButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getEmptyPokemonItem()))
                .setName(UtilChat.formatMessage(guiConfig.getEmptyPokemonName()));
    }

    @NotNull
    public static GuiElementBuilder getMoveButton(@NotNull Pokemon pokemon, @NotNull MoveTemplate move, @NotNull ServerPlayer player) {
        return new GuiElementBuilder(Utils.getItemTM(move))
                .setName(ButtonNames.getMoveName(move, player))
                .setLore(ButtonLore.getMoveLore(pokemon, move));
    }

    @NotNull
    public static GuiElementBuilder getPreviousPageButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getPreviousPageItem()))
                .setName(UtilChat.formatMessage(guiConfig.getPreviousPageName()));
    }

    @NotNull
    public static GuiElementBuilder getNextPageButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getNextPageItem()))
                .setName(UtilChat.formatMessage(guiConfig.getNextPageName()));
    }

    @NotNull
    public static GuiElementBuilder getFilterButton(@NotNull MoveFilter filter) {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getFilterItem()))
                .setName(UtilChat.formatMessage(guiConfig.getFilterName()))
                .setLore(ButtonLore.getFilterLore(filter));
    }

//    @NotNull
//    public static GuiElementBuilder getSearchButton() {
//        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();
//        List<Component> lore = Arrays.stream(guiConfig.getSearchLore().split("\\\\n"))
//                .map(UtilChat::formatMessage)
//                .toList();
//
//        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getSearchItem()))
//                .setName(UtilChat.formatMessage(guiConfig.getSearchName()))
//                .setLore(lore);
//    }

    @NotNull
    public static GuiElementBuilder getBackButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getBackItem()))
                .setName(UtilChat.formatMessage(guiConfig.getBackName()));
    }

    @NotNull
    public static GuiElementBuilder getCancelButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getCancelItem()))
                .setName(UtilChat.formatMessage(guiConfig.getCancelName()));
    }

    @NotNull
    public static GuiElementBuilder getComingButton() {
        return new GuiElementBuilder(UtilItem.parseItemCustomModel(MoveLearner.getInstance().getGuiConfig().getComingItem()));
    }

    @NotNull
    public static GuiElementBuilder getAcceptButton() {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        return new GuiElementBuilder(UtilItem.parseItemCustomModel(guiConfig.getAcceptItem()))
                .setName(UtilChat.formatMessage(guiConfig.getAcceptName()));
    }
}