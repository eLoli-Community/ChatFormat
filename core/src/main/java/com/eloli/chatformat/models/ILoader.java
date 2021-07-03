package com.eloli.chatformat.models;

import java.nio.file.Path;

public interface ILoader {
    Path getConfigPath();

    String replace(IPlayer player, String text);

    boolean isBungee();

    IPlayer getPlayer(String name);
}
