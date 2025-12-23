package com.vecoo.movelearner.api.currency;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CurrencyProviderRegistry {
    private static final Map<String, Supplier<CurrencyProvider>> PROVIDERS = new HashMap<>();

    public static void register(@NotNull String key, @NotNull Supplier<CurrencyProvider> supplier) {
        PROVIDERS.put(key.toLowerCase(), supplier);
    }

    public static CurrencyProvider get(@NotNull String key) {
        return PROVIDERS.getOrDefault(key.toLowerCase(), () -> null).get();
    }
}