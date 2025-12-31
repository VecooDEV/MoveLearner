package com.vecoo.movelearner.config;

import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.ConfigSerializable;
import lombok.Getter;

@Getter
@ConfigSerializable
public class LocaleConfig {
    private String reload = "&e(!) Configs reloaded.";
    private String openLearn = "&e(!) Learn menu opened %player%.";
    private String buyMove = "&e(!) You buy %move% to %pokemon% for %amount% %currency%.";
    private String buyMoveFree = "&e(!) You buy %move% to %pokemon% for free.";

    private String errorReload = "&c(!) Reload error, checking console and fixes config.";
    private String notCurrency = "&c(!) You need %amount% %currency% to move.";
    private String notPokemon = "&c(!) The pokemon you selected is not on your team.";
    private String alreadyMove = "&c(!) The pokemon %pokemon% already %move% move.";
    private String notValidCurrency = "&c(!) Type currency - %currency% not supported.";
    private String notValidItem = "&c(!) The item specified in the configuration is incorrect or does not exist, please inform the administration about it.";
    private String error = "&c(!) Error. Try again.";

    private String itemCurrency = "rare candy";
    private String cobblemonCurrency = "cobbledollars";
    private String impactorCurrency = "money";
}