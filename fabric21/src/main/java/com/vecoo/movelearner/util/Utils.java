package com.vecoo.movelearner.util;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.pokemon.moves.Learnset;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.movelearner.MoveLearner;
import lombok.val;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Utils {
    @NotNull
    public static ItemStack getItemTM(@NotNull MoveTemplate move) {
        var itemStack = CobblemonItems.NORMAL_GEM.getDefaultInstance();

        switch (move.getElementalType().getName()) {
            case "Bug" -> itemStack = CobblemonItems.BUG_GEM.getDefaultInstance();
            case "Dark" -> itemStack = CobblemonItems.DARK_GEM.getDefaultInstance();
            case "Dragon" -> itemStack = CobblemonItems.DRAGON_GEM.getDefaultInstance();
            case "Electric" -> itemStack = CobblemonItems.ELECTRIC_GEM.getDefaultInstance();
            case "Fairy" -> itemStack = CobblemonItems.FAIRY_GEM.getDefaultInstance();
            case "Ice" -> itemStack = CobblemonItems.ICE_GEM.getDefaultInstance();
            case "Ghost" -> itemStack = CobblemonItems.GHOST_GEM.getDefaultInstance();
            case "Flying" -> itemStack = CobblemonItems.FLYING_GEM.getDefaultInstance();
            case "Fighting" -> itemStack = CobblemonItems.FIGHTING_GEM.getDefaultInstance();
            case "Ground" -> itemStack = CobblemonItems.GROUND_GEM.getDefaultInstance();
            case "Normal" -> itemStack = CobblemonItems.NORMAL_GEM.getDefaultInstance();
            case "Psychic" -> itemStack = CobblemonItems.PSYCHIC_GEM.getDefaultInstance();
            case "Rock" -> itemStack = CobblemonItems.ROCK_GEM.getDefaultInstance();
            case "Fire" -> itemStack = CobblemonItems.FIRE_GEM.getDefaultInstance();
            case "Poison" -> itemStack = CobblemonItems.POISON_GEM.getDefaultInstance();
            case "Steel" -> itemStack = CobblemonItems.STEEL_GEM.getDefaultInstance();
            case "Grass" -> itemStack = CobblemonItems.GRASS_GEM.getDefaultInstance();
            case "Water" -> itemStack = CobblemonItems.WATER_GEM.getDefaultInstance();
        }

        return itemStack;
    }

    public static int getMovePrice(@NotNull Pokemon pokemon, @NotNull MoveTemplate move) {
        val moves = pokemon.getForm().getMoves();

        if (moves.getLevelUpMoves().values().stream().anyMatch(list -> list.contains(move))) {
            return MoveLearner.getInstance().getServerConfig().getLevelMovePrice();
        }

        if (moves.getTmMoves().contains(move)) {
            return MoveLearner.getInstance().getServerConfig().getTmMovePrice();
        }

        if (moves.getLegacyMoves().contains(move)) {
            return MoveLearner.getInstance().getServerConfig().getLegacyMovePrice();
        }

        if (moves.getTutorMoves().contains(move)) {
            return MoveLearner.getInstance().getServerConfig().getTutorMovePrice();
        }

        if (moves.getSpecialMoves().contains(move)) {
            return MoveLearner.getInstance().getServerConfig().getSpecialMovePrice();
        }

        if (moves.getEggMoves().contains(move)) {
            return MoveLearner.getInstance().getServerConfig().getEggMovePrice();
        }

        return 0;
    }

    @NotNull
    public static List<MoveTemplate> getAllMoves(@NotNull Learnset moves) {
        List<MoveTemplate> allMoveList = new ArrayList<>();

        allMoveList.addAll(moves.getAllLegalMoves());
        allMoveList.addAll(moves.getLegacyMoves());
        allMoveList.addAll(moves.getSpecialMoves());

        return allMoveList;
    }

    public static boolean isLearnedMove(@NotNull Pokemon pokemon, @NotNull MoveTemplate move) {
        Set<MoveTemplate> moves = new HashSet<>(pokemon.getAllAccessibleMoves());

        for (Move learnedMove : pokemon.getMoveSet().getMoves()) {
            moves.add(learnedMove.getTemplate());
        }

        return moves.contains(move);
    }

    @NotNull
    public static String formatStatMove(double stat) {
        if (stat <= 0) {
            return "-";
        }

        return String.valueOf(stat);
    }

    public static void removeItemStackTag(@Nullable Player player, @NotNull ItemStack removeItemStack, @NotNull DataComponentType<?> dataComponent, int amount) {
        if (player == null) {
            MoveLearner.getLogger().error("Item {} was not claimed because the player is null, this is an error.", removeItemStack.getDisplayName());
            return;
        }

        int totalRemoved = 0;

        InventoryMenu inventoryMenu = player.inventoryMenu;

        for (ItemStack itemStack : inventoryMenu.getItems()) {
            if (totalRemoved >= amount) {
                break;
            }

            if (itemStack.isEmpty() || itemStack.getItem() != removeItemStack.getItem()) {
                continue;
            }

            int toRemove = Math.min(itemStack.getCount(), amount - totalRemoved);

            if (itemStack.getComponents().isEmpty() && removeItemStack.getComponents().isEmpty()) {
                itemStack.shrink(toRemove);
                totalRemoved += toRemove;
                continue;
            }

            if (itemStack.getComponents().isEmpty() || removeItemStack.getComponents().isEmpty()) {
                continue;
            }

            if (!Objects.equals(itemStack.getComponents().get(dataComponent), removeItemStack.getComponents().get(dataComponent))) {
                continue;
            }

            itemStack.shrink(toRemove);
            totalRemoved += toRemove;
        }

        inventoryMenu.broadcastChanges();
    }

}
