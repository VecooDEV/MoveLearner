package com.vecoo.movelearner.config;

import com.vecoo.extralib.gson.UtilGson;
import com.vecoo.movelearner.MoveLearner;

public class LocaleConfig {
    private String reload = "&e(!) Configs reloaded.";
    private String openLearn = "&e(!) Learn menu opened %player%.";
    private String buyMoveItem = "&e(!) You buy %move% to %pokemon% for x%amount% rare candy.";
    private String buyMoveFree = "&e(!) You buy %move% to %pokemon% for free.";

    private String notItems = "&c(!) You need x%amount% candy to move.";
    private String notPokemon = "&c(!) The pokemon you selected is not on your team.";
    private String alreadyMove = "&c(!) The pokemon %pokemon% already %move% move.";
    private String notValidItem = "&c(!) The item specified in the configuration is incorrect or does not exist, please inform the administration about it.";

    public String getReload() {
        return this.reload;
    }

    public String getOpenLearn() {
        return this.openLearn;
    }

    public String getBuyMoveItem() {
        return this.buyMoveItem;
    }

    public String getBuyMoveFree() {
        return this.buyMoveFree;
    }

    public String getNotItems() {
        return this.notItems;
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

    private void save() {
        UtilGson.writeFileAsync("/config/MoveLearner/", "locale.json", UtilGson.getGson().toJson(this)).join();
    }

    public void init() {
        boolean completed = UtilGson.readFileAsync("/config/MoveLearner/", "locale.json", el -> {
            LocaleConfig config = UtilGson.getGson().fromJson(el, LocaleConfig.class);

            this.reload = config.getReload();
            this.openLearn = config.getOpenLearn();
            this.buyMoveItem = config.getBuyMoveItem();
            this.buyMoveFree = config.getBuyMoveFree();
            this.notItems = config.getNotItems();
            this.notPokemon = config.getNotPokemon();
            this.alreadyMove = config.getAlreadyMove();
            this.notValidItem = config.getNotValidItem();
        }).join();

        if (!completed) {
            MoveLearner.getLogger().error("Error init locale config, generating new locale config.");
            save();
        }
    }
}