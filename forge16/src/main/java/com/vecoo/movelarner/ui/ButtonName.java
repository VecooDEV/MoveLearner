package com.vecoo.movelarner.ui;

import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;

public class ButtonName {
    public static IFormattableTextComponent translatedTM(ImmutableAttack attack, ServerPlayerEntity player) {
        IFormattableTextComponent name = attack.getTranslatedName();

        if (MoveLearner.getInstance().getConfig().isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
            name.append(" (").append(attack.getLocalizedName()).append(")");
        }

        return name;
    }
}