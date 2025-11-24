package com.vecoo.movelearner.ui;

import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.config.GuiConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ButtonNames {
    @NotNull
    public static Component getMoveName(@NotNull ImmutableAttack move, @NotNull ServerPlayer player) {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();
        MutableComponent moveName = move.getTranslatedName();

        if (guiConfig.isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
            moveName.append(UtilChat.formatMessage(guiConfig.getLocalizedMoveName()
                    .replace("%move%", move.getAttackName())));
        }

        return moveName;
    }
}