package com.vecoo.movelearner.config;

import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.ConfigSerializable;
import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.meta.Comment;
import lombok.Getter;

@Getter
@ConfigSerializable
@SuppressWarnings("FieldCanBeLocal")
public class ServerConfig {
    @Comment("Currency type: item, pixelmon, impactor.")
    private String currencyType = "item";
    @Comment("The item that will be used as payment.")
    private String itemPriceMove = "pixelmon:rare_candy";
    @Comment("Will there be strict compliance testing of items?")
    private boolean itemStrongTags = true;
    @Comment("Hide already move pokemon?")
    private boolean hideAlreadyMove = false;
    @Comment("Will it be possible to set up this type of attack?")
    private boolean hmMove = true;
    @Comment("Will it be possible to set up this type of attack?")
    private boolean eggMove = true;
    @Comment("Number of currency per attack type.")
    private int levelMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int tmTrMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int hmMovePrice = 32;
    @Comment("Number of currency per attack type.")
    private int tutorMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int transferMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int eggMovePrice = 32;
}