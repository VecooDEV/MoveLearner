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
    private String priceLore = "Price: %amount% %currency%";
    private String priceFreeLore = "Price: free";
    private String informationName = "Information:";
    private String informationLore = "In this menu you can select a Pokemon that will be equipped with any move available to it of your choice.";
    private String filterName = "Filter";
    private String filterSymbol = "&7» ";
    private String searchName = "Search";
    private String searchLore = "Left click - search \\nRight click - reset";
    private String searchAcceptDialogue = "Accept";
    private String searchLoreDialogue = "Enter the first letters or the name of the move to search. Search does not support translatable names.";

    private String itemCurrency = "rare candy";
    private String pixelmonCurrency = "pokedollars";
    private String impactorCurrency = "money";
    private String customCurrency = "crystals";
}