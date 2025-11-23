package com.vecoo.movelearner.util;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.movelearner.MoveLearner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    @NotNull
    public static ItemStack getItemTM(@NotNull MoveTemplate move) {
        ItemStack itemStack = Items.GLOWSTONE.getDefaultInstance();

        switch (move.getElementalType().getName()) {
            case "bug" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "dark" -> itemStack = Items.ACACIA_BOAT.getDefaultInstance();
            case "dragon" -> itemStack = Items.ACACIA_FENCE.getDefaultInstance();
            case "electric" -> itemStack = Items.BAMBOO_DOOR.getDefaultInstance();
            case "fairy" -> Items.GLOWSTONE.getDefaultInstance();
            case "ice" -> Items.GLOWSTONE.getDefaultInstance();
            case "ghost" -> Items.GLOWSTONE.getDefaultInstance();
            case "flying" -> Items.GLOWSTONE.getDefaultInstance();
            case "fighting" -> Items.GLOWSTONE.getDefaultInstance();
            case "ground" -> Items.GLOWSTONE.getDefaultInstance();
            case "normal" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "psychic" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "rock" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "fire" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "poison" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "steel" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "grass" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
            case "water" -> itemStack = Items.GLOWSTONE.getDefaultInstance();
        }

        return itemStack;
    }

    public static int getMovePrice(@NotNull Pokemon pokemon, @NotNull MoveTemplate move) {
        Learnset moves = pokemon.getForm().getMoves();

        if (moves.getLevelUpMoves().values().stream().anyMatch(list -> list.contains(move))) {
            return MoveLearner.getInstance().getConfig().getLevelMovePrice();
        }

        if (moves.getTmMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getTmMovePrice();
        }

        if (moves.getLegacyMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getLegacyMovePrice();
        }

        if (moves.getTutorMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getTutorMovePrice();
        }

        if (moves.getSpecialMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getSpecialMovePrice();
        }

        if (moves.getEggMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getEggMovePrice();
        }

        return 0;
    }

    public static List<MoveTemplate> getAllMoves(@NotNull Learnset moves) {
        List<MoveTemplate> allMoveList = new ArrayList<>();

        allMoveList.addAll(moves.getAllLegalMoves());
        allMoveList.addAll(moves.getLegacyMoves());
        allMoveList.addAll(moves.getSpecialMoves());

        return allMoveList;
    }

    @NotNull
    public static String getStatMoveFormat(double stat) {
        if (stat <= 0) {
            return "-";
        }

        return String.valueOf(stat);
    }
}
