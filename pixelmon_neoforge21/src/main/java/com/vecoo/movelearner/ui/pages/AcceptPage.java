package com.vecoo.movelearner.ui.pages;

import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.service.MoveLearnerServiceUI;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.ui.Buttons;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.IntStream;

@Getter
public class AcceptPage extends SimpleGui {
    private final GuiConfig GUI_CONFIG = MoveLearner.getInstance().getGuiConfig();

    @NotNull
    private final ImmutableAttack move;
    @NotNull
    private final SelectMovePage previousPage;

    public AcceptPage(@NotNull ServerPlayer player, @NotNull ImmutableAttack move, @NotNull SelectMovePage previousPage) {
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

    private void fillAllSlotsWithFiller() {
        if (GUI_CONFIG.isFillerSureUI()) {
            IntStream.rangeClosed(0, 26)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void addCancelButton() {
        setSlot(10, Buttons.getCancelButton()
                .setCallback(() -> MoveLearnerServiceUI.openPage(player, this.previousPage.getPokemon(), this.previousPage)));
    }

    private void addMoveButton() {
        setSlot(12, Buttons.getMoveButton(Objects.requireNonNull(this.previousPage.getPokemon()), this.move, player));
    }

    private void addComingButton() {
        setSlot(13, Buttons.getComingButton());
    }

    private void addPokemonButton() {
        setSlot(14, Buttons.getPokemonButton(this.previousPage.getPokemon(), player));
    }

    private void addAcceptButton() {
        setSlot(16, Buttons.getAcceptButton()
                .setCallback(() -> MoveLearnerServiceUI.learnMove(player, this.previousPage.getPokemon(), this.move,
                        this.previousPage.getFilter(), this.previousPage.getSearch(), this.previousPage.getPage())));
    }
}
