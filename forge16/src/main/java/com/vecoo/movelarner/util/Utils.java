package com.vecoo.movelarner.util;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.moves.Moves;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.enums.TMType;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import com.pixelmonmod.pixelmon.items.TechnicalMoveItem;
import com.vecoo.movelarner.MoveLearner;
import de.waterdu.atlantis.util.item.ParsedItemStack;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.stream.Collectors;

public class Utils {
    public static ItemStack parsedItemStackCustomModel(String id) {
        String[] parts = id.split(":");

        ItemStack itemStack = ParsedItemStack.of(parts[0] + ":" + parts[1]).stack();

        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (parts.length == 3) {
            try {
                itemStack.getOrCreateTag().putInt("CustomModelData", Integer.parseInt(parts[2]));
            } catch (NumberFormatException e) {
                MoveLearner.getLogger().error("[MoveLearner] Invalid CustomModelData value in item \"{}\".", id);
            }
        }

        return itemStack;
    }

    public static ItemStack getTM(ImmutableAttack attack) {
        ItemStack itemStack = PixelmonItems.tm8_blank.getItem().getDefaultInstance();

        switch (attack.getAttackType()) {
            case BUG: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Bug Buzz"));
                break;
            }

            case DARK: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Dark Pulse"));
                break;
            }

            case DRAGON: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Dragon Claw"));
                break;
            }

            case ELECTRIC: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Electro Ball"));
                break;
            }

            case FAIRY: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Play Rough"));
                break;
            }

            case ICE: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Ice Punch"));
                break;
            }

            case GHOST: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Shadow Ball"));
                break;
            }

            case FLYING: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Fly"));
                break;
            }

            case FIGHTING: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Drain Punch"));
                break;
            }

            case GROUND: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Earthquake"));
                break;
            }

            case NORMAL: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Body Slam"));
                break;
            }

            case PSYCHIC: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Psyshock"));
                break;
            }

            case ROCK: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Stone Edge"));
                break;
            }

            case FIRE: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Fire Blast"));
                break;
            }

            case POISON: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Poison Jab"));
                break;
            }

            case STEEL: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Iron Head"));
                break;
            }

            case GRASS: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Grass Knot"));
                break;
            }

            case WATER: {
                itemStack = TechnicalMoveItem.of(TMType.TM9, ITechnicalMove.getMoveFor(TMType.TM9, "Waterfall"));
                break;
            }
        }
        return itemStack;
    }

    public static int movePrice(Pokemon pokemon, ImmutableAttack attack) {
        Moves moves = pokemon.getForm().getMoves();

        if (moves.getAllLevelUpMoves().contains(attack)) {
            return MoveLearner.getInstance().getConfig().getLevelMovePrice();
        }

        if (moves.getTMMoves().contains(attack)) {
            return MoveLearner.getInstance().getConfig().getTmMovePrice();
        }

        if (moves.getTRMoves().stream().map(ITechnicalMove::getAttack).collect(Collectors.toList()).contains(attack)) {
            return MoveLearner.getInstance().getConfig().getTrMovePrice();
        }

        if (moves.getHMMoves().contains(attack)) {
            return MoveLearner.getInstance().getConfig().getHmMovePrice();
        }

        if (moves.getTutorMoves().contains(attack)) {
            return MoveLearner.getInstance().getConfig().getTutorMovePrice();
        }

        if (moves.getTransferMoves().contains(attack)) {
            return MoveLearner.getInstance().getConfig().getTransferMovePrice();
        }

        if (moves.getEggMoves().contains(attack)) {
            return MoveLearner.getInstance().getConfig().getEggMovePrice();
        }

        return 0;
    }

    public static int countItemStack(ServerPlayerEntity player, ItemStack itemStack) {
        int count = 0;

        for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
            ItemStack stack = player.inventory.getItem(i);
            if (stack.getItem().equals(itemStack.getItem())) {
                if (itemStack.getTag() == null || (stack.getTag() != null && stack.getTag().equals(itemStack.getTag()))) {
                    count += stack.getCount();
                }
            }
        }

        return count;
    }
}
