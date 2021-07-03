package com.eloli.chatformat.bukkit;

import com.eloli.chatformat.models.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerImpl implements IPlayer {

    public Player player;

    public PlayerImpl(Player player) {
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
    public String getLocation() {
        return player.getWorld().getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void notice() {
        Bukkit.getScheduler().runTask(BukkitLoader.instance,()->{
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 500.0f, 1.0f);
        });
    }

    public Player getHandle() {
        return player;
    }
}
