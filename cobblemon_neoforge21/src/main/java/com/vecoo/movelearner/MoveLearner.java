package com.vecoo.movelearner;

import com.mojang.logging.LogUtils;
import com.vecoo.movelearner.command.LearnCommand;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.LocaleConfig;
import com.vecoo.movelearner.config.ServerConfig;
import com.vecoo.movelearner.util.PermissionNodes;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import org.slf4j.Logger;

@Mod(MoveLearner.MOD_ID)
public class MoveLearner {
    public static final String MOD_ID = "movelearner";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static MoveLearner instance;

    private ServerConfig config;
    private LocaleConfig locale;
    private GuiConfig gui;

    private MinecraftServer server;

    public MoveLearner() {
        instance = this;

        loadConfig();

        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPermissionGather(PermissionGatherEvent.Nodes event) {
        PermissionNodes.registerPermission(event);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        LearnCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        this.server = event.getServer();
    }

    public void loadConfig() {
        try {
            this.config = new ServerConfig();
            this.config.init();
            this.locale = new LocaleConfig();
            this.locale.init();
            this.gui = new GuiConfig();
            this.gui.init();
        } catch (Exception e) {
            LOGGER.error("Error load config.", e);
        }
    }

    public static MoveLearner getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public ServerConfig getConfig() {
        return instance.config;
    }

    public LocaleConfig getLocale() {
        return instance.locale;
    }

    public GuiConfig getGuiConfig() {
        return instance.gui;
    }

    public MinecraftServer getServer() {
        return instance.server;
    }
}