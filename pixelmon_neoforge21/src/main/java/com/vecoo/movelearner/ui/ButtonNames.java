package com.vecoo.movelearner.ui;

import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ButtonNames {
    @NotNull
    public static Component getMoveName(@NotNull ImmutableAttack move, @NotNull ServerPlayer player) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();
        val moveName = move.getTranslatedName();

        if (guiConfig.isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
            moveName.append(UtilChat.formatMessage(guiConfig.getLocalizedMoveName()
                    .replace("%move%", move.getAttackName())));
        }

        return moveName;
    }
}