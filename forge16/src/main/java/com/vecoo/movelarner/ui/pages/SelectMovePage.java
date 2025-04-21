package com.vecoo.movelarner.ui.pages;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.moves.Moves;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import com.vecoo.movelarner.MoveLearner;
import com.vecoo.movelarner.config.GuiConfig;
import com.vecoo.movelarner.config.ServerConfig;
import com.vecoo.movelarner.ui.ButtonLore;
import com.vecoo.movelarner.ui.ButtonName;
import com.vecoo.movelarner.ui.settings.PageFilter;
import com.vecoo.movelarner.util.DialogueInputRegistry;
import com.vecoo.movelarner.util.Utils;
import de.waterdu.atlantis.ui.api.*;
import de.waterdu.atlantis.util.entity.PlayerReference;
import de.waterdu.atlantis.util.text.TextUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectMovePage implements Page {
    private final Pokemon pokemon;
    private final String filter;
    private final String search;

    public SelectMovePage(Pokemon pokemon, String filter, String search) {
        this.pokemon = pokemon;
        this.filter = filter;
        this.search = search;
    }

    @Override
    public PageOptions getPageOptions(PlayerReference player) {
        return PageOptions.builder()
                .title(MoveLearner.getInstance().getGui().getSelectMoveTitle())
                .rows(6)
                .build();
    }

    @Override
    public void addButtons(PlayerReference player, ButtonCollector buttons) {
        Moves moves = pokemon.getForm().getMoves();

        List<ImmutableAttack> availableAttacks = listAttack(moves, filter);

        if (!search.isEmpty()) {
            availableAttacks = availableAttacks.stream()
                    .filter(attack -> attack.getAttackName().toLowerCase().startsWith(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        int page = 0;
        for (int i = 0; i < availableAttacks.size(); i++) {
            if (i > 0 && i % 45 == 0) page++;

            ImmutableAttack attack = availableAttacks.get(i);
            ItemStack itemStackTM = Utils.getTM(attack);
            ITextComponent movePriceLore = ButtonLore.movePrice(pokemon, attack);

            buttons.collect(Button.builder()
                    .directName(ButtonName.translatedTM(attack, player.entityDirect()))
                    .item(itemStackTM)
                    .lore(movePriceLore)
                    .index(i % 45)
                    .page(page)
                    .clickAction(clickData -> AtlantisUI.open(clickData.entity(), new AcceptPage(pokemon, attack, itemStackTM, filter)))
                    .build());
        }

        ItemStack fillerItem = Utils.parsedItemStackCustomModel(MoveLearner.getInstance().getGui().getFillerItem());
        GuiConfig guiConfig = MoveLearner.getInstance().getGui();

        buttons.collect(createButton(45, guiConfig.getPreviousPageName(), guiConfig.getPreviousPageItem())
                .prevPage()
                .build());

        buttons.collect(createButton(46, guiConfig.getFilterName(), guiConfig.getFilterItem())
                .lore(ButtonLore.filter(filter))
                .clickAction(clickData -> {
                    if (clickData.clickState().button() == ClickState.MouseButton.RIGHT) {
                        PageFilter.changeFilterRight(clickData.entity(), filter, pokemon);
                    } else {
                        PageFilter.changeFilterLeft(clickData.entity(), filter, pokemon);
                    }
                })
                .build());

        buttons.collect(createButton(47, guiConfig.getSearchName(), guiConfig.getSearchItem())
                .lore(TextUtils.asComponent(MoveLearner.getInstance().getGui().getSearchLore()))
                .clickAction(clickData -> {
                    if (clickData.clickState().button() == ClickState.MouseButton.RIGHT) {
                        if (!filter.isEmpty()) {
                            AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon, filter, ""));
                        }
                    } else {
                        DialogueInputRegistry.builder()
                                .title(TextUtils.asComponent(guiConfig.getSearchName()))
                                .text(TextUtils.asComponent(guiConfig.getSearchLoreDialogue()))
                                .closeHandler(closedScreen -> AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon, filter, "")))
                                .submitHandler(submitted -> AtlantisUI.open(clickData.entity(), new SelectMovePage(pokemon, PageFilter.ALL, submitted.getInput().toLowerCase())))
                                .open(clickData.entity());
                    }
                })
                .build());

        buttons.collect(createButton(49, guiConfig.getBackName(), guiConfig.getBackItem())
                .clickAction(clickData -> AtlantisUI.open(clickData.entity(), new SelectPokemonPage()))
                .build());

        buttons.collect(createButton(53, guiConfig.getNextPageName(), guiConfig.getNextPageItem())
                .nextPage()
                .build());

        if (guiConfig.isFillerChoiceMovesUI()) {
            IntStream.rangeClosed(48, 53)
                    .filter(slot -> slot != 49 && slot != 53)
                    .forEach(slot -> buttons.collect(new Decoration(fillerItem, slot)));
        }
    }

    private Button.Builder createButton(int index, String name, String item) {
        return Button.builder()
                .name(name)
                .item(Utils.parsedItemStackCustomModel(item))
                .index(index);
    }

    private List<ImmutableAttack> listAttack(Moves moves, String filter) {
        ServerConfig config = MoveLearner.getInstance().getConfig();

        switch (filter) {
            case "all": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack))
                        .filter(attack -> config.isHmMove() || !moves.getHMMoves().contains(attack))
                        .filter(attack -> config.isEggMove() || !moves.getEggMoves().contains(attack))
                        .collect(Collectors.toList());
            }

            case "level": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack) && moves.getAllLevelUpMoves().contains(attack))
                        .collect(Collectors.toList());
            }

            case "tmtr": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack) && moves.getTMMoves().contains(attack) || moves.getTRMoves().stream().map(ITechnicalMove::getAttack).collect(Collectors.toList()).contains(attack))
                        .collect(Collectors.toList());
            }

            case "hm": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack) && moves.getHMMoves().contains(attack))
                        .collect(Collectors.toList());
            }

            case "tutor": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack) && moves.getTutorMoves().contains(attack))
                        .collect(Collectors.toList());
            }

            case "transfer": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack) && moves.getTransferMoves().contains(attack))
                        .collect(Collectors.toList());
            }

            case "egg": {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack) && moves.getEggMoves().contains(attack))
                        .collect(Collectors.toList());
            }

            default: {
                return moves.getAllMoves().stream()
                        .filter(attack -> !pokemon.getMoveset().hasAttack(attack))
                        .collect(Collectors.toList());
            }
        }
    }
}
