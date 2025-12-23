package com.vecoo.movelearner.config;

import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.ConfigSerializable;
import lombok.Getter;

@Getter
@ConfigSerializable
public class ServerConfig {
    private String currencyType = "item";
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
}