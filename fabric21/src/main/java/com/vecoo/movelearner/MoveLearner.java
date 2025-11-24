package com.vecoo.movelearner;

import com.mojang.logging.LogUtils;
import com.vecoo.movelearner.command.LearnCommand;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.LocaleConfig;
import com.vecoo.movelearner.config.ServerConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public class MoveLearner implements ModInitializer {
    public static final String MOD_ID = "movelearner";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static MoveLearner instance;

    private ServerConfig config;
    private LocaleConfig locale;
    private GuiConfig gui;

    private MinecraftServer server;

    @Override
    public void onInitialize() {
        instance = this;

        loadConfig();

        CommandRegistrationCallback.EVENT.register(LearnCommand::register);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> this.server = server);
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