package com.vecoo.movelearner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/config.yml")
@ConfigSerializable
public class ServerConfig extends AbstractYamlConfig {
    private boolean useCurrency = false;
    private String itemPriceMove = "pixelmon:rare_candy";
    private boolean itemStrongTags = true;
    private boolean hmMove = true;
    private boolean eggMove = true;
    private int levelMovePrice = 16;
    private int tmTrMovePrice = 16;
    private int hmMovePrice = 32;
    private int tutorMovePrice = 16;
    private int transferMovePrice = 16;
    private int eggMovePrice = 32;

    public boolean isUseCurrency() {
        return this.useCurrency;
    }

    public String getItemPriceMove() {
        return this.itemPriceMove;
    }

    public boolean isItemStrongTags() {
        return this.itemStrongTags;
    }

    public boolean isHmMove() {
        return this.hmMove;
    }

    public boolean isEggMove() {
        return this.eggMove;
    }

    public int getLevelMovePrice() {
        return this.levelMovePrice;
    }

    public int getTmTrMovePrice() {
        return this.tmTrMovePrice;
    }

    public int getHmMovePrice() {
        return this.hmMovePrice;
    }

    public int getTutorMovePrice() {
        return this.tutorMovePrice;
    }

    public int getTransferMovePrice() {
        return this.transferMovePrice;
    }

    public int getEggMovePrice() {
        return this.eggMovePrice;
    }
}