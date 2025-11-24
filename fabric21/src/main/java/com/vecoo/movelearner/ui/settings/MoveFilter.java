package com.vecoo.movelearner.ui.settings;

import org.jetbrains.annotations.NotNull;

public enum MoveFilter {
    ALL("all"),
    LEVEL("level"),
    TM("tm"),
    LEGACY("legacy"),
    TUTOR("tutor"),
    SPECIAL("special"),
    EGG("egg");

    private final String id;

    MoveFilter(@NotNull String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
