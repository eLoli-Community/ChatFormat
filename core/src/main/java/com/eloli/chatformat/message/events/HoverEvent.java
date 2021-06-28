package com.eloli.chatformat.message.events;

import com.eloli.chatformat.message.components.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class HoverEvent<T> {
    public static HoverEvent<Component> showText(Component text) {
        return new HoverEvent<>(Action.SHOW_TEXT, text);
    }
    public static HoverEvent<String> showItem(String itemId) {
        return new HoverEvent<>(Action.SHOW_ITEM, itemId);
    }
    public static HoverEvent<ShowEntity> showEntity(String id, Component name) {
        return new HoverEvent<>(Action.SHOW_ENTITY, new ShowEntity(id, name));
    }
    private final Action<?> action;
    private final T value;
    private HoverEvent(Action<T> action, T value) {
        this.action = requireNonNull(action, "action");
        this.value = requireNonNull(value, "value");
    }

    public Action<?> action() {
        return action;
    }

    public T value() {
        return value;
    }

    public static final class ShowEntity {
        public final String id;
        public final Component name;
        private ShowEntity(String id, Component name) {
            this.id = id;
            this.name = name;
        }
    }
    public static final class Action<V> {
        public static final Action<Component> SHOW_TEXT = new Action<>("show_text", Component.class);
        public static final Action<String> SHOW_ITEM = new Action<>("show_item", String.class);
        public static final Action<ShowEntity> SHOW_ENTITY = new Action<>("show_entity", ShowEntity.class);
        public static Action<?>[] values(){
            return new Action[]{SHOW_TEXT,SHOW_ITEM,SHOW_ENTITY};
        }

        public static final Map<String, Action<?>> NAMES = Arrays.stream(Action.values()).collect(Collectors.toUnmodifiableMap(Action::toString, (v)-> v));

        private final String name;
        private final Class<V> type;
        Action(final String name, final Class<V> type) {
            this.name = name;
            this.type = type;
        }

        public String name() {
            return name;
        }
        public Class<V> type() {
            return type;
        }
    }
}
