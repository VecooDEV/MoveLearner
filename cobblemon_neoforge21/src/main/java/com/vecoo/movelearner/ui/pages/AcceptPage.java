package com.vecoo.movelearner.ui.pages;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.IntStream;

public class AcceptPage extends SimpleGui {
    private final GuiConfig GUI_CONFIG = MoveLearner.getInstance().getGuiConfig();

    private final MoveTemplate move;
    private final SelectMovePage previousPage;

    public AcceptPage(@NotNull ServerPlayer player, @NotNull MoveTemplate move, @NotNull SelectMovePage previousPage) {
        super(MenuType.GENERIC_9x3, player, false);
        this.move = move;
        this.previousPage = previousPage;

        setTitle(UtilChat.formatMessage(GUI_CONFIG.getAcceptTitle()));
        setLockPlayerInventory(true);

        fillAllSlotsWithFiller();
        addCancelButton();
        addMoveButton();
        addComingButton();
        addPokemonButton();
        addAcceptButton();
    }

    public AcceptPage(@NotNull ServerPlayer player, @NotNull MoveTemplate move, @NotNull Pokemon pokemon,
                      @NotNull MoveFilter filter, @NotNull String search, int page) {
        this(player, move, new SelectMovePage(player, pokemon, filter, search, page));
    }

    @NotNull
    public MoveTemplate getMove() {
        return this.move;
    }

    @NotNull
    public SelectMovePage getPreviousPage() {
        return this.previousPage;
    }

    private void fillAllSlotsWithFiller() {
        if (GUI_CONFIG.isFillerSureUI()) {
            IntStream.rangeClosed(0, 26)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void addCancelButton() {
        setSlot(10, Buttons.getCancelButton()
                .setCallback(() -> MoveLearnerFactoryUI.openPage(player, this.previousPage.getPokemon(), this.previousPage)));
    }

    private void addMoveButton() {
        setSlot(12, Buttons.getMoveButton(Objects.requireNonNull(this.previousPage.getPokemon()), this.move, player));
    }

    private void addComingButton() {
        setSlot(13, Buttons.getComingButton());
    }

    private void addPokemonButton() {
        setSlot(14, Buttons.getPokemonButton(Objects.requireNonNull(this.previousPage.getPokemon()), player));
    }

    private void addAcceptButton() {
        setSlot(16, Buttons.getAcceptButton()
                .setCallback(() -> MoveLearnerFactoryUI.learnMoveItem(player, this)));
    }
}
