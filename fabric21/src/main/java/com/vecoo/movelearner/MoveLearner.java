package com.vecoo.movelearner;

import com.mojang.logging.LogUtils;
import com.vecoo.extralib.loader.YamlLoader;
import com.vecoo.movelearner.api.currency.CurrencyProviderRegistry;
import com.vecoo.movelearner.command.LearnCommand;
import com.vecoo.movelearner.config.GuiConfig;
import com.vecoo.movelearner.config.LocaleConfig;
import com.vecoo.movelearner.config.ServerConfig;
import com.vecoo.movelearner.impl.CobblemonCurrencyProvider;
import com.vecoo.movelearner.impl.ImpactorCurrencyProvider;
import com.vecoo.movelearner.impl.ItemCurrencyProvider;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.io.IOException;

public class MoveLearner implements ModInitializer {
    public static final String MOD_ID = "movelearner";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Getter
    private static MoveLearner instance;

    private ServerConfig serverConfig;
    private LocaleConfig localeConfig;
    private GuiConfig guiConfig;

    private MinecraftServer server;

    @Override
    public void onInitialize() {
        instance = this;

        loadConfig();
        loadCurrencies();

        CommandRegistrationCallback.EVENT.register(LearnCommand::register);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> this.server = server);
    }

    public void loadConfig() {
        try {
            this.serverConfig = YamlLoader.load(ServerConfig.class, "config/movelearner/config.yml", false);
            this.localeConfig = YamlLoader.load(LocaleConfig.class, "config/movelearner/locale.yml", false);
            this.guiConfig = YamlLoader.load(GuiConfig.class, "config/movelearner/gui.yml", false);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void loadCurrencies() {
        CurrencyProviderRegistry.register("item", ItemCurrencyProvider::new);

        if (FabricLoader.getInstance().isModLoaded("cobbledollars")) {
            CurrencyProviderRegistry.register("cobbledollars", CobblemonCurrencyProvider::new);
        }

        if (FabricLoader.getInstance().isModLoaded("impactor")) {
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