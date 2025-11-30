package com.vecoo.movelearner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/gui.yml")
@ConfigSerializable
public class GuiConfig extends AbstractYamlConfig {
    private boolean localizedNameMoves = true;
    private boolean fillerChoicePokemonUI = true;
    private boolean fillerChoiceMovesUI = true;
    private boolean fillerSureUI = true;
    private boolean informationUI = true;

    private String fillerItem = "minecraft:white_stained_glass_pane:0";
    private String emptyPokemonItem = "pixelmon:poke_ball";
    private String previousPageItem = "minecraft:arrow:0";
    private String nextPageItem = "minecraft:arrow:0";
    private String backItem = "minecraft:red_stained_glass_pane:0";
    private String acceptItem = "minecraft:lime_stained_glass_pane:0";
    private String comingItem = "minecraft:paper:0";
    private String cancelItem = "minecraft:red_stained_glass_pane:0";
    private String informationItem = "minecraft:paper:0";
    private String filterItem = "minecraft:hopper";
    private String searchItem = "minecraft:compass";

    private String selectPokemonTitle = "Select pokemon";
    private String selectMoveTitle = "Select move";
    private String acceptTitle = "Are you sure?";

    private String emptyPokemonName = "&cEmpty Pokemon";
    private String movesLore = "&7Moves: ";
    private String moveSymbol = "&7» ";
    private String localizedMoveName = " &7(%move%)";
    private String localizedMoveLore = " &7(%move%)";
    private String previousPageName = "Previous page";
    private String nextPageName = "Next page";
    private String backName = "&cBack";
    private String acceptName = "&aAccept";
    private String cancelName = "&cCancel";
    private String typeLore = "Type: ";
    private String powerLore = "Power: %amount%";
    private String accuracyLore = "Accuracy: %amount%";
    private String ppLore = "PP: %amount% (%maxAmount%)";
    private String priceItemLore = "Price: %amount% rare candy";
    private String priceCurrencyLore = "Price: %amount% pokedollars";
    private String priceFreeLore = "Price: free";
    private String informationName = "Information:";
    private String informationLore = "In this menu you can select a Pokemon that will be equipped with any move available to it of your choice.";
    private String filterName = "Filter";
    private String filterSymbol = "&7» ";
    private String searchName = "Search";
    private String searchLore = "Left click - search \\nRight click - reset";
    private String searchAcceptDialogue = "Accept";
    private String searchLoreDialogue = "Enter the first letters or the name of the move to search. Search does not support translatable names.";

    public boolean isLocalizedNameMoves() {
        return this.localizedNameMoves;
    }

    public boolean isFillerChoicePokemonUI() {
        return this.fillerChoicePokemonUI;
    }

    public boolean isFillerChoiceMovesUI() {
        return this.fillerChoiceMovesUI;
    }

    public boolean isFillerSureUI() {
        return this.fillerSureUI;
    }

    public boolean isInformationUI() {
        return this.informationUI;
    }

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

    public String getComingItem() {
        return this.comingItem;
    }

    public String getCancelItem() {
        return this.cancelItem;
    }

    public String getInformationItem() {
        return this.informationItem;
    }

    public String getFilterItem() {
        return this.filterItem;
    }

    public String getSearchItem() {
        return this.searchItem;
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

    public String getLocalizedMoveName() {
        return this.localizedMoveName;
    }

    public String getLocalizedMoveLore() {
        return this.localizedMoveLore;
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

    public String getTypeLore() {
        return this.typeLore;
    }

    public String getPowerLore() {
        return this.powerLore;
    }

    public String getAccuracyLore() {
        return this.accuracyLore;
    }

    public String getPpLore() {
        return this.ppLore;
    }

    public String getPriceItemLore() {
        return this.priceItemLore;
    }

    public String getPriceCurrencyLore() {
        return this.priceCurrencyLore;
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

    public String getFilterName() {
        return this.filterName;
    }

    public String getFilterSymbol() {
        return this.filterSymbol;
    }

    public String getSearchName() {
        return this.searchName;
    }

    public String getSearchLore() {
        return this.searchLore;
    }

    public String getSearchAcceptDialogue() {
        return this.searchAcceptDialogue;
    }

    public String getSearchLoreDialogue() {
        return this.searchLoreDialogue;
    }
}