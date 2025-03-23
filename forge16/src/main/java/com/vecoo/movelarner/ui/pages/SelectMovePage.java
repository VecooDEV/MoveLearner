package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.ui.ButtonLore;
import com.vecoo.movelarner.ui.ButtonName;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.*;
import de.waterdu.atlantis.util.entity.PlayerReference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public class SelectMovePage implements Page {
    private final Pokemon pokemon;

    public SelectMovePage(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public PageOptions getPageOptions(PlayerReference player) {
        return PageOptions.builder()
                .title(MoveLearner.getInstance().getGui().getSelectMoveTitle())
                .rows(6)
                .build();
    }

    @Override
    public void addButtons(PlayerReference player, ButtonCollector buttons) {
        int i = 0;
        int page = 0;

        for (ImmutableAttack attack : pokemon.getForm().getMoves().getAllMoves()) {
            if (pokemon.getMoveset().hasAttack(attack)) {
                continue;
            }

            if (i % 45 == 0 && i != 0) {
                i = 0;
                page++;
            }

            ItemStack itemStackTM = Utils.getTM(attack);
            IFormattableTextComponent movePriceLore = ButtonLore.movePrice(pokemon, attack);

            buttons.collect(Button.builder()
                    .directName(ButtonName.translatedTM(attack, player.entityDirect()))
                    .item(itemStackTM)
                    .lore(movePriceLore)
                    .index(i++)
                    .page(page)
                    .clickAction(clickData -> AtlantisUI.open(player, new AcceptPage(pokemon, attack, itemStackTM, movePriceLore)))
                    .build()
            );
        }

        ItemStack fillerItem = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getFillerItem());

        for (int slot = 45; slot < 54; slot++) {
            switch (slot) {
                case 45: {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getPreviousPageName())
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getPreviousPageItem()))
                            .index(slot)
                            .prevPage()
                            .build());
                    break;
                }

                case 49: {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getBackName())
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getBackItem()))
                            .index(slot)
                            .clickAction(clickData -> AtlantisUI.open(player, new SelectPokemonPage()))
                            .build());
                    break;
                }

                case 53: {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getNextPageName())
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getNextPageItem()))
                            .index(slot)
                            .nextPage()
                            .build());
                    break;
                }

                default: {
                    buttons.collect(new Decoration(fillerItem, slot));
                    break;
                }
            }
        }
    }
}
