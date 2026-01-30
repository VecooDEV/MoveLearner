package com.vecoo.movelearner.ui.pages;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.service.MoveLearnerServiceUI;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
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

@Getter
public class SelectMovePage extends SimpleGui {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final MoveFilter filter;
    @NotNull
    private final String search;
    @NotNull
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

        setTitle(UtilChat.formatMessage(MoveLearner.getInstance().getGuiConfig().getSelectMoveTitle()));
        setLockPlayerInventory(true);

        int start = (page - 1) * 45;
        int end = Math.min(start + 45, this.moves.size());

        fillAllSlotsWithFiller();
        fillMoveSlots(this.moves.subList(start, end));
        addPreviousPageButton();
        addFilterButton();
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

    private void openPage(int newPage) {
        new SelectMovePage(player, this.pokemon, this.filter, this.search, newPage).openForce();
    }

    private void openPage(@NotNull MoveFilter filter) {
        new SelectMovePage(player, this.pokemon, filter, "", 1).openForce();
    }

    @NotNull
    private List<MoveTemplate> getFilteredAndSearchMoves() {
        return getFilteredMoves(this.pokemon.getForm().getMoves()).stream()
                .filter(move -> this.search.isEmpty()
                                || move.getName().toLowerCase().startsWith(this.search.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void fillAllSlotsWithFiller() {
        if (MoveLearner.getInstance().getGuiConfig().isFillerChoiceMovesUI()) {
            IntStream.rangeClosed(45, 53)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void fillMoveSlots(@NotNull List<MoveTemplate> moves) {
        int slot = 0;

        for (MoveTemplate move : moves) {
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

    private void changeFilterDown(@NotNull MoveFilter filter) {
        val order = getFilterOrder();

        openPage(order.get((order.indexOf(filter) + 1) % order.size()));
    }

    private void changeFilterUp(@NotNull MoveFilter filter) {
        val order = getFilterOrder();

        openPage(order.get((order.indexOf(filter) - 1 + order.size()) % order.size()));
    }

    @NotNull
    private List<MoveFilter> getFilterOrder() {
        val serverConfig = MoveLearner.getInstance().getServerConfig();
        List<MoveFilter> order = new ArrayList<>();

        order.add(MoveFilter.ALL);
        order.add(MoveFilter.LEVEL);
        order.add(MoveFilter.TM);

        if (serverConfig.isLegacyMove()) {
            order.add(MoveFilter.LEGACY);
        }

        order.add(MoveFilter.TUTOR);

        if (serverConfig.isSpecialMove()) {
            order.add(MoveFilter.SPECIAL);
        }

        if (serverConfig.isEggMove()) {
            order.add(MoveFilter.EGG);
        }

        return order;
    }

    @NotNull
    private Set<MoveTemplate> getFilteredMoves(@NotNull Learnset moves) {
        val serverConfig = MoveLearner.getInstance().getServerConfig();

        return switch (this.filter) {
            case ALL -> Utils.getAllMoves(moves).stream()
                    .filter(move -> serverConfig.isEggMove() || !moves.getEggMoves().contains(move))
                    .filter(move -> serverConfig.isLegacyMove() || !moves.getLegacyMoves().contains(move))
                    .filter(move -> serverConfig.isSpecialMove() || !moves.getSpecialMoves().contains(move))
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
            case LEVEL -> moves.getAllLegalMoves().stream()
                    .filter(move ->
                            moves.getLevelUpMoves()
                                    .values()
                                    .stream()
                                    .anyMatch(list -> list.contains(move)))
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
            case TM -> moves.getTmMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
            case LEGACY -> moves.getLegacyMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
            case TUTOR -> moves.getTutorMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
            case SPECIAL -> moves.getSpecialMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
            case EGG -> moves.getEggMoves().stream()
                    .filter(move -> !serverConfig.isHideAlreadyMove() || !Utils.isLearnedMove(this.pokemon, move))
                    .collect(Collectors.toSet());
        };
    }
}
