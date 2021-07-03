package com.eloli.chatformat.bungee;

import com.eloli.chatformat.models.IPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerImpl implements IPlayer {
    public final ProxiedPlayer handle;

    public PlayerImpl(ProxiedPlayer player){
        this.handle = player;
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public String getUuid() {
        return handle.getUniqueId().toString();
    }

    @Override
    public String getDisplayName() {
        return handle.getDisplayName();
    }

    @Override
    public String getLocation() {
        return handle.getServer().getInfo().getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return handle.hasPermission(permission);
    }

    @Override
    public void notice() {
        //
    }
}