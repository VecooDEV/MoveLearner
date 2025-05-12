package com.vecoo.movelarner.ui;

import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.Button;

public class Buttons {
    public static Button.Builder createButton(int index, String name, String itemId) {
        return Button.builder()
                .name(name)
                .item(Utils.parseItemCustomModel(itemId))
                .index(index);
    }

    public static Button.Builder createButton(int index, String name, String lore, String itemId) {
        return Button.builder()
                .name(name)
                .lore(lore)
                .item(Utils.parseItemCustomModel(itemId))
                .index(index);
    }
}