package com.vecoo.movelearner.api.events;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import org.jetbrains.annotations.NotNull;

public class LearnEvent extends Event implements ICancellableEvent {
    private final ServerPlayer player;
    private final Pokemon pokemon;
    private final Move move;

    public LearnEvent(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Move move) {
        this.player = player;
        this.pokemon = pokemon;
        this.move = move;
    }

    @NotNull
    public ServerPlayer getPlayer() {
        return this.player;
    }

    @NotNull
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public Move getMove() {
        return this.move;
    }

    public static class BuyItem extends LearnEvent implements ICancellableEvent {
        private final ItemStack itemStack;
        private final int amount;

        public BuyItem(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Move move,
                       @NotNull ItemStack itemStack, int amount) {
            super(player, pokemon, move);
            this.itemStack = itemStack;
            this.amount = amount;
        }

        @NotNull
        public ItemStack getItemStack() {
            return this.itemStack;
        }

        public int getAmount() {
            return this.amount;
        }
    }

    public static class BuyCurrency extends LearnEvent implements ICancellableEvent {
        private final int price;

        public BuyCurrency(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Move move, int price) {
            super(player, pokemon, move);
            this.price = price;
        }

        public int getPrice() {
            return this.price;
        }
    }
}
