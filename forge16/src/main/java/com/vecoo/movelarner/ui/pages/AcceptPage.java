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

        ItemStack fillerItem = Utils.parseItemCustomModel(guiConfig.getFillerItem());

        if (guiConfig.isFillerSureUI()) {
            for (int i = 0; i < 27; i++) {
                buttons.collect(new Decoration(fillerItem, i));
            }
        }

        buttons.collect(Button.builder()
                .name(guiConfig.getCancelName())
                .item(Utils.parseItemCustomModel(guiConfig.getCancelItem()))
                .index(10)
                .clickAction(clickData -> AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon, filter, "")))
                .build());

        buttons.collect(Button.builder()
                .directName(ButtonName.translatedTM(attack, player.entityDirect()))
                .item(itemStackTM)
                .index(12)
                .build());

        buttons.collect(Button.builder()
                .item(Utils.parseItemCustomModel(guiConfig.getComingItem()))
                .index(13)
                .build());

        buttons.collect(Button.builder()
                .directName(ButtonName.pokemonName(pokemon))
                .directLore(ButtonLore.pokemonMoves(pokemon, player.entityDirect()))
                .item(SpriteItemHelper.getPhoto(pokemon))
                .index(14)
                .build());

        buttons.collect(Button.builder()
                .name(guiConfig.getAcceptName())
                .item(Utils.parseItemCustomModel(guiConfig.getAcceptItem()))
                .index(16)
                .clickAction(clickData -> {
                    if (MoveLearner.getInstance().getConfig().isUseCurrency()) {
                        MoveLearnerFactoryUI.learnMoveCurrency(clickData.entity(), pokemon, attack, filter);
                    } else {
                        MoveLearnerFactoryUI.learnMoveItem(clickData.entity(), pokemon, attack, filter);
                    }
                })
                .build());
    }
}
