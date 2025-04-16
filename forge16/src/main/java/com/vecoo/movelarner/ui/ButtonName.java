package com.vecoo.movelarner.ui;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public class ButtonName {
    public static IFormattableTextComponent translatedTM(ImmutableAttack attack, ServerPlayerEntity player) {
        IFormattableTextComponent name = attack.getTranslatedName().withStyle(Style.EMPTY.withItalic(false));

        if (MoveLearner.getInstance().getGui().isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
            name.append(TextUtils.asComponent(MoveLearner.getInstance().getGui().getLocalizedMoveName()
                    .replace("%move%", attack.getAttackName())));
        }

        return name;
    }

    public static ITextComponent pokemonName(Pokemon pokemon) {
        IFormattableTextComponent pokemonName = pokemon.getTranslatedName().withStyle(Style.EMPTY.withItalic(false));

        if (!pokemon.isDefaultForm()) {
            pokemonName.append("-").append(pokemon.getForm().getTranslatedName());
        }

        return pokemonName;
    }
}