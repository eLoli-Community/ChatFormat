package com.eloli.chatformat.models;

public interface IPlayer {
    String getName();

    String getUuid();

    String getDisplayName();

    String getLocation();

    boolean hasPermission(String permission);

    void notice();
}
