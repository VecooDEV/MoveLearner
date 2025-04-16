package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.api.factory.MoveLearnerFactoryUI;
import com.vecoo.movelarner.config.GuiConfig;
import com.vecoo.movelarner.ui.ButtonLore;
import com.vecoo.movelarner.ui.ButtonName;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.*;
import de.waterdu.atlantis.util.entity.PlayerReference;
import net.minecraft.item.ItemStack;

public class AcceptPage implements Page {
    private final Pokemon pokemon;
    private final ImmutableAttack attack;
    private final ItemStack itemStackTM;
    private final String filter;

    public AcceptPage(Pokemon pokemon, ImmutableAttack attack, ItemStack itemStackTM, String filter) {
        this.pokemon = pokemon;
        this.attack = attack;
        this.itemStackTM = itemStackTM;
        this.filter = filter;
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
        GuiConfig guiConfig = MoveLearner.getInstance().getGui();

        ItemStack fillerItem = Utils.parsedItemStackCustomModel(guiConfig.getFillerItem());

        for (int i = 0; i < 27; i++) {
            switch (i) {
                case 10: {
                    buttons.collect(Button.builder()
                            .name(guiConfig.getCancelName())
                            .item(Utils.parsedItemStackCustomModel(guiConfig.getCancelItem()))
                            .index(i)
                            .clickAction(clickData -> AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon, filter, "")))
                            .build());
                    break;
                }

                case 12: {
                    buttons.collect(Button.builder()
                            .directName(ButtonName.translatedTM(attack, player.entityDirect()))
                            .item(itemStackTM)
                            .index(i)
                            .build());
                    break;
                }

                case 13: {
                    buttons.collect(Button.builder()
                            .item(Utils.parsedItemStackCustomModel(guiConfig.getComingItem()))
                            .index(i)
                            .build());
                    break;
                }

                case 14: {
                    buttons.collect(Button.builder()
                            .directName(ButtonName.pokemonName(pokemon))
                            .directLore(ButtonLore.pokemonMoves(pokemon, player.entityDirect()))
                            .item(SpriteItemHelper.getPhoto(pokemon))
                            .index(i)
                            .build());
                    break;
                }

                case 16: {
                    buttons.collect(Button.builder()
                            .name(guiConfig.getAcceptName())
                            .item(Utils.parsedItemStackCustomModel(guiConfig.getAcceptItem()))
                            .index(i)
                            .clickAction(clickData -> MoveLearnerFactoryUI.learnMove(clickData.entity(), pokemon, attack, filter))
                            .build());
                    break;
                }

                default: {
                    if (guiConfig.isFillerSureUI()) {
                        buttons.collect(new Decoration(fillerItem, i));
                    }
                    break;
                }
            }
        }
    }
}
