package com.vecoo.movelearner.api.events;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class LearnEvent extends Event implements ICancellableEvent {
    @NotNull
    private final ServerPlayer player;
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private final ImmutableAttack move;

    @Getter
    public static class BuyItem extends LearnEvent implements ICancellableEvent {
        @NotNull
        private final ItemStack itemStack;
        private final int amount;

        public BuyItem(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move,
                       @NotNull ItemStack itemStack, int amount) {
            super(player, pokemon, move);
            this.itemStack = itemStack;
            this.amount = amount;
        }
    }

    @Getter
    public static class BuyCurrency extends LearnEvent implements ICancellableEvent {
        private final int price;

        public BuyCurrency(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move, int price) {
            super(player, pokemon, move);
            this.price = price;
        }
    }
}
