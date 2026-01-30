package com.vecoo.movelearner.ui.pages;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.elements.GuiElementBuilder;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.service.MoveLearnerServiceUI;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class SelectPokemonPage extends SimpleGui {
    public SelectPokemonPage(@NotNull ServerPlayer player) {
        super(MenuType.GENERIC_9x3, player, false);

        setTitle(UtilChat.formatMessage(MoveLearner.getInstance().getGuiConfig().getSelectPokemonTitle()));
        setLockPlayerInventory(true);

        fillAllSlotsWithFiller();
        fillPokemonSlots();
        addInformationButton();
    }

    private void fillPokemonSlots() {
        int startSlot = 10;

        for (int i = 0; i < 6; i++) {
            if (startSlot == 13) {
                startSlot++;
            }

            setSlot(startSlot++, getPokemonSlotButton(Cobblemon.INSTANCE.getStorage().getParty(player).get(i)));
        }
    }

    private void fillAllSlotsWithFiller() {
        if (MoveLearner.getInstance().getGuiConfig().isFillerChoicePokemonUI()) {
            IntStream.rangeClosed(0, 26)
                    .forEach(slot -> setSlot(slot, Buttons.getFillerButton()));
        }
    }

    private void addInformationButton() {
        if (MoveLearner.getInstance().getGuiConfig().isInformationUI()) {
            setSlot(13, Buttons.getInformationButton());
        }
    }

    @NotNull
    private GuiElementBuilder getPokemonSlotButton(@Nullable Pokemon pokemon) {
        if (pokemon == null) {
            return Buttons.getEmptyPokemonButton();
        }

        return Buttons.getPokemonButton(pokemon, player)
                .setCallback(() -> MoveLearnerServiceUI.openPage(player, pokemon,
                        new SelectMovePage(player, pokemon, MoveFilter.ALL, "", 1)));
    }
}