package com.vecoo.movelearner.api.factory;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.moves.BenchedMove;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.vecoo.extralib.chat.UtilChat;
import com.vecoo.extralib.item.UtilItem;
import com.vecoo.extralib.player.UtilPlayer;
import com.vecoo.movelearner.MoveLearner;
import com.vecoo.movelearner.api.events.LearnEvent;
import com.vecoo.movelearner.config.LocaleConfig;
import com.vecoo.movelearner.ui.pages.AcceptPage;
import com.vecoo.movelearner.ui.settings.MoveFilter;
import com.vecoo.movelearner.util.Utils;
import eu.pb4.sgui.api.GuiHelpers;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoveLearnerFactoryUI {
    public static void openPage(@NotNull ServerPlayer player, @Nullable Pokemon pokemon, @NotNull SimpleGui page) {
        PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

        if (pokemon == null || partyStore.get(pokemon.getUuid()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocale().getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        page.openForce();
    }

    public static void learnMoveItem(@NotNull ServerPlayer player, @NotNull AcceptPage page) {
        LocaleConfig localeConfig = MoveLearner.getInstance().getLocale();
        PlayerPartyStore partyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon pokemon = page.getPreviousPage().getPokemon();

        if (pokemon == null || partyStore.get(pokemon.getUuid()) == null) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotPokemon()));
            GuiHelpers.close(player);
            return;
        }

        MoveSet moveset = pokemon.getMoveSet();
        Move move = page.getMove().create();

        if (moveset.getMoves().contains(move)) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getAlreadyMove()
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())
                    .replace("%move%", move.getName())));
            page.getPreviousPage().openForce();
            return;
        }

        ItemStack itemStack = UtilItem.parseItemCustomModel(MoveLearner.getInstance().getConfig().getItemPriceMove());

        if (itemStack.isEmpty()) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getNotValidItem()));
            GuiHelpers.close(player);
            return;
        }

        int price = Utils.getMovePrice(pokemon, move.getTemplate());

        if (!buyItem(player, pokemon, move.getTemplate(), itemStack, price)) {
            return;
        }

        if (!moveset.hasSpace()) {
            pokemon.getBenchedMoves().add(new BenchedMove(move.getTemplate(), 0));
            page.getPreviousPage().safeOpen(player);
        } else {
            moveset.add(move);
            page.getPreviousPage().openForce();
        }

        if (price > 0) {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveItem()
                    .replace("%move%", move.getName())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())
                    .replace("%amount%", String.valueOf(price))));
        } else {
            player.sendSystemMessage(UtilChat.formatMessage(localeConfig.getBuyMoveFree()
                    .replace("%move%", move.getName())
                    .replace("%pokemon%", pokemon.getDisplayName(false).getString())));
        }
    }

    public static void learnMoveItem(@NotNull ServerPlayer player, @NotNull MoveTemplate move, @NotNull Pokemon pokemon,
                                     @NotNull MoveFilter filter, @NotNull String search, int price) {
        learnMoveItem(player, new AcceptPage(player, move, pokemon, filter, search, price));
    }

    private static boolean buyItem(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull MoveTemplate move,
                                   @NotNull ItemStack itemStack, int amount) {
        if (MoveLearner.getInstance().getConfig().isItemStrongTags()) {
            if (UtilPlayer.countItemStack(player, itemStack) < amount) {
                sendNotItemsMessage(player, amount);
                return false;
            }

            if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyItem(player, pokemon, move.create(), itemStack, amount)).isCanceled()) {
                return false;
            }

            UtilPlayer.removeItemStack(player, itemStack, amount);
        } else {
            if (UtilPlayer.countItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA) < amount) {
                sendNotItemsMessage(player, amount);
                return false;
            }

            if (NeoForge.EVENT_BUS.post(new LearnEvent.BuyItem(player, pokemon, move.create(), itemStack, amount)).isCanceled()) {
                return false;
            }

            UtilPlayer.removeItemStackTag(player, itemStack, DataComponents.CUSTOM_MODEL_DATA, amount);
        }

        return true;
    }

    private static void sendNotItemsMessage(@NotNull ServerPlayer player, int amount) {
        player.sendSystemMessage(UtilChat.formatMessage(MoveLearner.getInstance().getLocale().getNotItems()
                .replace("%amount%", String.valueOf(amount))));
        GuiHelpers.close(player);
    }
}
