package com.vecoo.movelarner.ui;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class ButtonLore {
    public static List<ITextComponent> pokemon(Pokemon pokemon, ServerPlayerEntity player) {
        List<ITextComponent> lore = new ArrayList<>();

        lore.add(TextUtils.asComponent(MoveLearner.getInstance().getGui().getMovesLore()));

        boolean showLocalizedNames = MoveLearner.getInstance().getConfig().isLocalizedNameMoves() && !player.getLanguage().equals("en_us");

        for (Attack move : pokemon.getMoveset()) {
            IFormattableTextComponent formattedText = TextUtils.asComponent(MoveLearner.getInstance().getGui().getMoveSymbol()).append(move.getMove().getTranslatedName().withStyle(Style.EMPTY.withColor(TextFormatting.GRAY)));

            if (showLocalizedNames) {
                formattedText.append(" (").append(move.getMove().getLocalizedName()).append(")").withStyle(Style.EMPTY.withColor(TextFormatting.GRAY));
            }

            lore.add(formattedText);
        }

        return lore;
    }

    public static IFormattableTextComponent movePrice(Pokemon pokemon, ImmutableAttack attack) {
        int amount = Utils.movePrice(pokemon, attack);

        if (amount <= 0) {
            return TextUtils.asComponent(MoveLearner.getInstance().getGui().getPriceFreeLore());
        }

        return TextUtils.asComponent(MoveLearner.getInstance().getGui().getPriceLore()
                .replace("%amount%", String.valueOf(Utils.movePrice(pokemon, attack))));
    }
}