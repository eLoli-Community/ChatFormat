package com.eloli.chatformat.message.models;

public class Color {
    private int value;
    protected Color(int value) {
        this.value = value;
    }

    public int value(){
        return value;
    }

    public Color value(int value){
        this.value = value;
        return this;
    }

    public static Color fromValue(int value){
        return new Color(value);
    }
    public static Color fromRgb(int r, int g, int b) {
        return fromValue((r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff));
    }
    public static Color fromHex(String string) {
        if(string.startsWith("#")) {
            final String hexString = string.substring(1);
            if(hexString.length() != 3 && hexString.length() != 6) {
                return null;
            }
            final int hex;
            try {
                hex = Integer.parseInt(hexString, 16);
            } catch(final NumberFormatException e) {
                return null;
            }

            if(hexString.length() == 6) {
                return new Color(hex);
            } else {
                final int red = (hex & 0xf00) >> 8 | (hex & 0xf00) >> 4;
                final int green = (hex & 0x0f0) >> 4 | (hex & 0x0f0);
                final int blue = (hex & 0x00f) << 4 | (hex & 0x00f);
                return fromRgb(red, green, blue);
            }
        }
        return null;
    }
    public static Color fromString(String string) {
        if(string.startsWith("#")) {
            return fromHex(string);
        }
        return NamedColor.ofName(string);
    }
}
