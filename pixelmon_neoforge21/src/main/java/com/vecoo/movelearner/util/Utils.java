package com.vecoo.movelearner.util;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.moves.Moves;
import com.pixelmonmod.pixelmon.api.pokemon.type.Type;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.enums.TMType;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import com.pixelmonmod.pixelmon.init.registry.ItemRegistration;
import com.pixelmonmod.pixelmon.items.TechnicalMoveItem;
import com.vecoo.movelearner.MoveLearner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Utils {
    @NotNull
    public static ItemStack getItemTM(@NotNull ImmutableAttack move) {
        ItemStack itemStack = ItemRegistration.TM8_BLANK.get().getDefaultInstance();
        ResourceKey<Type> type = move.getAttackType().getKey();

        if (type == null) {
            return itemStack;
        }

        switch (type.location().getPath()) {
            case "bug" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Bug Buzz"));
            case "dark" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Dark Pulse"));
            case "dragon" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Dragon Claw"));
            case "electric" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Electro Ball"));
            case "fairy" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Play Rough"));
            case "ice" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Ice Punch"));
            case "ghost" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Shadow Ball"));
            case "flying" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Fly"));
            case "fighting" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Drain Punch"));
            case "ground" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Earthquake"));
            case "normal" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Body Slam"));
            case "psychic" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Psyshock"));
            case "rock" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Stone Edge"));
            case "fire" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Fire Blast"));
            case "poison" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Poison Jab"));
            case "steel" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Iron Head"));
            case "grass" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Grass Knot"));
            case "water" -> itemStack = TechnicalMoveItem.of(TMType.TM9, getMove("Waterfall"));
        }

        return itemStack;
    }

    public static int getMovePrice(@NotNull Pokemon pokemon, @NotNull ImmutableAttack move) {
        Moves moves = pokemon.getForm().getMoves();

        if (moves.getAllLevelUpMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getLevelMovePrice();
        }

        if (moves.getTMMoves().contains(move) || moves.getTRMoves().stream().map(ITechnicalMove::getAttack).toList().contains(move)) {
            return MoveLearner.getInstance().getConfig().getTmTrMovePrice();
        }

        if (moves.getHMMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getHmMovePrice();
        }

        if (moves.getTutorMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getTutorMovePrice();
        }

        if (moves.getTransferMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getTransferMovePrice();
        }

        if (moves.getEggMoves().contains(move)) {
            return MoveLearner.getInstance().getConfig().getEggMovePrice();
        }

        return 0;
    }

    @NotNull
    public static String formatStatMove(int stat) {
        if (stat <= 0) {
            return "-";
        }

        return String.valueOf(stat);
    }

    @NotNull
    private static ITechnicalMove getMove(@NotNull String moveName) {
        return ITechnicalMove.getMoveFor(TMType.TM9, moveName);
    }
}
