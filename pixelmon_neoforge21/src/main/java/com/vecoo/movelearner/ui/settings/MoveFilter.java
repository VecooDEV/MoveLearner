package com.vecoo.movelearner.ui.settings;

import org.jetbrains.annotations.NotNull;

public enum MoveFilter {
    ALL("all"),
    LEVEL("level"),
    TM_TR("tm_tr"),
    HM("hm"),
    TUTOR("tutor"),
    TRANSFER("transfer"),
    EGG("egg");

    private final String id;

    MoveFilter(@NotNull String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
