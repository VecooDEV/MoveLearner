package com.vecoo.movelearner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/locale.yml")
@ConfigSerializable
public class LocaleConfig extends AbstractYamlConfig {
    private String reload = "&e(!) Configs reloaded.";
    private String openLearn = "&e(!) Learn menu opened %player%.";
    private String buyMoveItem = "&e(!) You buy %move% to %pokemon% for x%amount% rare candy.";
    private String buyMoveCurrency = "&e(!) You buy %move% to %pokemon% for %amount% pokedollars.";
    private String buyMoveFree = "&e(!) You buy %move% to %pokemon% for free.";

    private String notItems = "&c(!) You need x%amount% candy to move.";
    private String notCurrency = "&c(!) You need %amount% pokedollars to move.";
    private String notPokemon = "&c(!) The pokemon you selected is not on your team.";
    private String alreadyMove = "&c(!) The pokemon %pokemon% already %move% move.";
    private String notValidItem = "&c(!) The item specified in the configuration is incorrect or does not exist, please inform the administration about it.";
    private String error = "&c(!) Error. Try again.";

    public String getReload() {
        return this.reload;
    }

    public String getOpenLearn() {
        return this.openLearn;
    }

    public String getBuyMoveItem() {
        return this.buyMoveItem;
    }

    public String getBuyMoveCurrency() {
        return this.buyMoveCurrency;
    }

    public String getBuyMoveFree() {
        return this.buyMoveFree;
    }

    public String getNotItems() {
        return this.notItems;
    }

    public String getNotCurrency() {
        return this.notCurrency;
    }

    public String getNotPokemon() {
        return this.notPokemon;
    }

    public String getAlreadyMove() {
        return this.alreadyMove;
    }

    public String getNotValidItem() {
        return this.notValidItem;
    }

    public String getError() {
        return this.error;
    }
}