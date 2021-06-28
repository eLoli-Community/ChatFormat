package com.eloli.chatformat.bukkit;

import com.eloli.chatformat.models.IPlayer;
import org.bukkit.entity.Player;

public class PlayerImpl implements IPlayer {

    public Player player;

    public PlayerImpl(Player player){
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getUuid() {
        return player.getUniqueId().toString();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public Player getHandle(){
        return player;
    }
}
