import dev.hephaestus.proximity.app.api.rendering.Template;
import dev.hephaestus.proximity.m15.SingleSided;

open module proximity.m15ultimate {
    requires dev.hephaestus.proximity.app;
    requires dev.hephaestus.proximity.mtg;
    requires dev.hephaestus.proximity.scryfall;
    requires java.desktop;
    requires dev.hephaestus.proximity.json;
    requires javafx.base;

    provides Template with SingleSided;
}