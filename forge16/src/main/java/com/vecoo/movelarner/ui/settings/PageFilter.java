package com.vecoo.movelarner.ui.settings;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.ui.pages.SelectMovePage;
import de.waterdu.atlantis.ui.api.AtlantisUI;
import net.minecraft.entity.player.ServerPlayerEntity;

public class PageFilter {
    public static final String ALL = "all";
    public static final String LEVEL = "level";
    public static final String TMTR = "tmtr";
    public static final String HM = "hm";
    public static final String TUTOR = "tutor";
    public static final String TRANSFER = "transfer";
    public static final String EGG = "egg";

    public static void changeFilterLeft(ServerPlayerEntity player, String filter, Pokemon pokemon) {
        switch (filter) {
            case "all": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, LEVEL));
                break;
            }

            case "level": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, TMTR));
                break;
            }

            case "tmtr": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, MoveLearner.getInstance().getConfig().isHmMove() ? HM : TUTOR));
                break;
            }

            case "hm": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, TUTOR));
                break;
            }

            case "tutor": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, TRANSFER));
                break;
            }

            case "transfer": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, MoveLearner.getInstance().getConfig().isEggMove() ? EGG : ALL));
                break;
            }

            case "egg": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, ALL));
                break;
            }
        }
    }


    public static void changeFilterRight(ServerPlayerEntity player, String filter, Pokemon pokemon) {
        switch (filter) {
            case "all": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, MoveLearner.getInstance().getConfig().isEggMove() ? EGG : TRANSFER));
                break;
            }

            case "level": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, ALL));
                break;
            }

            case "tmtr": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, LEVEL));
                break;
            }

            case "hm": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, TMTR));
                break;
            }

            case "tutor": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, MoveLearner.getInstance().getConfig().isHmMove() ? HM : TMTR));
                break;
            }

            case "transfer": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, TUTOR));
                break;
            }

            case "egg": {
                AtlantisUI.open(player, new SelectMovePage(pokemon, TRANSFER));
                break;
            }
        }
    }
}
