function require(a) {
    return __core.require(a);
}

function apply(a, b) {
    return __core.apply(a, b);
}

function isBungee(){
    return __core.getLoader().isBungee();
}


function getPlayer(a){
    return __core.getLoader().getPlayer(a);
}

var __color = Java.type("com.eloli.chatformat.message.models.Color");
var __namedcolor = Java.type("com.eloli.chatformat.message.models.NamedColor");

var Color = {};
Color.fromValue = function (value) {
    return __color.fromValue(value);
}
Color.fromRgb = function (r, g, b) {
    return __color.fromRgb(r, g, b);
}
Color.fromHex = function (hex) {
    return __color.fromHex(hex);
}

Color.d0 = __namedcolor.black;
Color.d1 = __namedcolor.dark_blue;
Color.d2 = __namedcolor.dark_green;
Color.d3 = __namedcolor.dark_aqua;
Color.d4 = __namedcolor.dark_red;
Color.d5 = __namedcolor.dark_purple;
Color.d6 = __namedcolor.gold;
Color.d7 = __namedcolor.gray;
Color.d8 = __namedcolor.dark_gray;
Color.d9 = __namedcolor.blue;
Color.da = __namedcolor.green;
Color.db = __namedcolor.aqua;
Color.dc = __namedcolor.red;
Color.dd = __namedcolor.light_purple;
Color.de = __namedcolor.yellow;
Color.df = __namedcolor.white;

Color.black = __namedcolor.black;
Color.dark_blue = __namedcolor.dark_blue;
Color.dark_green = __namedcolor.dark_green;
Color.dark_aqua = __namedcolor.dark_aqua;
Color.dark_red = __namedcolor.dark_red;
Color.dark_purple = __namedcolor.dark_purple;
Color.gold = __namedcolor.gold;
Color.gray = __namedcolor.gray;
Color.dark_gray = __namedcolor.dark_gray;
Color.blue = __namedcolor.blue;
Color.green = __namedcolor.green;
Color.aqua = __namedcolor.aqua;
Color.red = __namedcolor.red;
Color.light_purple = __namedcolor.light_purple;
Color.yellow = __namedcolor.yellow;
Color.white = __namedcolor.white;

var __style = Java.type("com.eloli.chatformat.message.models.Style");
var Style = {};

function style() {
    return new __style();
}

var __clickEvent = Java.type("com.eloli.chatformat.message.events.ClickEvent");
var ClickEvent = {};
ClickEvent.openUrl = function (url) {
    return __clickEvent.openUrl(url);
}
ClickEvent.runCommand = function (command) {
    return __clickEvent.runCommand(command);
}
ClickEvent.suggestCommand = function (command) {
    return __clickEvent.suggestCommand(command);
}
ClickEvent.changePage = function (page) {
    return __clickEvent.changePage(page);
}
ClickEvent.copyToClipboard = function (text) {
    return __clickEvent.copyToClipboard(text);
}

var __hoverEvent = Java.type("com.eloli.chatformat.message.events.HoverEvent");
var HoverEvent = {};
HoverEvent.showText = function (text) {
    return __hoverEvent.showText(text);
}
HoverEvent.showItem = function (itemId) {
    return __hoverEvent.showItem(itemId);
}
HoverEvent.showEntity = function (id, name) {
    return __hoverEvent.showEntity(id, name);
}

var __component = Java.type("com.eloli.chatformat.message.components.Component");
var components = __component.components;

var __textual = Java.type("com.eloli.chatformat.message.components.Textual");
var Textual = {};
Textual.legacy = function (a, b) {
    if (b === undefined) {
        return __textual.legacy(a);
    } else {
        return __textual.legacy(a, b);
    }
}
Textual.of = function (a, b) {
    if (b === undefined) {
        return __textual.of(a);
    } else {
        return __textual.of(a, b);
    }
}

function text(a, b) {
    return Textual.legacy(a, b);
}

function plain(a, b) {
    return Textual.of(a, b);
}
