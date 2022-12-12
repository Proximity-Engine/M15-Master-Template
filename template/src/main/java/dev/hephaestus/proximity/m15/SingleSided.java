package dev.hephaestus.proximity.m15;


import dev.hephaestus.proximity.app.api.Parent;
import dev.hephaestus.proximity.mtg.MTGOptions;
import dev.hephaestus.proximity.mtg.MTGTemplate;
import dev.hephaestus.proximity.mtg.cards.MagicCard;
import dev.hephaestus.proximity.mtg.cards.SingleFacedCard;
import dev.hephaestus.proximity.mtg.data.Color;
import dev.hephaestus.proximity.mtg.data.Colors;

public class SingleSided extends MTGTemplate<SingleFacedCard> {
    public SingleSided() {
        super("M15 Standard (Single Sided)", 2176, 2960, 800, true);
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof SingleFacedCard;
    }

    @Override
    public void createLayers(Parent<SingleFacedCard> layers) {
        addArt(layers);

        layers.group("frame", frame -> {
            frame.select("background", background -> {
                background.select("nyx", card -> card.hasFrameEffect("nyx"), nyx -> {
                    nyx.image("artifact", MagicCard::isArtifact);
                    nyx.image("colorless", MagicCard::isColorless);
                    nyx.select(card -> card.colors().count() == 1, monocolored -> {
                        /* This will resolve to "frame/background/nyx/W"
                           Proximity will then check for files with the following extensions, in order:
                             .png, .jpg, .jpeg, .jif, .jfif, .jfi
                         */
                        monocolored.image("W", MagicCard::isWhite);
                        monocolored.image("U", MagicCard::isBlue);
                        monocolored.image("B", MagicCard::isBlack);
                        monocolored.image("R", MagicCard::isRed);
                        monocolored.image("G", MagicCard::isGreen);
                    });
                    nyx.image("gold");
                });

                background.select("snow", card -> card.is("snow"), snow -> {
                    snow.image("artifact", card -> card.isArtifact() && !card.isLand());
                    snow.image("land", MagicCard::isLand);
                    snow.select(card -> card.colors().count() == 1, monocolored -> {
                        monocolored.image("W", MagicCard::isWhite);
                        monocolored.image("U", MagicCard::isBlue);
                        monocolored.image("B", MagicCard::isBlack);
                        monocolored.image("R", MagicCard::isRed);
                        monocolored.image("G", MagicCard::isGreen);
                    });
                    snow.image("gold");
                });

                background.select("normal", normal -> {
                    normal.image("vehicle", card -> card.is("vehicle"));
                    normal.image("artifact", card -> card.isArtifact() && !card.isLand());
                    normal.image("land", card -> card.is("land"));
                    normal.select(card -> card.colors().count() == 1, monocolored -> {
                        monocolored.image("W", MagicCard::isWhite);
                        monocolored.image("U", MagicCard::isBlue);
                        monocolored.image("B", MagicCard::isBlack);
                        monocolored.image("R", MagicCard::isRed);
                        monocolored.image("G", MagicCard::isGreen);
                    });

                    for (Colors pair : Color.combinations(2)) {
                        normal.image(pair.toString(), card -> card.isHybrid() && card.colors().equals(pair));
                    }

                    normal.image("gold", card -> !card.isColorless());
                });
            });

            frame.select("pinlines", pinlines -> {
                pinlines.tree("normal", tree -> {
                    tree.branch("snow", card -> card.is("snow"));
                    tree.branch("nonsnow");
                }, selector -> {
                    selector.tree(tree -> {
                        tree.branch("land", MagicCard::isLand);
                        tree.branch("nonland");
                    }, normal -> {
                        normal.image("artifact", card -> card.isArtifact() && card.isColorless() && !card.isLand());
                        normal.image("colorless", card -> card.isColorless() && !card.isLand());

                        for (Colors pair : Color.combinations(2)) {
                            normal.image(pair.toString(), card -> card.colors().equals(pair));
                        }

                        normal.select(card -> card.colors().count() == 1, monocolored -> {
                            /* This will resolve to one of the following:
                             *   - frame/pinlines/land/snow/W
                             *   - frame/pinlines/nonland/snow/W
                             *   - frame/pinlines/land/nonsnow/W
                             *   - frame/pinlines/nonland/nonsnow/W
                             */
                            monocolored.image("W", MagicCard::isWhite);
                            monocolored.image("U", MagicCard::isBlue);
                            monocolored.image("B", MagicCard::isBlack);
                            monocolored.image("R", MagicCard::isRed);
                            monocolored.image("G", MagicCard::isGreen);
                        });
                        normal.image("gold", card -> card.colors().count() > 2);
                        normal.image("colorless");
                    });
                });
            });

            frame.tree("crown", tree -> {
                tree.branch("hollow", card -> card.hasFrameEffect("nyxtouched") || card.hasFrameEffect("companion"));
                tree.branch("normal");
            }, crown -> {
                crown.select(normal -> {
                    normal.image("artifact", card -> card.isColorless() && card.isArtifact() && !card.isLand());
                    normal.select(monocolored -> {
                        /* This will resolve to one of the following:
                         *   - frame/crown/normal/W
                         *   - frame/crown/hollow/W
                         */
                        monocolored.image("W", MagicCard::isWhite);
                        monocolored.image("U", MagicCard::isBlue);
                        monocolored.image("B", MagicCard::isBlack);
                        monocolored.image("R", MagicCard::isRed);
                        monocolored.image("G", MagicCard::isGreen);
                    });
                    normal.image("land", MagicCard::isLand);
                    normal.image("gold", card -> !card.isColorless());
                    normal.image("colorless");
                });
            }, card -> card.is("legendary"));

            frame.select("border", border -> {
                border.image("legendary", card -> card.is("legendary"));
                border.image("normal");
            });

            frame.select("plates", plates -> {
                plates.tree("normal", tree -> {
                    tree.branch("snow", card -> card.is("snow"));
                    tree.branch("nonsnow");
                }, normal -> {
                    normal.select(card -> card.colors().count() == 1, monocolored -> {
                        /* This will resolve to one of the following:
                         *   - frame/plates/singlesided/snow/W
                         *   - frame/plates/singlesided/nonsnow/W
                         */
                        monocolored.image("W", MagicCard::isWhite);
                        monocolored.image("U", MagicCard::isBlue);
                        monocolored.image("B", MagicCard::isBlack);
                        monocolored.image("R", MagicCard::isRed);
                        monocolored.image("G", MagicCard::isGreen);
                    });
                    normal.image("artifact", card -> card.isArtifact() && !card.isLand());
                    normal.image("colorless", MagicCard::isColorless);
                    normal.image("hybrid", card -> (card.colors().count() == 2 && card.isHybrid()) || (card.isColorless() && !card.isLand()));
                    normal.image("gold");
                });
            });

            frame.select("pt", card -> card.json().has("power") && card.json().has("toughness"), plates -> {
                plates.image("vehicle", card -> card.is("vehicle"));
                plates.select(card -> card.colors().count() == 1, monocolored -> {
                    // This will resolve to "frame/pt/W"
                    monocolored.image("W", MagicCard::isWhite);
                    monocolored.image("U", MagicCard::isBlue);
                    monocolored.image("B", MagicCard::isBlack);
                    monocolored.image("R", MagicCard::isRed);
                    monocolored.image("G", MagicCard::isGreen);
                });
                plates.image("artifact", card -> card.isArtifact() && card.isColorless() && !card.isLand());
                plates.image("colorless", card -> (card.colors().count() == 2 && card.isHybrid()) || (card.isColorless() && !card.isLand()));
                plates.image("gold");
            });
        });

        layers.group("text", text -> {
            addNamePlateText(text);
            addCardName(text);
            addTypeLine(text);
            addTextbox(text);
            addPowerAndToughness(text);
            addCollectorInfo(text);
        });
    }

    @Override
    public void createOptions(Options<SingleFacedCard> options) {
        options.add(MTGOptions.SHOW_REMINDER_TEXT);
        options.add(MTGOptions.TRUNCATE_FLASH);
        options.add(MTGOptions.FLAVOR_BAR);
        options.add(MTGOptions.CUSTOM_ART);
        options.add(MTGOptions.ARTIST);
    }
}
