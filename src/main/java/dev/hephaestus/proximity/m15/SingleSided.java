package dev.hephaestus.proximity.m15;

import dev.hephaestus.proximity.app.api.rendering.RenderData;
import dev.hephaestus.proximity.app.api.rendering.elements.Group;
import dev.hephaestus.proximity.app.api.rendering.elements.Tree;
import dev.hephaestus.proximity.mtg.Card;
import dev.hephaestus.proximity.mtg.MTGOptions;
import dev.hephaestus.proximity.mtg.MTGTemplate;
import dev.hephaestus.proximity.mtg.cards.SingleFacedCard;
import dev.hephaestus.proximity.mtg.data.Color;
import dev.hephaestus.proximity.mtg.data.Colors;

import static javafx.beans.binding.Bindings.or;

public class SingleSided extends MTGTemplate {
    @Override
    public int getDPI() {
        return 800;
    }

    @Override
    public int getWidth() {
        return 2176;
    }

    @Override
    public int getHeight() {
        return 2960;
    }

    @Override
    public void build(Card card, Group group) {
        this.addArt(card, group);

        group.group("frame", frame -> {
            frame.select("background", background -> {
                background.select("nyx", card.hasFrameEffect("nyx"), nyx -> {
                    nyx.image(card.isArtifact());
                    nyx.image(card.colors().isColorless());

                    nyx.select(card.colors().isOfSize(1), monocolored -> {
                        monocolored.image(card.colors().isWhite());
                        monocolored.image(card.colors().isBlue());
                        monocolored.image(card.colors().isBlack());
                        monocolored.image(card.colors().isRed());
                        monocolored.image(card.colors().isGreen());
                    });

                    nyx.image("gold");
                });

                background.select("snow", card.isType("snow"), snow -> {
                    snow.image(card.isArtifact());
                    snow.image(card.isLand());

                    snow.select(card.colors().isOfSize(1), monocolored -> {
                        monocolored.image(card.colors().isWhite());
                        monocolored.image(card.colors().isBlue());
                        monocolored.image(card.colors().isBlack());
                        monocolored.image(card.colors().isRed());
                        monocolored.image(card.colors().isGreen());
                    });

                    snow.image("gold");
                });

                background.select("normal", normal -> {
                    normal.image(card.isType("vehicle"));
                    normal.image("artifact", and(card.isArtifact(), not(card.isLand())));
                    normal.image(card.isLand());

                    normal.select(card.colors().isOfSize(1), monocolored -> {
                        monocolored.image(card.colors().isWhite());
                        monocolored.image(card.colors().isBlue());
                        monocolored.image(card.colors().isBlack());
                        monocolored.image(card.colors().isRed());
                        monocolored.image(card.colors().isGreen());
                    });

                    for (Colors pair : Color.combinations(2)) {
                        normal.image(pair.toString(), and(card.colors().isHybrid(), card.colors().isEqualTo(pair)));
                    }

                    normal.image("gold", not(card.colors().isColorless()));
                });
            });

            frame.tree("pinlines/normal", pinlines -> {
                pinlines.image("artifact", and(card.isArtifact(), card.colors().isColorless(), not(card.isLand())));
                pinlines.image("colorless", and(card.colors().isColorless(), not(card.isLand())));

                for (Colors pair : Color.combinations(2)) {
                    pinlines.image(card.colors().isEqualTo(pair));
                }

                pinlines.select(card.colors().isOfSize(1), monocolored -> {
                    monocolored.image(card.colors().isWhite());
                    monocolored.image(card.colors().isBlue());
                    monocolored.image(card.colors().isBlack());
                    monocolored.image(card.colors().isRed());
                    monocolored.image(card.colors().isGreen());
                });

                pinlines.image(card.colors().isGold());
                pinlines.image("colorless");
            }, new Tree.Level("nonsnow", card.isType("snow")), new Tree.Level("nonland", card.isLand()));

            frame.tree("crown", card.isLegendary(), tree -> {
                tree.select(crown -> {
                    crown.image("artifact", and(card.isArtifact(), card.colors().isColorless(), not(card.isLand())));

                    crown.select(card.colors().isOfSize(1), monocolored -> {
                        monocolored.image(card.colors().isWhite());
                        monocolored.image(card.colors().isBlue());
                        monocolored.image(card.colors().isBlack());
                        monocolored.image(card.colors().isRed());
                        monocolored.image(card.colors().isGreen());
                    });

                    for (Colors pair : Color.combinations(2)) {
                        crown.image(card.colors().isEqualTo(pair));
                    }

                    crown.image(card.isLand());
                    crown.image(card.colors().isGold());
                    crown.image("colorless");
                });
            }, new Tree.Level("normal", new Tree.Level.Branch("hollow", or(card.hasFrameEffect("nyxtouched"), card.hasFrameEffect("companion")))));
            
            frame.select("border", border -> {
                border.image(card.isLegendary());
                border.image("normal");
            });
            
            frame.tree("plates/normal", tree -> {
                tree.select(plates -> {
                    plates.select(card.colors().isOfSize(1), monocolored -> {
                        monocolored.image(card.colors().isWhite());
                        monocolored.image(card.colors().isBlue());
                        monocolored.image(card.colors().isBlack());
                        monocolored.image(card.colors().isRed());
                        monocolored.image(card.colors().isGreen());
                    });

                    plates.image("artifact", and(card.isArtifact(), not(card.isLand())));
                    plates.image(card.colors().isColorless());
                    plates.image(card.colors().isHybrid());
                    plates.image("gold");
                });
            }, new Tree.Level("snow", card.isType("snow")));

            frame.select("pt", and(card.json.contains("power"), card.json.contains("toughness")), pt -> {
                pt.image(card.isType("vehicle"));

                pt.select(card.colors().isOfSize(1), monocolored -> {
                    monocolored.image(card.colors().isWhite());
                    monocolored.image(card.colors().isBlue());
                    monocolored.image(card.colors().isBlack());
                    monocolored.image(card.colors().isRed());
                    monocolored.image(card.colors().isGreen());
                });

                pt.image("artifact", and(card.isArtifact(), card.colors().isColorless(), not(card.isLand())));
                pt.image("colorless", or(and(card.colors().isOfSize(2), card.colors().isHybrid()), and(card.colors().isColorless(), not(card.isLand()))));
                pt.image("gold");
            });
        });

        group.group("text", text -> {
            this.addNamePlateText(card, text);
            this.addTypeLine(card, text);
            this.addTextbox(card, text);
            this.addPowerAndToughness(card, text);
            this.addCollectorInfo(card, text);
        });
    }

    @Override
    public void createOptions(Options<Card> options) {
        options.add(MTGOptions.FLAVOR_BAR);
        options.add(MTGOptions.TRUNCATE_FLASH);
        options.add(MTGOptions.SHOW_REMINDER_TEXT);
        options.add(MTGOptions.CUSTOM_ART);
        options.add(MTGOptions.ARTIST);
    }

    @Override
    public boolean canHandle(RenderData data) {
        return data instanceof SingleFacedCard;
    }
}
