package com.vecoo.movelarner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/config.yml")
@ConfigSerializable
public class ServerConfig extends AbstractYamlConfig {
    private String itemPriceMove = "pixelmon:rare_candy";
    private int levelMovePrice = 16;
    private int tmMovePrice = 16;
    private int trMovePrice = 16;
    private int hmMovePrice = 32;
    private int tutorMovePrice = 16;
    private int transferMovePrice = 16;
    private int eggMovePrice = 32;
    private boolean localizedNameMoves = true;
    private boolean fillerUI = true;
    private boolean informationUI = true;

    public String getItemPriceMove() {
        return this.itemPriceMove;
    }

    public int getLevelMovePrice() {
        return this.levelMovePrice;
    }

    public int getTmMovePrice() {
        return this.tmMovePrice;
    }

    public int getTrMovePrice() {
        return this.trMovePrice;
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

    public boolean isLocalizedNameMoves() {
        return this.localizedNameMoves;
    }

    public boolean isFillerUI() {
        return this.fillerUI;
    }

    public boolean isInformationUI() {
        return this.informationUI;
    }
}