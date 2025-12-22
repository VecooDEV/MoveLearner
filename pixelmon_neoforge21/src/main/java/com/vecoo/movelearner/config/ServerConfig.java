package com.vecoo.movelearner.config;

import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.ConfigSerializable;
import lombok.Getter;

@Getter
@ConfigSerializable
public class ServerConfig {
    private String currencyType = "item";
    private String itemPriceMove = "pixelmon:rare_candy";
    private boolean itemStrongTags = true;
    private boolean hideAlreadyMove = false;
    private boolean hmMove = true;
    private boolean eggMove = true;
    private int levelMovePrice = 16;
    private int tmTrMovePrice = 16;
    private int hmMovePrice = 32;
    private int tutorMovePrice = 16;
    private int transferMovePrice = 16;
    private int eggMovePrice = 32;
}