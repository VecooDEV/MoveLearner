package com.vecoo.movelearner.ui.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum MoveFilter {
    ALL("all"),
    LEVEL("level"),
    TM_TR("tm_tr"),
    HM("hm"),
    TUTOR("tutor"),
    TRANSFER("transfer"),
    EGG("egg");

    @NotNull
    private final String id;
}
