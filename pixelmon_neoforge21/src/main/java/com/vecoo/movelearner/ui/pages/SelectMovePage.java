package com.vecoo.movelearner.ui.pages;

import com.pixelmonmod.pixelmon.api.dialogue.DialogueFactory;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.moves.Moves;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.service.MoveLearnerServiceUI;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import lombok.Getter;
import lombok.val;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public class SelectMovePage extends SimpleGui {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final MoveFilter filter;
    @NotNull
    private final String search;
    @NotNull
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

        setTitle(UtilChat.formatMessage(MoveLearner.getInstance().getGuiConfig().getSelectMoveTitle()));
        setLockPlayerInventory(true);

        int start = (page - 1) * 45;
        int end = Math.min(start + 45, this.moves.size());

        fillAllSlotsWithFiller();
        fillMoveSlots(this.moves.subList(start, end));
        addPreviousPageButton();
        addFilterButton();
        addSearchButton();
        addBackButton();
        addNextPageButton();
    }

    public SelectMovePage(@NotNull SelectMovePage selectMovePage) {
        this(selectMovePage.getPlayer(), selectMovePage.getPokemon(), selectMovePage.getFilter(),
                selectMovePage.getSearch(), selectMovePage.getPage());
    }

    @Override
    public void openForce() {
        if (MoveLearnerServiceUI.validatePokemon(this.pokemon, player)) {
            super.openForce();
        }
    }

    @Override
    public void safeOpen(@NotNull ServerPlayer player) {
        if (MoveLearnerServiceUI.validatePokemon(this.pokemon, player)) {
            super.safeOpen(player);
        }
    }

    private void openPage() {
        new SelectMovePage(this).openForce();
    }

    private void openPage(int newPage) {
        new SelectMovePage(player, this.pokemon, this.filter, this.search, newPage).openForce();
    }

    private void openPage(@NotNull MoveFilter filter) {
        new SelectMovePage(player, this.pokemon, filter, "", 1).openForce();
    }

    private void openPage(@NotNull String search) {
        new SelectMovePage(player, this.pokemon, MoveFilter.ALL, search, 1).openForce();
    }

    @NotNull
    private List<ImmutableAttack> getFilteredAndSearchMoves() {
        return getFilteredMoves(this.pokemon.getForm().getMoves()).stream()
                .filter(move -> this.search.isEmpty()
                                || move.getAttackName().toLowerCase().startsWith(this.search.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void fillAllSlotsWithFiller() {
        if (MoveLearner.getInstance().getGuiConfig().isFillerChoiceMovesUI()) {
            IntStream.rangeClosed(45, 53)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void fillMoveSlots(@NotNull List<ImmutableAttack> moves) {
        int slot = 0;

        for (ImmutableAttack move : moves) {
            setSlot(slot++, Buttons.getMoveButton(this.pokemon, move, player)
                    .setCallback(() -> new AcceptPage(player, move, this).openForce()));
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
                        val guiConfig = MoveLearner.getInstance().getGuiConfig();

                        DialogueFactory.builder()
                                .title(UtilChat.formatMessage(guiConfig.getSearchName()))
                                .description(UtilChat.formatMessage(guiConfig.getSearchLoreDialogue()))
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
        val order = getFilterList();

        openPage(order.get((order.indexOf(filter) + 1) % order.size()));
    }

    private void changeFilterUp(@NotNull MoveFilter filter) {
        val order = getFilterList();

        openPage(order.get((order.indexOf(filter) - 1 + order.size()) % order.size()));
    }

    @NotNull
    private List<MoveFilter> getFilterList() {
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        List<MoveFilter> list = new ArrayList<>();

        list.add(MoveFilter.ALL);
        list.add(MoveFilter.LEVEL);
        list.add(MoveFilter.TM_TR);

        if (serverConfig.isHmMove()) {
            list.add(MoveFilter.HM);
        }

        list.add(MoveFilter.TUTOR);
        list.add(MoveFilter.TRANSFER);

        if (serverConfig.isEggMove()) {
            list.add(MoveFilter.EGG);
        }

        return list;
    }

    @NotNull
    private Set<ImmutableAttack> getFilteredMoves(@NotNull Moves moves) {
        val serverConfig = MoveLearner.getInstance().getServerConfig();

        return switch (this.filter) {
            case ALL -> moves.getAllMoves().stream()
                    .filter(move -> serverConfig.isHmMove() || !moves.getHMMoves().contains(move))
                    .filter(move -> serverConfig.isEggMove() || !moves.getEggMoves().contains(move))
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
            case LEVEL -> moves.getAllLevelUpMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
            case TM_TR -> Stream.concat(moves.getTMMoves().stream(),
                            moves.getTRMoves().stream().map(ITechnicalMove::getAttack))
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
            case HM -> moves.getHMMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
            case TUTOR -> moves.getTutorMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
            case TRANSFER -> moves.getTransferMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
            case EGG -> moves.getEggMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !this.pokemon.getMoveset().hasAttack(move))
                    .collect(Collectors.toSet());
        };
    }
}
