package com.vecoo.movelearner.config;

import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.ConfigSerializable;
import com.vecoo.extralib.shade.spongepowered.configurate.objectmapping.meta.Comment;
import lombok.Getter;

@Getter
@ConfigSerializable
@SuppressWarnings("FieldCanBeLocal")
public class ServerConfig {
    @Comment("Currency type: item, cobbledollars, impactor.")
    private String currencyType = "item";
    @Comment("The item that will be used as payment.")
    private String itemPriceMove = "cobblemon:rare_candy";
    @Comment("Will there be strict compliance testing of items?")
    private boolean itemStrongTags = true;
    @Comment("Hide already move pokemon?")
    private boolean hideAlreadyMove = false;
    @Comment("Will it be possible to set up this type of attack?")
    private boolean legacyMove = true;
    @Comment("Will it be possible to set up this type of attack?")
    private boolean specialMove = true;
    @Comment("Will it be possible to set up this type of attack?")
    private boolean eggMove = true;
    @Comment("Number of currency per attack type.")
    private int levelMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int tmMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int legacyMovePrice = 32;
    @Comment("Number of currency per attack type.")
    private int tutorMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int specialMovePrice = 16;
    @Comment("Number of currency per attack type.")
    private int eggMovePrice = 32;
}