package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelarner.ui.ButtonLore;
import com.vecoo.movelarner.ui.ButtonName;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.*;
import de.waterdu.atlantis.util.entity.PlayerReference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;

public class AcceptPage implements Page {
    private final Pokemon pokemon;
    private final ImmutableAttack attack;
    private final ItemStack itemStackTM;
    private final IFormattableTextComponent movePriceLore;

    public AcceptPage(Pokemon pokemon, ImmutableAttack attack, ItemStack itemStackTM, IFormattableTextComponent movePriceLore) {
        this.pokemon = pokemon;
        this.attack = attack;
        this.itemStackTM = itemStackTM;
        this.movePriceLore = movePriceLore;
    }

    @Override
    public PageOptions getPageOptions(PlayerReference player) {
        return PageOptions.builder()
                .title(MoveLearner.getInstance().getGui().getAcceptTitle())
                .rows(3)
                .build();
    }

    @Override
    public void addButtons(PlayerReference player, ButtonCollector buttons) {
        ItemStack fillerItem = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getFillerItem());

        for (int i = 0; i < 27; i++) {
            switch (i) {
                case 10: {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getCancelName())
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getCancelItem()))
                            .index(i)
                            .clickAction(clickData -> AtlantisUI.open(player, new SelectMovePage(pokemon)))
                            .build());
                    break;
                }

                case 12: {
                    buttons.collect(Button.builder()
                            .directName(pokemon.getTranslatedName())
                            .directLore(ButtonLore.pokemon(pokemon, player.entityDirect()))
                            .item(SpriteItemHelper.getPhoto(pokemon))
                            .index(i)
                            .build());
                    break;
                }

                case 13: {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getPriceName())
                            .lore(movePriceLore)
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getPriceItem()))
                            .index(i)
                            .build());
                    break;
                }

                case 14: {
                    buttons.collect(Button.builder()
                            .directName(ButtonName.translatedTM(attack, player.entityDirect()))
                            .item(itemStackTM)
                            .index(i)
                            .build());
                    break;
                }

                case 16: {
                    buttons.collect(Button.builder()
                            .name(MoveLearner.getInstance().getGui().getAcceptName())
                            .item(Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getAcceptItem()))
                            .index(i)
                            .clickAction(clickData -> MoveLearnerFactoryUI.learnMove(clickData.entity(), pokemon, attack))
                            .build());
                    break;
                }

                default: {
                    buttons.collect(new Decoration(fillerItem, i));
                    break;
                }
            }
        }
    }
}
