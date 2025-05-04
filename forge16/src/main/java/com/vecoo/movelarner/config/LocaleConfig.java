package com.vecoo.movelarner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/locale.yml")
@ConfigSerializable
public class LocaleConfig extends AbstractYamlConfig {
    private String reload = "&e(!) Configs reloaded.";

    private String openLearn = "&e(!) Learn menu opened %player%.";
    private String buyAttackItem = "&e(!) You buy %attack% to %pokemon% for x%amount% rare candy.";
    private String buyAttackCurrency = "&e(!) You buy %attack% to %pokemon% for %amount% currency.";
    private String buyAttackFree = "&e(!) You buy %attack% to %pokemon% for free.";

    private String notItems = "&c(!) You need x%amount% candy to attack.";
    private String notCurrency = "&c(!) You need %amount% currency to attack.";
    private String notPokemon = "&c(!) The pokemon %pokemon% you selected is not on your team.";
    private String alreadyAttack = "&c(!) The pokemon %pokemon% already %attack% attack.";
    private String notValidItem = "&c(!) The item specified in the configuration is incorrect or does not exist, please inform the administration about it.";
    private String error = "&c(!) Error. Try again.";

    public String getReload() {
        return this.reload;
    }

    public String getOpenLearn() {
        return this.openLearn;
    }

    public String getBuyAttackItem() {
        return this.buyAttackItem;
    }

    public String getBuyAttackCurrency() {
        return this.buyAttackCurrency;
    }

    public String getBuyAttackFree() {
        return this.buyAttackFree;
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

    public String getAlreadyAttack() {
        return this.alreadyAttack;
    }

    public String getNotValidItem() {
        return this.notValidItem;
    }

    public String getError() {
        return this.error;
    }
}