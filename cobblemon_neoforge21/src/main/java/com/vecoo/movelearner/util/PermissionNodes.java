package com.vecoo.movelearner.util;

import com.vecoo.extralib.util.PermissionUtil;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class PermissionNodes {
    private static final Set<PermissionNode<?>> PERMISSION_LIST = new HashSet<>();

    public static PermissionNode<Boolean> LEARN_COMMAND = PermissionUtil.getPermissionNode("minecraft.command.learn", true);
    public static PermissionNode<Boolean> LEARN_OPEN_COMMAND = PermissionUtil.getPermissionNode("minecraft.command.learn.open", false);
    public static PermissionNode<Boolean> LEARN_RELOAD_COMMAND = PermissionUtil.getPermissionNode("minecraft.command.learn.reload", false);

    public static void registerPermission(@NotNull PermissionGatherEvent.Nodes event) {
        PERMISSION_LIST.add(LEARN_COMMAND);
        PERMISSION_LIST.add(LEARN_OPEN_COMMAND);
        PERMISSION_LIST.add(LEARN_RELOAD_COMMAND);

        for (PermissionNode<?> node : PERMISSION_LIST) {
            if (!event.getNodes().contains(node)) {
                event.addNodes(node);
            }
        }
    }
}