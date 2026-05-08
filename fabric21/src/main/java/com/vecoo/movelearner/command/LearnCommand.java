package com.vecoo.movelearner.command;

import com.mojang.brigadier.CommandDispatcher;
import com.vecoo.extralib.util.PermissionUtil;
import com.vecoo.extralib.util.TextUtil;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.ui.pages.SelectPokemonPage;
import com.vecoo.movelearner.util.PermissionNodes;
import lombok.val;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class LearnCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection commandSelection) {
        dispatcher.register(Commands.literal("learn")
                .executes(e -> executeLearn(e.getSource().getPlayerOrException()))

                .then(Commands.literal("open")
                        .requires(p -> PermissionUtil.hasPermission(p, PermissionNodes.LEARN_OPEN_COMMAND))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(e -> executeLearnPlayer(e.getSource(), EntityArgument.getPlayer(e, "player")))))

                .then(Commands.literal("reload")
                        .requires(p -> PermissionUtil.hasPermission(p, PermissionNodes.LEARN_RELOAD_COMMAND))
                        .executes(e -> executeReload(e.getSource()))));
    }

    private static int executeLearn(@NotNull ServerPlayer player) {
        new SelectPokemonPage(player).open();
        return 1;
    }

    private static int executeLearnPlayer(@NotNull CommandSourceStack source, @NotNull ServerPlayer player) {
        new SelectPokemonPage(player).openForce();

        source.sendSystemMessage(TextUtil.formatMessage(MoveLearner.getInstance().getLocaleConfig().getOpenLearn()
                .replace("%player%", player.getName().getString())));
        return 1;
    }

    private static int executeReload(@NotNull CommandSourceStack source) {
        val localeConfig = MoveLearner.getInstance().getLocaleConfig();

        try {
            MoveLearner.getInstance().loadConfig();
        } catch (Exception e) {
            source.sendSystemMessage(TextUtil.formatMessage(localeConfig.getErrorReload()));
            MoveLearner.getLogger().error(e.getMessage());
            return 0;
        }

        source.sendSystemMessage(TextUtil.formatMessage(localeConfig.getReload()));
        return 1;
    }
}