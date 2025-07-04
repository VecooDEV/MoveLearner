package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelarner.config.GuiConfig;
import com.vecoo.movelarner.ui.ButtonLore;
import com.vecoo.movelarner.ui.ButtonName;
import com.vecoo.movelarner.ui.Buttons;
import com.vecoo.movelarner.ui.settings.PageFilter;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.*;
import de.waterdu.atlantis.util.entity.PlayerReference;
import net.minecraft.item.ItemStack;

public class SelectPokemonPage implements Page {
    @Override
    public PageOptions getPageOptions(PlayerReference player) {
        return PageOptions.builder()
                .title(MoveLearner.getInstance().getGuiConfig().getSelectPokemonTitle())
                .rows(3)
                .build();
    }

    @Override
    public void addButtons(PlayerReference player, ButtonCollector buttons) {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();

        if (guiConfig.isFillerChoicePokemonUI()) {
            ItemStack fillerItem = Utils.parseItemCustomModel(guiConfig.getFillerItem());

            for (int i = 0; i < 27; i++) {
                buttons.collect(new Decoration(fillerItem, i));
            }
        }

        Pokemon[] party = StorageProxy.getParty(player.uuid()).getAll();

        int i = 10;

        for (int pokemonIndex = 0; pokemonIndex < party.length; pokemonIndex++) {
            if (i == 13 && guiConfig.isInformationUI()) {
                buttons.collect(Button.builder()
                        .name(guiConfig.getInformationName())
                        .lore(guiConfig.getInformationLore())
                        .item(Utils.parseItemCustomModel(guiConfig.getInformationItem()))
                        .index(i++));
                pokemonIndex--;
                continue;
            }

            Pokemon pokemon = party[pokemonIndex];

            if (pokemon == null || pokemon.isEgg()) {
                buttons.collect(Buttons.createButton(i++, guiConfig.getEmptyPokemonName(), guiConfig.getEmptyPokemonItem()));
            } else {
                buttons.collect(Button.builder()
                        .directName(ButtonName.pokemonName(pokemon))
                        .directLore(ButtonLore.pokemonMoves(pokemon, player.entityDirect()))
                        .item(SpriteItemHelper.getPhoto(pokemon))
                        .index(i++)
                        .clickAction(clickData -> MoveLearnerFactoryUI.openPageAndCheck(clickData.entity(), pokemon, new SelectMovePage(pokemon, PageFilter.ALL, "")))
                        .build());
            }
        }
    }
}