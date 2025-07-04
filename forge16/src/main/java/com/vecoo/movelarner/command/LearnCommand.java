package com.vecoo.movelarner.command;

import com.mojang.brigadier.CommandDispatcher;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.ui.pages.SelectPokemonPage;
import de.waterdu.atlantis.ui.api.AtlantisUI;
import de.waterdu.atlantis.util.entity.PermissionUtils;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class LearnCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("learn")
                .requires(p -> PermissionUtils.hasPermission(p, "minecraft.command.learn"))
                .executes(e -> executeLearn(e.getSource().getPlayerOrException()))

                .then(Commands.literal("open")
                        .requires(p -> PermissionUtils.hasPermission(p, "minecraft.command.learn.open"))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(e -> executeLearnPlayer(e.getSource(), EntityArgument.getPlayer(e, "player")))))

                .then(Commands.literal("reload")
                        .requires(p -> PermissionUtils.hasPermission(p, "minecraft.command.learn.reload"))
                        .executes(e -> executeReload(e.getSource()))));
    }

    private static int executeLearn(ServerPlayerEntity player) {
        AtlantisUI.open(player, new SelectPokemonPage());
        return 1;
    }

    private static int executeLearnPlayer(CommandSource source, ServerPlayerEntity player) {
        source.sendSuccess(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getOpenLearn()
                .replace("%player%", player.getName().getString())), false);

        AtlantisUI.open(player, new SelectPokemonPage());
        return 1;
    }

    private static int executeReload(CommandSource source) {
        MoveLearner.getInstance().loadConfig();

        source.sendSuccess(TextUtils.asComponent(MoveLearner.getInstance().getLocale().getReload()), false);
        return 1;
    }
}