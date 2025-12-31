package com.vecoo.movelearner.ui;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import lombok.val;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ButtonNames {
    @NotNull
    public static Component getMoveName(@NotNull MoveTemplate move, @NotNull ServerPlayer player) {
        val guiConfig = MoveLearner.getInstance().getGuiConfig();
        val moveName = move.getDisplayName();

        if (guiConfig.isLocalizedNameMoves() && !player.clientInformation().language().equals("en_us")) {
            moveName.append(UtilChat.formatMessage(guiConfig.getLocalizedMoveName()
                    .replace("%move%", move.getName())));
        }

        return moveName;
    }
}