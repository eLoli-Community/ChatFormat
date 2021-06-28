package com.eloli.chatformat.message.models;

import com.eloli.chatformat.message.events.ClickEvent;
import com.eloli.chatformat.message.events.HoverEvent;

public class Style {
    public String font = null;
    public Color color = NamedColor.white;
    public boolean obfuscated = false;
    public boolean bold = false;
    public boolean strikethrough = false;
    public boolean underlined = false;
    public boolean italic = false;
    public ClickEvent clickEvent = null;
    public HoverEvent hoverEvent = null;
    public String insertion = null;

    public Style font(String font) {
        this.font = font;
        return this;
    }

    public Style color(Color color) {
        this.color = color;
        return this;
    }

    public Style obfuscated() {
        this.obfuscated = true;
        return this;
    }

    public Style bold() {
        this.bold = true;
        return this;
    }

    public Style strikethrough() {
        this.strikethrough = true;
        return this;
    }

    public Style underlined() {
        this.underlined = true;
        return this;
    }

    public Style italic() {
        this.italic = true;
        return this;
    }

    public Style clickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    public Style hoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    public Style insertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    @Override
    public Style clone() {
        Style result = new Style();
        result.font = this.font;
        result.color = this.color;
        result.obfuscated = this.obfuscated;
        result.bold = this.bold;
        result.strikethrough = this.strikethrough;
        result.underlined = this.underlined;
        result.italic = this.italic;
        result.clickEvent = this.clickEvent;
        result.hoverEvent = this.hoverEvent;
        result.insertion = this.insertion;
        return result;
    }
}
