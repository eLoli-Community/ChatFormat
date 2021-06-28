package com.eloli.chatformat.bukkit;

import com.eloli.chatformat.message.components.Component;
import com.eloli.chatformat.message.components.Components;
import com.eloli.chatformat.message.components.Textual;
import com.eloli.chatformat.message.models.NamedColor;
import com.eloli.chatformat.message.models.Style;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.awt.*;

public class Translator {
    public static BaseComponent trans(Component component) {
        if (component instanceof Textual) {
            TextComponent textComponent = new TextComponent();
            textComponent.setText(((Textual) component).text);
            setStyle(textComponent, ((Textual) component).style);
            return textComponent;
        } else if (component instanceof Components) {
            BaseComponent result = null;
            for (int i = 0; i < ((Components) component).components.size(); i++) {
                Component subComponent = ((Components) component).components.get(i);
                BaseComponent pResult = trans(subComponent);
                if (pResult == null) {
                    continue;
                }
                if (result == null) {
                    result = pResult;
                } else {
                    result.addExtra(pResult);
                }
            }
            if (result == null) {
                result = new TextComponent();
            }
            return result;
        }
        return null;
    }

    public static void setStyle(TextComponent textComponent, Style style) {
        if (style.color instanceof NamedColor) {
            textComponent.setColor(ChatColor.getByChar(((NamedColor) style.color).getLegacy()));
        } else if (style.color != null) {
            try {
                textComponent.setColor(ChatColor.of(new Color(style.color.value())));
            } catch (NoSuchMethodError e) {
                textComponent.setColor(ChatColor.WHITE);
                Bukkit.getLogger().warning("Version below 1.16 don't support rgb color.");
            }
        }
        textComponent.setObfuscated(style.obfuscated);
        textComponent.setBold(style.bold);
        textComponent.setStrikethrough(style.strikethrough);
        textComponent.setUnderlined(style.underlined);
        textComponent.setItalic(style.italic);
        if (style.clickEvent != null) {
            textComponent.setClickEvent(new ClickEvent(
                    ClickEvent.Action.valueOf(style.clickEvent.action().toString().toUpperCase()),
                    style.clickEvent.value()));
        }
        if (style.hoverEvent != null) {
            switch (style.hoverEvent.action().name()) {
                case "show_text":
                    textComponent.setHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{trans((Component) style.hoverEvent.value())}
                    ));
                    break;
                case "show_item":
                    // TODO: Support soon
                    break;
                case "show_entity":
                    // TODO: Support soon
                    break;
            }
        }
        textComponent.setInsertion(style.insertion);
    }
}
