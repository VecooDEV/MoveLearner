package com.vecoo.movelearner.command;

import com.mojang.brigadier.CommandDispatcher;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.permission.UtilPermission;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.ui.pages.SelectPokemonPage;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class LearnCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("learn")
                .requires(p -> MoveLearner.getInstance().getConfig().isLowPermission() || UtilPermission.hasPermission(p, "minecraft.command.learn"))
                .executes(e -> executeLearn(e.getSource().getPlayerOrException()))

                .then(Commands.literal("open")
                        .requires(p -> UtilPermission.hasPermission(p, "minecraft.command.learn.open"))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(e -> executeLearnPlayer(e.getSource(), EntityArgument.getPlayer(e, "player")))))

                .then(Commands.literal("reload")
                        .requires(p -> UtilPermission.hasPermission(p, "minecraft.command.learn.reload"))
                        .executes(e -> executeReload(e.getSource()))));
    }

    private static int executeLearn(@NotNull ServerPlayer player) {
        new SelectPokemonPage(player).open();
        return 1;
    }

    private static int executeLearnPlayer(@NotNull CommandSourceStack source, @NotNull ServerPlayer player) {
        new SelectPokemonPage(player).reOpen();

        source.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocale().getOpenLearn()
                .replace("%player%", player.getName().getString())));
        return 1;
    }

    private static int executeReload(@NotNull CommandSourceStack source) {
        MoveLearner.getInstance().loadConfig();

        source.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocale().getReload()));
        return 1;
    }
}