package com.vecoo.movelarner.util;

import com.pixelmonmod.pixelmon.api.events.dialogue.DialogueInputEvent;
import com.pixelmonmod.pixelmon.api.util.helpers.NetworkHelper;
import com.pixelmonmod.pixelmon.comm.packetHandlers.dialogue.OpenDialogueInputPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DialogueInputRegistry {
    private static final Map<UUID, Builder> REGISTERED_DIALOGUES = new ConcurrentHashMap<>();

    private DialogueInputRegistry() {
        throw new UnsupportedOperationException("[MoveLearner] Invalid usage this is a static registry");
    }

    private static void addDialogue(ServerPlayerEntity player, Builder builder) {
        REGISTERED_DIALOGUES.put(player.getUUID(), builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    @SubscribeEvent
    public static void onDialogueSubmit(DialogueInputEvent.Submitted event) {
        Builder builder = REGISTERED_DIALOGUES.get(event.getPlayer().getUUID());

        if (builder != null && builder.submitHandler != null) {
            builder.submitHandler.accept(event);
        }

        REGISTERED_DIALOGUES.remove(event.getPlayer().getUUID());
    }

    @SubscribeEvent
    public static void onDialogueSubmit(DialogueInputEvent.ClosedScreen event) {
        Builder builder = REGISTERED_DIALOGUES.get(event.getPlayer().getUUID());

        if (builder != null && builder.closeHandler != null) {
            builder.closeHandler.accept(event);
        }

        REGISTERED_DIALOGUES.remove(event.getPlayer().getUUID());
    }

    public static class Builder {
        private ITextComponent title;
        private ITextComponent text;
        private String defaultInputValue = "";
        private boolean closeOnEsc = true;
        private Consumer<DialogueInputEvent.Submitted> submitHandler;
        private Consumer<DialogueInputEvent.ClosedScreen> closeHandler;

        public Builder title(ITextComponent title) {
            this.title = title;
            return this;
        }

        public Builder text(ITextComponent text) {
            this.text = text;
            return this;
        }

        public Builder closeOffEscape() {
            this.closeOnEsc = false;
            return this;
        }

        public Builder submitHandler(Consumer<DialogueInputEvent.Submitted> submitHandler) {
            this.submitHandler = submitHandler;
            return this;
        }

        public Builder closeHandler(Consumer<DialogueInputEvent.ClosedScreen> closeHandler) {
            this.closeHandler = closeHandler;
            return this;
        }

        public Builder defaultInputValue(String defaultInputValue) {
            this.defaultInputValue = defaultInputValue;
            return this;
        }

        public void open(ServerPlayerEntity player) {
            NetworkHelper.sendPacket(player, new OpenDialogueInputPacket(this.title, this.text, this.defaultInputValue, this.closeOnEsc));
            DialogueInputRegistry.addDialogue(player, this);
        }
    }
}