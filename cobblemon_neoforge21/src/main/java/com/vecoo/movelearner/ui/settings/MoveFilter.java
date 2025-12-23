package com.vecoo.movelearner.ui.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum MoveFilter {
    ALL("all"),
    LEVEL("level"),
    TM("tm"),
    LEGACY("legacy"),
    TUTOR("tutor"),
    SPECIAL("special"),
    EGG("egg");

    @NotNull
    private final String id;
}
