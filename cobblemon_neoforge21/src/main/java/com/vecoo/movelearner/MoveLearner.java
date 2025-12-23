package com.vecoo.movelearner;

import com.mojang.logging.LogUtils;
import com.vecoo.extralib.config.YamlConfigFactory;
import com.vecoo.movelearner.api.currency.CurrencyProviderRegistry;
import com.vecoo.movelearner.api.currency.impl.ImpactorCurrencyProvider;
import com.vecoo.movelearner.api.currency.impl.ItemCurrencyProvider;
import com.vecoo.movelearner.command.LearnCommand;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.LocaleConfig;
import com.vecoo.movelearner.config.ServerConfig;
import com.vecoo.movelearner.util.PermissionNodes;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import org.slf4j.Logger;

import java.nio.file.Path;

@Mod(MoveLearner.MOD_ID)
public class MoveLearner {
    public static final String MOD_ID = "movelearner";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Getter
    private static MoveLearner instance;

    private ServerConfig serverConfig;
    private LocaleConfig localeConfig;
    private GuiConfig guiConfig;

    private MinecraftServer server;

    public MoveLearner() {
        instance = this;

        loadConfig();
        loadCurrencies();

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
        this.serverConfig = YamlConfigFactory.load(ServerConfig.class, Path.of("config/MoveLearner/config.yml"));
        this.localeConfig = YamlConfigFactory.load(LocaleConfig.class, Path.of("config/MoveLearner/locale.yml"));
        this.guiConfig = YamlConfigFactory.load(GuiConfig.class, Path.of("config/MoveLearner/gui.yml"));
    }

    private void loadCurrencies() {
        CurrencyProviderRegistry.register("item", ItemCurrencyProvider::new);

        if (ModList.get().isLoaded("impactor")) {
            CurrencyProviderRegistry.register("impactor", ImpactorCurrencyProvider::new);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public ServerConfig getServerConfig() {
        return instance.serverConfig;
    }

    public LocaleConfig getLocaleConfig() {
        return instance.localeConfig;
    }

    public GuiConfig getGuiConfig() {
        return instance.guiConfig;
    }

    public MinecraftServer getServer() {
        return instance.server;
    }
}