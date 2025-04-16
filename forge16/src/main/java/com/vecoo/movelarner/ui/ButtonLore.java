package com.vecoo.movelarner.ui;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.config.GuiConfig;
import com.vecoo.movelarner.config.ServerConfig;
import com.vecoo.movelarner.ui.settings.PageFilter;
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
    public static List<ITextComponent> pokemonMoves(Pokemon pokemon, ServerPlayerEntity player) {
        List<ITextComponent> lore = new ArrayList<>();
        GuiConfig guiConfig = MoveLearner.getInstance().getGui();

        lore.add(TextUtils.asComponent(guiConfig.getMovesLore()));

        boolean showLocalizedNames = guiConfig.isLocalizedNameMoves() && !player.getLanguage().equals("en_us");

        for (Attack move : pokemon.getMoveset()) {
            IFormattableTextComponent formattedText = TextUtils.asComponent(guiConfig.getMoveSymbol()).withStyle(Style.EMPTY.withItalic(false)).append(move.getMove().getTranslatedName()).withStyle(TextFormatting.WHITE);

            if (showLocalizedNames) {
                formattedText.append(TextUtils.asComponent(guiConfig.getLocalizedMoveLore()
                        .replace("%move%", move.getMove().getLocalizedName())));
            }

            lore.add(formattedText);
        }

        return lore;
    }

    public static ITextComponent movePrice(Pokemon pokemon, ImmutableAttack attack) { //TODO: Power/PP attack.
        int amount = Utils.movePrice(pokemon, attack);

        if (amount <= 0) {
            return TextUtils.asComponent(MoveLearner.getInstance().getGui().getPriceFreeLore());
        }

        return TextUtils.asComponent(MoveLearner.getInstance().getGui().getPriceLore()
                .replace("%amount%", String.valueOf(Utils.movePrice(pokemon, attack))));
    }

    public static List<ITextComponent> filter(String filter) {
        List<ITextComponent> lore = new ArrayList<>();

        ServerConfig config = MoveLearner.getInstance().getConfig();

        lore.add(createFilterLine("All", filter.equals(PageFilter.ALL)));

        lore.add(createFilterLine("Level", filter.equals(PageFilter.LEVEL)));

        lore.add(createFilterLine("TM/TR", filter.equals(PageFilter.TMTR)));

        if (config.isHmMove()) {
            lore.add(createFilterLine("HM", filter.equals(PageFilter.HM)));
        }

        lore.add(createFilterLine("Tutor", filter.equals(PageFilter.TUTOR)));

        lore.add(createFilterLine("Transfer", filter.equals(PageFilter.TRANSFER)));

        if (config.isEggMove()) {
            lore.add(createFilterLine("Egg", filter.equals(PageFilter.EGG)));
        }

        return lore;
    }

    private static ITextComponent createFilterLine(String text, boolean isActive) {
        return TextUtils.asComponent(MoveLearner.getInstance().getGui().getFilterSymbol() + (isActive ? "&f" : "&7") + text);
    }
}