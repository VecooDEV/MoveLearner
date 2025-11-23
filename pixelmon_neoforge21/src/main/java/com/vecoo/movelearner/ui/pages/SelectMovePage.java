package com.vecoo.movelearner.ui.pages;

import com.pixelmonmod.pixelmon.api.dialogue.DialogueFactory;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.moves.Moves;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.ServerConfig;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SelectMovePage extends SimpleGui {
    private final ServerConfig CONFIG = MoveLearner.getInstance().getConfig();
    private final GuiConfig GUI_CONFIG = MoveLearner.getInstance().getGuiConfig();

    private final Pokemon pokemon;
    private final MoveFilter filter;
    private final String search;
    private final List<ImmutableAttack> moves;
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
        addSearchButton();
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
    public List<ImmutableAttack> getMoves() {
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
    private List<ImmutableAttack> getFilteredAndSearchMoves() {
        return getFilteredMoves(this.pokemon.getForm().getMoves()).stream()
                .filter(move -> this.search.isEmpty()
                                || move.getAttackName().toLowerCase().startsWith(this.search.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void fillAllSlotsWithFiller() {
        if (GUI_CONFIG.isFillerChoiceMovesUI()) {
            IntStream.rangeClosed(45, 53)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void fillMoveSlots(@NotNull List<ImmutableAttack> moves) {
        int slot = 0;

        for (ImmutableAttack move : moves) {
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

    private void addSearchButton() {
        setSlot(47, Buttons.getSearchButton()
                .setCallback(clickType -> {
                    if (clickType.isRight) {
                        if (!this.search.isEmpty()) {
                            openPage(MoveFilter.ALL);
                        }
                    } else if (clickType.isLeft) {
                        DialogueFactory.builder()
                                .title(UtilChat.formatMessage(GUI_CONFIG.getSearchName()))
                                .description(UtilChat.formatMessage(GUI_CONFIG.getSearchLoreDialogue()))
                                .onlyAlphabeticalAndSpaceInput()
                                .maxInputLength(15)
                                .closeOnEscape()
                                .onClose(closedScreen -> openPage())
                                .buttons(Buttons.getDialogueAcceptButton()
                                        .onClick(dialogue -> MoveLearner.getInstance().getServer().execute(() ->
                                                openPage(dialogue.getInput().toLowerCase()))).build())
                                .sendTo(player);
                    }
                }));
    }

    private void changeFilterDown(@NotNull MoveFilter filter) {
        switch (filter) {
            case ALL -> openPage(MoveFilter.LEVEL);
            case LEVEL -> openPage(MoveFilter.TM_TR);
            case TM_TR -> openPage(CONFIG.isHmMove() ? MoveFilter.HM : MoveFilter.TUTOR);
            case HM -> openPage(MoveFilter.TUTOR);
            case TUTOR -> openPage(MoveFilter.TRANSFER);
            case TRANSFER -> openPage(CONFIG.isEggMove() ? MoveFilter.EGG : MoveFilter.ALL);
            case EGG -> openPage(MoveFilter.ALL);
        }
    }

    private void changeFilterUp(@NotNull MoveFilter filter) {
        switch (filter) {
            case ALL -> openPage(CONFIG.isEggMove() ? MoveFilter.EGG : MoveFilter.TRANSFER);
            case LEVEL -> openPage(MoveFilter.ALL);
            case TM_TR -> openPage(MoveFilter.LEVEL);
            case HM -> openPage(MoveFilter.TM_TR);
            case TUTOR -> openPage(CONFIG.isHmMove() ? MoveFilter.HM : MoveFilter.TM_TR);
            case TRANSFER -> openPage(MoveFilter.TUTOR);
            case EGG -> openPage(MoveFilter.TRANSFER);
        }
    }

    @NotNull
    private Set<ImmutableAttack> getFilteredMoves(@NotNull Moves moves) {
        return switch (this.filter) {
            case ALL -> moves.getAllMoves().stream()
                    .filter(move -> CONFIG.isHmMove() || !moves.getHMMoves().contains(move))
                    .filter(move -> CONFIG.isEggMove() || !moves.getEggMoves().contains(move))
                    .collect(Collectors.toSet());
            case LEVEL -> moves.getAllLevelUpMoves();
            case TM_TR -> Stream.concat(
                    moves.getTMMoves().stream(),
                    moves.getTRMoves().stream().map(ITechnicalMove::getAttack)).collect(Collectors.toSet());
            case HM -> moves.getHMMoves();
            case TUTOR -> moves.getTutorMoves();
            case TRANSFER -> moves.getTransferMoves();
            case EGG -> moves.getEggMoves();
        };
    }
}
