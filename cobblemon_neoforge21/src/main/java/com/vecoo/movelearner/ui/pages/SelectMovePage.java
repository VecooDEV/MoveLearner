package com.vecoo.movelearner.ui.pages;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.ServerConfig;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectMovePage extends SimpleGui {
    private final ServerConfig CONFIG = MoveLearner.getInstance().getConfig();
    private final GuiConfig GUI_CONFIG = MoveLearner.getInstance().getGuiConfig();

    private final Pokemon pokemon;
    private final MoveFilter filter;
    private final String search;
    private final List<MoveTemplate> moves;
    private final int page;
    private final int totalPages;

    public SelectMovePage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveFilter filter,
                          @NotNull String search, int page) {
        super(MenuType.GENERIC_9x6, player, false);
        this.pokemon = pokemon;
        this.filter = filter;
        this.search = search;
        this.page = Math.max(1, page);
        this.moves = getFilteredAndSearchMoves();
        this.totalPages = Math.max(1, (this.moves.size() + 44) / 45);

        setTitle(UtilChat.formatMessage(GUI_CONFIG.getSelectMoveTitle()));
        setLockPlayerInventory(true);

        int start = (page - 1) * 45;
        int end = Math.min(start + 45, this.moves.size());

        fillMoveSlots(this.moves.subList(start, end));
        fillAllSlotsWithFiller();
        addPreviousPageButton();
        addFilterButton();
//        addSearchButton();
        addBackButton();
        addNextPageButton();
    }

    @Nullable
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public MoveFilter getFilter() {
        return this.filter;
    }

    @NotNull
    public String getSearch() {
        return this.search;
    }

    @NotNull
    public List<MoveTemplate> getMoves() {
        return this.moves;
    }

    public int getPage() {
        return this.page;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    private void openPage() {
        MoveLearnerFactoryUI.openPage(player, this.pokemon, new SelectMovePage(player, this.pokemon, this.filter, this.search, this.page));
    }

    private void openPage(int newPage) {
        MoveLearnerFactoryUI.openPage(player, this.pokemon, new SelectMovePage(player, this.pokemon, this.filter, this.search, newPage));
    }

    private void openPage(@NotNull MoveFilter filter) {
        MoveLearnerFactoryUI.openPage(player, this.pokemon, new SelectMovePage(player, this.pokemon, filter, "", 1));
    }

    private void openPage(@NotNull String search) {
        MoveLearnerFactoryUI.openPage(player, this.pokemon, new SelectMovePage(player, this.pokemon, MoveFilter.ALL, search, 1));
    }

    @NotNull
    private List<MoveTemplate> getFilteredAndSearchMoves() {
        return getFilteredMoves(this.pokemon.getForm().getMoves()).stream()
                .filter(move -> this.search.isEmpty()
                                || move.getName().toLowerCase().startsWith(this.search.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void fillAllSlotsWithFiller() {
        if (GUI_CONFIG.isFillerChoiceMovesUI()) {
            IntStream.rangeClosed(45, 53)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void fillMoveSlots(@NotNull List<MoveTemplate> moves) {
        int slot = 0;

        for (MoveTemplate move : moves) {
            setSlot(slot++, Buttons.getMoveButton(this.pokemon, move, player)
                    .setCallback(() -> MoveLearnerFactoryUI.openPage(player, this.pokemon, new AcceptPage(player, move, this))));
        }
    }

    private void addPreviousPageButton() {
        if (this.page > 1) {
            setSlot(45, Buttons.getPreviousPageButton()
                    .setCallback(() -> openPage(this.page - 1)));
        }
    }

    private void addNextPageButton() {
        if (this.page < this.totalPages) {
            setSlot(53, Buttons.getNextPageButton()
                    .setCallback(() -> openPage(this.page + 1)));
        }
    }

    private void addFilterButton() {
        setSlot(46, Buttons.getFilterButton(this.filter)
                .setCallback(clickType -> {
                    if (clickType.isRight) {
                        changeFilterUp(this.filter);
                    } else if (clickType.isLeft) {
                        changeFilterDown(this.filter);
                    }
                }));
    }

    private void addBackButton() {
        setSlot(49, Buttons.getBackButton()
                .setCallback(() -> new SelectPokemonPage(player).openForce()));
    }

//    private void addSearchButton() {
//        setSlot(47, Buttons.getSearchButton()
//                .setCallback(clickType -> {
//                    if (clickType.isRight) {
//                        if (!this.search.isEmpty()) {
//                            openPage(MoveFilter.ALL);
//                        }
//                    } else if (clickType.isLeft) {
//                        DialogueFactory.builder()
//                                .title(UtilChat.formatMessage(GUI_CONFIG.getSearchName()))
//                                .description(UtilChat.formatMessage(GUI_CONFIG.getSearchLoreDialogue()))
//                                .onlyAlphabeticalAndSpaceInput()
//                                .maxInputLength(15)
//                                .closeOnEscape()
//                                .onClose(closedScreen -> openPage())
//                                .buttons(Buttons.getDialogueAcceptButton()
//                                        .onClick(dialogue -> MoveLearner.getInstance().getServer().execute(() ->
//                                                openPage(dialogue.getInput().toLowerCase()))).build())
//                                .sendTo(player);
//                        SignGui
//                    }
//                }));
//    }

    private void changeFilterDown(@NotNull MoveFilter filter) {
        List<MoveFilter> order = getFilterOrder();
        openPage(order.get((order.indexOf(filter) + 1) % order.size()));
    }

    private void changeFilterUp(@NotNull MoveFilter filter) {
        List<MoveFilter> order = getFilterOrder();
        openPage(order.get((order.indexOf(filter) - 1 + order.size()) % order.size()));
    }

    @NotNull
    private List<MoveTemplate> getFilteredMoves(@NotNull Learnset moves) {
        return switch (this.filter) {
            case ALL -> Utils.getAllMoves(moves).stream()
                    .filter(move -> CONFIG.isEggMove() || !moves.getEggMoves().contains(move))
                    .filter(move -> CONFIG.isLegacyMove() || !moves.getLegacyMoves().contains(move))
                    .filter(move -> CONFIG.isSpecialMove() || !moves.getSpecialMoves().contains(move))
                    .collect(Collectors.toList());
            case LEVEL -> moves.getAllLegalMoves().stream()
                    .filter(move ->
                            moves.getLevelUpMoves()
                                    .values()
                                    .stream()
                                    .anyMatch(list -> list.contains(move)))
                    .collect(Collectors.toList());
            case TM -> moves.getTmMoves();
            case LEGACY -> moves.getLegacyMoves();
            case TUTOR -> moves.getTutorMoves();
            case SPECIAL -> moves.getSpecialMoves();
            case EGG -> moves.getEggMoves();
        };
    }

    private List<MoveFilter> getFilterOrder() {
        List<MoveFilter> order = new ArrayList<>();

        order.add(MoveFilter.ALL);
        order.add(MoveFilter.LEVEL);
        order.add(MoveFilter.TM);

        if (CONFIG.isLegacyMove()) {
            order.add(MoveFilter.LEGACY);
        }

        order.add(MoveFilter.TUTOR);

        if (CONFIG.isSpecialMove()) {
            order.add(MoveFilter.SPECIAL);
        }

        if (CONFIG.isEggMove()) {
            order.add(MoveFilter.EGG);
        }

        return order;
    }
}
