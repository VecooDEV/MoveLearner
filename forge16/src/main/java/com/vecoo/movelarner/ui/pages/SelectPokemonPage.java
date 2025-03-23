package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.ui.ButtonLore;
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
        if (MoveLearner.getInstance().getConfig().isFillerUI()) {
            ItemStack fillerItem = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getFillerItem());

            for (int i = 0; i < 27; i++) {
                buttons.collect(new Decoration(fillerItem, i));
            }
        }

        int i = 10;
        ItemStack emptyPokemonItem = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getEmptyPokemonItem());

        for (Pokemon pokemon : StorageProxy.getParty(player.uuid()).getAll()) {
            if (i == 13) {
                if (MoveLearner.getInstance().getConfig().isInformationUI()) {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getInformationName())
                            .lore(MoveLearner.getInstance().getGui().getInformationLore())
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getInformationItem()))
                            .index(i++)
                            .build());
                } else {
                    i++;
                }
            }

            if (pokemon == null || pokemon.isEgg()) {
                buttons.collect(Button.builder()
                        .name(MoveLearner.getInstance().getGui().getEmptyPokemonName())
                        .item(emptyPokemonItem)
                        .index(i++)
                        .build());
            } else {
                buttons.collect(Button.builder()
                        .directName(pokemon.getTranslatedName())
                        .directLore(ButtonLore.pokemon(pokemon, player.entityDirect()))
                        .item(SpriteItemHelper.getPhoto(pokemon))
                        .index(i++)
                        .clickAction(clickData -> AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon)))
                        .build()
                );
            }
        }
    }
}