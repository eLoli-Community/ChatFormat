package com.eloli.chatformat.message.events;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public final class ClickEvent {
    private final Action action;
    private final String value;

    private ClickEvent(final @NonNull Action action, final @NonNull String value) {
        this.action = requireNonNull(action, "action");
        this.value = requireNonNull(value, "value");
    }

    public static ClickEvent openUrl(String url) {
        return new ClickEvent(Action.OPEN_URL, url);
    }

    public static ClickEvent runCommand(String command) {
        return new ClickEvent(Action.RUN_COMMAND, command);
    }

    public static ClickEvent suggestCommand(String command) {
        return new ClickEvent(Action.SUGGEST_COMMAND, command);
    }

    public static ClickEvent changePage(String page) {
        return new ClickEvent(Action.CHANGE_PAGE, page);
    }

    public static ClickEvent changePage(int page) {
        return changePage(String.valueOf(page));
    }

    public static ClickEvent copyToClipboard(String text) {
        return new ClickEvent(Action.COPY_TO_CLIPBOARD, text);
    }

    public Action action() {
        return this.action;
    }

    public String value() {
        return this.value;
    }

    public enum Action {
        OPEN_URL("open_url"),
        RUN_COMMAND("run_command"),
        SUGGEST_COMMAND("suggest_command"),
        CHANGE_PAGE("change_page"),
        COPY_TO_CLIPBOARD("copy_to_clipboard");
        public static final Map<String, Action> NAMES = Arrays.stream(Action.values()).collect(Collectors.toUnmodifiableMap(Action::toString, (v) -> v));
        private final String name;

        Action(String name) {
            this.name = name;
        }

        @Override
        public @NonNull String toString() {
            return this.name;
        }
    }
}

