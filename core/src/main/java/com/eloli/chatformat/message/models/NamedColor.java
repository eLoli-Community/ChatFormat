package com.eloli.chatformat.message.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NamedColor extends Color {
    public static final NamedColor black = new NamedColor('0', "black", 0x000000);
    public static final NamedColor dark_blue = new NamedColor('1', "dark_blue", 0x0000aa);
    public static final NamedColor dark_green = new NamedColor('2', "dark_green", 0x00aa00);
    public static final NamedColor dark_aqua = new NamedColor('3', "dark_aqua", 0x00aaaa);
    public static final NamedColor dark_red = new NamedColor('4', "dark_red", 0xaa0000);
    public static final NamedColor dark_purple = new NamedColor('5', "dark_purple", 0xaa00aa);
    public static final NamedColor gold = new NamedColor('6', "gold", 0xffaa00);
    public static final NamedColor gray = new NamedColor('7', "gray", 0xaaaaaa);
    public static final NamedColor dark_gray = new NamedColor('8', "dark_gray", 0x555555);
    public static final NamedColor blue = new NamedColor('9', "blue", 0x5555ff);
    public static final NamedColor green = new NamedColor('a', "green", 0x55ff55);
    public static final NamedColor aqua = new NamedColor('b', "aqua", 0x55ffff);
    public static final NamedColor red = new NamedColor('c', "red", 0xff5555);
    public static final NamedColor light_purple = new NamedColor('d', "light_purple", 0xff55ff);
    public static final NamedColor yellow = new NamedColor('e', "yellow", 0xffff55);
    public static final NamedColor white = new NamedColor('f', "white", 0xffffff);

    public static final NamedColor d0 = black;
    public static final NamedColor d1 = dark_blue;
    public static final NamedColor d2 = dark_green;
    public static final NamedColor d3 = dark_aqua;
    public static final NamedColor d4 = dark_red;
    public static final NamedColor d5 = dark_purple;
    public static final NamedColor d6 = gold;
    public static final NamedColor d7 = gray;
    public static final NamedColor d8 = dark_gray;
    public static final NamedColor d9 = blue;
    public static final NamedColor da = green;
    public static final NamedColor db = aqua;
    public static final NamedColor dc = red;
    public static final NamedColor dd = light_purple;
    public static final NamedColor de = yellow;
    public static final NamedColor df = white;


    public static final List<NamedColor> VALUES = Collections.unmodifiableList(Arrays.asList(d0, d1, d2, d3, d4, d5,
            d6, d7, d8, d9, da, db, dc, dd, de, df));
    public static final Map<Character, NamedColor> LEGACIES = VALUES.stream().
            collect(Collectors.toMap(NamedColor::getLegacy, (v) -> v));
    public static final Map<String, NamedColor> NAMES = VALUES.stream().
            collect(Collectors.toMap(NamedColor::toString, (v) -> v));
    public static final Map<Integer, NamedColor> EXACTS = VALUES.stream().
            collect(Collectors.toMap(Color::value, (v) -> v));


    protected final char legacy;
    protected final String name;

    private NamedColor(char legacy, String name, int value) {
        super(value);
        this.legacy = legacy;
        this.name = name;
    }

    public static NamedColor ofExact(int value) {
        return EXACTS.get(value);
    }

    public static NamedColor ofName(String name) {
        return NAMES.get(name);
    }

    public static NamedColor ofLegacy(char c) {
        return LEGACIES.get(c);
    }

    public char getLegacy() {
        return this.legacy;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
