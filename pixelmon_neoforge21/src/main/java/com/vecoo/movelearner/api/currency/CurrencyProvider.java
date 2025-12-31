package com.vecoo.movelearner.api.currency;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public interface CurrencyProvider {
    @NotNull
    Component lore(int price);

    boolean buy(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move, int price);

    void successfulBuyMessage(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull ImmutableAttack move, int price);
}
