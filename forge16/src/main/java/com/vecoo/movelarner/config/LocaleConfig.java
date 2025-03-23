package com.vecoo.movelarner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/locale.yml")
@ConfigSerializable
public class LocaleConfig extends AbstractYamlConfig {
    private String reload = "&e(!) Configs reloaded.";

    private String openLearn = "&e(!) Learn menu opened %player%.";
    private String buyAttack = "&e(!) You buy %attack% to %pokemon% for x%amount% rare candy.";

    private String notItems = "&c(!) You need x%amount% candy to attack.";
    private String error = "&c(!) Error. Try again.";

    public String getReload() {
        return this.reload;
    }

    public String getOpenLearn() {
        return this.openLearn;
    }

    public String getBuyAttack() {
        return this.buyAttack;
    }

    public String getNotItems() {
        return this.notItems;
    }

    public String getError() {
        return this.error;
    }
}