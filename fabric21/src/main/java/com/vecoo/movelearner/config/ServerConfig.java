package com.vecoo.movelearner.config;

import com.vecoo.extralib.gson.UtilGson;
import com.vecoo.movelearner.MoveLearner;

public class ServerConfig {
    private String itemPriceMove = "cobblemon:rare_candy";
    private boolean itemStrongTags = true;
    private boolean hideAlreadyMove = false;
    private boolean legacyMove = true;
    private boolean specialMove = true;
    private boolean eggMove = true;
    private int levelMovePrice = 16;
    private int tmMovePrice = 16;
    private int legacyMovePrice = 32;
    private int tutorMovePrice = 16;
    private int specialMovePrice = 16;
    private int eggMovePrice = 32;

    public String getItemPriceMove() {
        return this.itemPriceMove;
    }

    public boolean isItemStrongTags() {
        return this.itemStrongTags;
    }

    public boolean isHideAlreadyMove() {
        return this.hideAlreadyMove;
    }

    public boolean isLegacyMove() {
        return this.legacyMove;
    }

    public boolean isSpecialMove() {
        return this.specialMove;
    }

    public boolean isEggMove() {
        return this.eggMove;
    }

    public int getLevelMovePrice() {
        return this.levelMovePrice;
    }

    public int getTmMovePrice() {
        return this.tmMovePrice;
    }

    public int getLegacyMovePrice() {
        return this.legacyMovePrice;
    }

    public int getTutorMovePrice() {
        return this.tutorMovePrice;
    }

    public int getSpecialMovePrice() {
        return this.specialMovePrice;
    }

    public int getEggMovePrice() {
        return this.eggMovePrice;
    }

    private void write() {
        UtilGson.writeFileAsync("/config/MoveLearner/", "config.json", UtilGson.newGson().toJson(this)).join();
    }

    public void init() {
        boolean completed = UtilGson.readFileAsync("/config/MoveLearner/", "config.json", el -> {
            ServerConfig config = UtilGson.newGson().fromJson(el, ServerConfig.class);

            this.itemPriceMove = config.getItemPriceMove();
            this.itemStrongTags = config.isItemStrongTags();
            this.hideAlreadyMove = config.isHideAlreadyMove();
            this.legacyMove = config.isLegacyMove();
            this.specialMove = config.isSpecialMove();
            this.eggMove = config.isEggMove();
            this.levelMovePrice = config.getLevelMovePrice();
            this.tmMovePrice = config.getTmMovePrice();
            this.legacyMovePrice = config.getLegacyMovePrice();
            this.tutorMovePrice = config.getTutorMovePrice();
            this.specialMovePrice = config.getSpecialMovePrice();
            this.eggMovePrice = config.getEggMovePrice();
        }).join();

        if (!completed) {
            MoveLearner.getLogger().error("Error init config, generating new config.");
            write();
        }
    }
}