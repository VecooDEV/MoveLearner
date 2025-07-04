package com.vecoo.movelarner.api.events;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class LearnEvent extends Event {
    private final ServerPlayerEntity player;
    private final Pokemon pokemon;
    private final ImmutableAttack attack;

    public LearnEvent(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack) {
        this.player = player;
        this.pokemon = pokemon;
        this.attack = attack;
    }

    public ServerPlayerEntity getPlayer() {
        return this.player;
    }

    public Pokemon getPokemon() {
        return this.pokemon;
    }

    public ImmutableAttack getAttack() {
        return this.attack;
    }

    @Cancelable
    public static class BuyItem extends LearnEvent {
        private final ItemStack itemStack;
        private final int amount;

        public BuyItem(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack, ItemStack itemStack, int amount) {
            super(player, pokemon, attack);
            this.itemStack = itemStack;
            this.amount = amount;
        }

        public ItemStack getItemStack() {
            return this.itemStack;
        }

        public int getAmount() {
            return this.amount;
        }
    }

    @Cancelable
    public static class BuyCurrency extends LearnEvent {
        private final int price;

        public BuyCurrency(ServerPlayerEntity player, Pokemon pokemon, ImmutableAttack attack, int price) {
            super(player, pokemon, attack);
            this.price = price;
        }

        public int getPrice() {
            return this.price;
        }
    }
}
