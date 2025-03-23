package com.vecoo.movelarner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/gui.yml")
@ConfigSerializable
public class GuiConfig extends AbstractYamlConfig {
    private String fillerItem = "minecraft:white_stained_glass_pane:0";
    private String emptyPokemonItem = "pixelmon:poke_ball";
    private String previousPageItem = "minecraft:arrow:0";
    private String nextPageItem = "minecraft:arrow:0";
    private String backItem = "minecraft:red_stained_glass_pane:0";
    private String acceptItem = "minecraft:lime_stained_glass_pane:0";
    private String priceItem = "minecraft:paper:0";
    private String cancelItem = "minecraft:red_stained_glass_pane:0";
    private String informationItem = "minecraft:paper:0";

    private String selectPokemonTitle = "Select pokemon";
    private String selectMoveTitle = "Select move";
    private String acceptTitle = "Are you sure?";
    private String emptyPokemonName = "&cEmpty Pokemon";
    private String movesLore = "&9Moves: ";
    private String moveSymbol = "&7» ";
    private String previousPageName = "&fPrevious page";
    private String nextPageName = "&fNext page";
    private String backName = "&cBack";
    private String acceptName = "&aAccept";
    private String cancelName = "&cCancel";
    private String priceName = "&fPrice move";
    private String priceLore = "&fPrice: %amount% rare candy";
    private String priceFreeLore = "&fPrice: free";
    private String informationName = "&fInformation:";
    private String informationLore = "&fIn this menu you can select a Pokemon that will be equipped with any attack available to it of your choice.";

    public String getFillerItem() {
        return this.fillerItem;
    }

    public String getEmptyPokemonItem() {
        return this.emptyPokemonItem;
    }

    public String getPreviousPageItem() {
        return this.previousPageItem;
    }

    public String getNextPageItem() {
        return this.nextPageItem;
    }

    public String getBackItem() {
        return this.backItem;
    }

    public String getAcceptItem() {
        return this.acceptItem;
    }

    public String getPriceItem() {
        return this.priceItem;
    }

    public String getCancelItem() {
        return this.cancelItem;
    }

    public String getInformationItem() {
        return this.informationItem;
    }

    public String getSelectPokemonTitle() {
        return this.selectPokemonTitle;
    }

    public String getSelectMoveTitle() {
        return this.selectMoveTitle;
    }

    public String getAcceptTitle() {
        return this.acceptTitle;
    }

    public String getEmptyPokemonName() {
        return this.emptyPokemonName;
    }

    public String getMovesLore() {
        return this.movesLore;
    }

    public String getMoveSymbol() {
        return this.moveSymbol;
    }

    public String getPreviousPageName() {
        return this.previousPageName;
    }

    public String getNextPageName() {
        return this.nextPageName;
    }

    public String getBackName() {
        return this.backName;
    }

    public String getAcceptName() {
        return this.acceptName;
    }


    public String getCancelName() {
        return this.cancelName;
    }

    public String getPriceName() {
        return this.priceName;
    }

    public String getPriceLore() {
        return this.priceLore;
    }

    public String getPriceFreeLore() {
        return this.priceFreeLore;
    }

    public String getInformationName() {
        return this.informationName;
    }

    public String getInformationLore() {
        return this.informationLore;
    }
}