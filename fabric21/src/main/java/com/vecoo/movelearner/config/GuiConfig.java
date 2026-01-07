package com.vecoo.movelearner.config;

import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.ConfigSerializable;
import lombok.Getter;

@Getter
@ConfigSerializable
public class GuiConfig {
    private boolean localizedNameMoves = true;
    private boolean fillerChoicePokemonUI = true;
    private boolean fillerChoiceMovesUI = true;
    private boolean fillerSureUI = true;
    private boolean informationUI = true;

    private String selectPokemonTitle = "Select pokemon";
    private String selectMoveTitle = "Select move";
    private String acceptTitle = "Are you sure?";

    private String fillerItem = "minecraft:white_stained_glass_pane:0";
    private String emptyPokemonItem = "cobblemon:poke_ball";
    private String previousPageItem = "minecraft:arrow:0";
    private String nextPageItem = "minecraft:arrow:0";
    private String backItem = "minecraft:red_stained_glass_pane:0";
    private String acceptItem = "minecraft:lime_stained_glass_pane:0";
    private String comingItem = "minecraft:paper:0";
    private String cancelItem = "minecraft:red_stained_glass_pane:0";
    private String informationItem = "minecraft:paper:0";
    private String filterItem = "minecraft:hopper";

    private String emptyPokemonName = "&cEmpty Pokemon";
    private String previousPageName = "Previous page";
    private String nextPageName = "Next page";
    private String backName = "&cBack";
    private String acceptName = "&aAccept";
    private String cancelName = "&cCancel";
    private String informationName = "Information:";
    private String informationLore = "In this menu you can select a Pokemon that will be equipped with any move available to it of your choice.";
    private String filterName = "Filter";
    private String filterSymbol = "&7» ";

    private String movesLore = "&7Moves: ";
    private String moveSymbol = "&7» ";
    private String localizedMoveName = " &7(%move%)";
    private String localizedMoveLore = " &7(%move%)";
    private String typeLore = "Type: ";
    private String powerLore = "Power: %amount%";
    private String accuracyLore = "Accuracy: %amount%";
    private String ppLore = "PP: %amount% (%maxAmount%)";
    private String priceLore = "Price: %amount% %currency%";
    private String priceFreeLore = "Price: free";

    private String filterAll = "All";
    private String filterLevel = "Level";
    private String filterTM = "TM";
    private String filterLegacy = "Legacy";
    private String filterTutor = "Tutor";
    private String filterSpecial = "Special";
    private String filterEgg = "Egg";

    private String itemCurrency = "rare candy";
    private String cobblemonCurrency = "cobbledollars";
    private String impactorCurrency = "money";
    private String notValidCurrency = "&cType currency - %currency% not supported.";
}