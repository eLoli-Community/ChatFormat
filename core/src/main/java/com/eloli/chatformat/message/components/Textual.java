package com.eloli.chatformat.message.components;

import com.eloli.chatformat.message.models.Color;
import com.eloli.chatformat.message.models.NamedColor;
import com.eloli.chatformat.message.models.Style;

import java.util.ArrayList;

public class Textual extends Component {
    public static final char COLOR_CHAR = '\u00A7';
    public static final Style RESET = new Style();
    public String text;
    public Style style;

    private Textual(String text, Style style) {
        this.text = text;
        this.style = style;
    }

    public static Component legacy(String message) {
        return legacy(message, NamedColor.white);
    }

    public static Component legacy(String message, Color defaultColor) {
        return legacy(message, new Style().color(defaultColor));
    }

    public static Component legacy(String message, Style defaultStyle) {
        ArrayList<Component> components = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        Textual component = Textual.of(message, defaultStyle);

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == COLOR_CHAR) {
                if (++i >= message.length()) {
                    break;
                }
                c = message.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c += 32;
                }
                Style format;
                if (c == '#' && i + 6 < message.length()) {
                    StringBuilder hex = new StringBuilder("#");
                    for (int j = 0; j < 6; j++) {
                        hex.append(message.charAt(i + 1 + j));
                    }
                    try {
                        format = new Style().color(Color.fromHex(hex.toString()));
                    } catch (IllegalArgumentException ex) {
                        format = null;
                    }

                    i += 6;
                } else {
                    format = legacy(c);
                }
                if (format == null) {
                    continue;
                }
                if (builder.length() > 0) {
                    Textual old = component;
                    component = new Textual("", old.style);
                    old.setText(builder.toString());
                    builder = new StringBuilder();
                    components.add(old);
                }
                if (format.bold) {
                    component.style.bold();
                } else if (format.italic) {
                    component.style.italic();
                } else if (format.underlined) {
                    component.style.underlined();
                } else if (format.strikethrough) {
                    component.style.strikethrough();
                } else if (format.obfuscated) {
                    component.style.obfuscated();
                } else if (format == RESET) {
                    format = defaultStyle;
                    component = new Textual("", format);
                } else {
                    format = defaultStyle.clone().color(format.color);
                    component = new Textual("", format);
                }
                continue;
            }
            builder.append(c);
        }

        component.setText(builder.toString());
        components.add(component);

        return Components.components(components.toArray(new Component[]{}));
    }

    public static Style legacy(char c) {
        if ((c >= '0' && c <= '9')
                || (c >= 'a' && c <= 'f')) {
            return new Style().color(NamedColor.ofLegacy(c));
        }
        switch (c) {
            case 'k':
                return new Style().obfuscated();
            case 'l':
                return new Style().bold();
            case 'm':
                return new Style().strikethrough();
            case 'n':
                return new Style().underlined();
            case 'o':
                return new Style().italic();
            case 'r':
                return RESET;
            default:
                return new Style();
        }
    }

    public static Textual of(String text) {
        return new Textual(text, new Style());
    }

    public static Textual of(String text, Color color) {
        return new Textual(text, new Style().color(color));
    }

    public static Textual of(String text, Style style) {
        return new Textual(text, style);
    }

    public Textual setText(String text) {
        this.text = text;
        return this;
    }
}
