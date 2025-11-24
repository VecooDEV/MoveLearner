package com.vecoo.movelearner.ui.pages;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class SelectPokemonPage extends SimpleGui {
    private final GuiConfig GUI_CONFIG = MoveLearner.getInstance().getGuiConfig();

    public SelectPokemonPage(@NotNull ServerPlayer player) {
        super(MenuType.GENERIC_9x3, player, false);

        setTitle(UtilChat.formatMessage(GUI_CONFIG.getSelectPokemonTitle()));
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
        if (GUI_CONFIG.isFillerChoicePokemonUI()) {
            IntStream.rangeClosed(0, 26)
                    .forEach(i -> setSlot(i, Buttons.getFillerButton()));
        }
    }

    private void addInformationButton() {
        if (GUI_CONFIG.isInformationUI()) {
            setSlot(13, Buttons.getInformationButton());
        }
    }

    @NotNull
    private GuiElementBuilder getPokemonSlotButton(@Nullable Pokemon pokemon) {
        if (pokemon == null) {
            return Buttons.getEmptyPokemonButton();
        }

        return Buttons.getPokemonButton(pokemon)
                .setCallback(() -> MoveLearnerFactoryUI.openPage(player, pokemon,
                        new SelectMovePage(player, pokemon, MoveFilter.ALL, "", 1)));
    }
}