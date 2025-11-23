package com.vecoo.movelearner.ui;

import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.config.GuiConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class ButtonNames {
    @NotNull
    public static Component getMoveName(@NotNull MoveTemplate move, @NotNull ServerPlayer player) {
        GuiConfig guiConfig = MoveLearner.getInstance().getGuiConfig();
        MutableComponent moveName = move.getDisplayName();

        if (guiConfig.isLocalizedNameMoves() && !player.getLanguage().equals("en_us")) {
            moveName.append(UtilChat.formatMessage(guiConfig.getLocalizedMoveName()
                    .replace("%move%", move.getName())));
        }

        return moveName;
    }
}