package com.vecoo.movelarner.config;

import com.pixelmonmod.pixelmon.api.config.api.data.ConfigPath;
import com.pixelmonmod.pixelmon.api.config.api.yaml.AbstractYamlConfig;
import info.pixelmon.repack.org.spongepowered.objectmapping.ConfigSerializable;

@ConfigPath("config/MoveLearner/config.yml")
@ConfigSerializable
public class ServerConfig extends AbstractYamlConfig {
    private String itemPriceMove = "pixelmon:rare_candy";
    private boolean levelMove = true;
    private boolean tmTrMove = true;
    private boolean hmMove = true;
    private boolean tutorMove = true;
    private boolean transferMove = true;
    private boolean eggMove = true;
    private int levelMovePrice = 16;
    private int tmTrMovePrice = 16;
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

    public boolean isLevelMove() {
        return this.levelMove;
    }

    public boolean isTmTrMove() {
        return this.tmTrMove;
    }

    public boolean isHmMove() {
        return this.hmMove;
    }

    public boolean isTutorMove() {
        return this.tutorMove;
    }

    public boolean isTransferMove() {
        return this.transferMove;
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