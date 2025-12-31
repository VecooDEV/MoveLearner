package com.vecoo.movelearner.ui.pages;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
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

import java.util.stream.IntStream;

public class AcceptPage extends SimpleGui {
    private final GuiConfig GUI_CONFIG = MoveLearner.getInstance().getGuiConfig();

    @Getter
    @NotNull
    private final MoveTemplate move;
    @Getter
    @NotNull
    private final SelectMovePage selectMovePage;

    public AcceptPage(@NotNull ServerPlayer player, @NotNull MoveTemplate move, @NotNull SelectMovePage selectMovePage) {
        super(MenuType.GENERIC_9x3, player, false);
        this.move = move;
        this.selectMovePage = selectMovePage;

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
                .setCallback(() -> MoveLearnerServiceUI.openPage(player, this.selectMovePage.getPokemon(), this.selectMovePage)));
    }

    private void addMoveButton() {
        setSlot(12, Buttons.getMoveButton(this.selectMovePage.getPokemon(), this.move, player));
    }

    private void addComingButton() {
        setSlot(13, Buttons.getComingButton());
    }

    private void addPokemonButton() {
        setSlot(14, Buttons.getPokemonButton(this.selectMovePage.getPokemon(), player));
    }

    private void addAcceptButton() {
        setSlot(16, Buttons.getAcceptButton()
                .setCallback(() -> MoveLearnerServiceUI.learnMove(player, this.selectMovePage.getPokemon(), this)));
    }
}
