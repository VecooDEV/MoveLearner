package com.vecoo.movelearner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.ui.api.elements.GuiElementBuilder;
import com.vecoo.extralib.ui.api.gui.SimpleGui;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.ui.Buttons;
import com.vecoo.movelearner.ui.settings.MoveFilter;
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
        PlayerPartyStorage partyStorage = StorageProxy.getPartyNow(player);

        if (partyStorage == null) {
            return;
        }

        int startSlot = 10;

        for (Pokemon pokemon : partyStorage.getAll()) {
            if (startSlot == 13) {
                startSlot++;
            }

            setSlot(startSlot++, getPokemonSlotButton(pokemon));
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
        if (pokemon == null || pokemon.isEgg()) {
            return Buttons.getEmptyPokemonButton();
        }

        return Buttons.getPokemonButton(pokemon, player)
                .setCallback(() -> MoveLearnerFactoryUI.openPage(player, pokemon,
                        new SelectMovePage(player, pokemon, MoveFilter.ALL, "", 1)));
    }
}