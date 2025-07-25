package com.vecoo.movelarner;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.config.api.yaml.YamlConfigFactory;
import com.vecoo.movelarner.command.LearnCommand;
import com.vecoo.movelarner.config.GuiConfig;
import com.vecoo.movelarner.config.LocaleConfig;
import com.vecoo.movelarner.config.ServerConfig;
import com.vecoo.movelarner.util.DialogueInputRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(MoveLearner.MOD_ID)
public class MoveLearner {
    public static final String MOD_ID = "movelearner";
    private static final Logger LOGGER = LogManager.getLogger("MoveLearner");

    private static MoveLearner instance;

    private ServerConfig config;
    private LocaleConfig locale;
    private GuiConfig gui;

    private MinecraftServer server;

    public MoveLearner() {
        instance = this;

        this.loadConfig();

        MinecraftForge.EVENT_BUS.register(this);
        Pixelmon.EVENT_BUS.register(DialogueInputRegistry.class);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        LearnCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        this.server = event.getServer();

        PermissionAPI.registerNode("minecraft.command.learn", DefaultPermissionLevel.OP, "/learn");
        PermissionAPI.registerNode("minecraft.command.learn.open", DefaultPermissionLevel.OP, "/learn open <player>");
        PermissionAPI.registerNode("minecraft.command.learn.reload", DefaultPermissionLevel.OP, "/learn reload");
    }

    public void loadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(ServerConfig.class);
            this.locale = YamlConfigFactory.getInstance(LocaleConfig.class);
            this.gui = YamlConfigFactory.getInstance(GuiConfig.class);
        } catch (IOException e) {
            LOGGER.error("[MoveLearner] Error load config.", e);
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