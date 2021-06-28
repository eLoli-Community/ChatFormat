package com.eloli.chatformat.models;

public interface IPlayer {
    String getName();
    String getUuid();
    String getDisplayName();
    boolean hasPermission(String permission);
}
