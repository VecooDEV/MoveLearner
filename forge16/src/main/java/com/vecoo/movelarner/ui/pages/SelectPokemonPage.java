package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.config.GuiConfig;
import com.vecoo.movelarner.ui.ButtonLore;
import com.vecoo.movelarner.ui.ButtonName;
import com.vecoo.movelarner.ui.settings.PageFilter;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.*;
import de.waterdu.atlantis.util.entity.PlayerReference;
import net.minecraft.item.ItemStack;

public class SelectPokemonPage implements Page {
    @Override
    public PageOptions getPageOptions(PlayerReference player) {
        return PageOptions.builder()
                .title(MoveLearner.getInstance().getGui().getSelectPokemonTitle())
                .rows(3)
                .build();
    }

    @Override
    public void addButtons(PlayerReference player, ButtonCollector buttons) {
        MoveLearner instance = MoveLearner.getInstance();
        GuiConfig guiConfig = instance.getGui();

        if (guiConfig.isFillerChoicePokemonUI()) {
            ItemStack fillerItem = Utils.parsedItemStackCustomModel(guiConfig.getFillerItem());

            for (int i = 0; i < 27; i++) {
                buttons.collect(new Decoration(fillerItem, i));
            }
        }

        Pokemon[] party = StorageProxy.getParty(player.uuid()).getAll();

        for (int i = 10, pokemonIndex = 0; pokemonIndex < party.length; pokemonIndex++) {
            if (i == 13 && guiConfig.isInformationUI()) {
                buttons.collect(createButton(guiConfig.getInformationName(), guiConfig.getInformationLore(), guiConfig.getInformationItem(), i++));
                pokemonIndex--;
                continue;
            }

            Pokemon pokemon = party[pokemonIndex];

            if (pokemon == null || pokemon.isEgg()) {
                buttons.collect(createButton(guiConfig.getEmptyPokemonName(), null, guiConfig.getEmptyPokemonItem(), i++));
            } else {
                buttons.collect(Button.builder()
                        .directName(ButtonName.pokemonName(pokemon))
                        .directLore(ButtonLore.pokemonMoves(pokemon, player.entityDirect()))
                        .item(SpriteItemHelper.getPhoto(pokemon))
                        .index(i++)
                        .clickAction(clickData -> AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon, PageFilter.ALL, "")))
                        .build());
            }
        }
    }

    private Button createButton(String name, String lore, String itemModel, int index) {
        return Button.builder()
                .name(name)
                .lore(lore)
                .item(Utils.parsedItemStackCustomModel(itemModel))
                .index(index)
                .build();
    }
}