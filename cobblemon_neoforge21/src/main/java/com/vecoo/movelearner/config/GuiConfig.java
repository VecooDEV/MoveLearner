package com.vecoo.movelearner.config;

import com.vecoo.extralib.gson.UtilGson;
import com.vecoo.movelearner.MoveLearner;

public class GuiConfig {
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

    private String selectPokemonTitle = "Select pokemon";
    private String selectMoveTitle = "Select move";
    private String acceptTitle = "Are you sure?";

    private String emptyPokemonName = "&cEmpty Pokemon";
    private String movesLore = "&7Moves: ";
    private String moveSymbol = "&7» ";
    private String localizedMoveName = " &7(%move%)";
    private String localizedMoveLore = " &7(%move%)";
    private String previousPageName = "&fPrevious page";
    private String nextPageName = "&fNext page";
    private String backName = "&cBack";
    private String acceptName = "&aAccept";
    private String cancelName = "&cCancel";
    private String typeLore = "&fType: ";
    private String powerLore = "&fPower: %amount%";
    private String accuracyLore = "&fAccuracy: %amount%";
    private String ppLore = "&fPP: %amount% (%maxAmount%)";
    private String priceItemLore = "&fPrice: %amount% rare candy";
    private String priceFreeLore = "&fPrice: free";
    private String informationName = "&fInformation:";
    private String informationLore = "&fIn this menu you can select a Pokemon that will be equipped with any move available to it of your choice.";
    private String filterName = "&fFilter";
    private String filterSymbol = "&7» ";

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

    private void write() {
        UtilGson.writeFileAsync("/config/MoveLearner/", "gui.json", UtilGson.newGson().toJson(this)).join();
    }

    public void init() {
        boolean completed = UtilGson.readFileAsync("/config/MoveLearner/", "gui.json", el -> {
            GuiConfig config = UtilGson.newGson().fromJson(el, GuiConfig.class);

            this.localizedNameMoves = config.isLocalizedNameMoves();
            this.fillerChoicePokemonUI = config.isFillerChoicePokemonUI();
            this.fillerChoiceMovesUI = config.isFillerChoiceMovesUI();
            this.fillerSureUI = config.isFillerSureUI();
            this.informationUI = config.isInformationUI();
            this.fillerItem = config.getFillerItem();
            this.emptyPokemonItem = config.getEmptyPokemonItem();
            this.previousPageItem = config.getPreviousPageItem();
            this.nextPageItem = config.getNextPageItem();
            this.backItem = config.getBackItem();
            this.acceptItem = config.getAcceptItem();
            this.comingItem = config.getComingItem();
            this.cancelItem = config.getCancelItem();
            this.informationItem = config.getInformationItem();
            this.filterItem = config.getFilterItem();
            this.selectPokemonTitle = config.getSelectPokemonTitle();
            this.selectMoveTitle = config.getSelectMoveTitle();
            this.acceptTitle = config.getAcceptTitle();
            this.emptyPokemonName = config.getEmptyPokemonName();
            this.movesLore = config.getMovesLore();
            this.moveSymbol = config.getMoveSymbol();
            this.localizedMoveName = config.getLocalizedMoveName();
            this.localizedMoveLore = config.getLocalizedMoveLore();
            this.previousPageName = config.getPreviousPageName();
            this.nextPageName = config.getNextPageName();
            this.backName = config.getBackName();
            this.acceptName = config.getAcceptName();
            this.cancelName = config.getCancelName();
            this.typeLore = config.getTypeLore();
            this.powerLore = config.getPowerLore();
            this.accuracyLore = config.getAccuracyLore();
            this.ppLore = config.getPpLore();
            this.priceItemLore = config.getPriceItemLore();
            this.priceFreeLore = config.getPriceFreeLore();
            this.informationName = config.getInformationName();
            this.informationLore = config.getInformationLore();
            this.filterName = config.getFilterName();
            this.filterSymbol = config.getFilterSymbol();
        }).join();

        if (!completed) {
            MoveLearner.getLogger().error("Error init config, generating new config.");
            write();
        }
    }
}