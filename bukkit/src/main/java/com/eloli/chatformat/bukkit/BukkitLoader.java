package com.eloli.chatformat.bukkit;

import com.eloli.chatformat.Core;
import com.eloli.chatformat.message.IChatEvent;
import com.eloli.chatformat.models.ILoader;
import com.eloli.chatformat.models.IPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;

public final class BukkitLoader extends JavaPlugin implements Listener, ILoader {

    public Set<AsyncPlayerChatEvent> willHandle = new HashSet<>();
    public Thread handleThread;
    public LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
    public Core core;
    @Override
    public void onEnable() {
        try {
            this.core = new Core(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginCommand("chatformat").setExecutor((sender, command, label, args) -> {
            queue.addFirst(()->{
                try {
                    core.init();
                }catch (Exception e){
                    getLogger().log(Level.WARNING,e.getMessage(),e);
                }
            });
            return true;
        });
        getServer().getPluginManager().registerEvents(this,this);
        handleThread = new Thread(()->{
            while (true){
                try {
                    queue.take().run();
                }catch (Exception e){
                    getLogger().log(Level.WARNING,e.getMessage(),e);
                }
            }
        });
        getServer().getScheduler().runTaskTimer(this,()->{
            if (!handleThread.isAlive()) {
                handleThread.start();
            }
        },0,20);
        queue.add(()->{
            try {
                core.init();
            } catch (ScriptException | IOException e) {
                getLogger().log(Level.WARNING,e.getMessage(),e);
            }
        });
    }

    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGHEST)
    public void onChatBefore(AsyncPlayerChatEvent event){
        willHandle.add(event);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatAfter(AsyncPlayerChatEvent event){
        if(willHandle.contains(event)){
            queue.add(()->{
                for (Player recipient : event.getRecipients()) {
                    try {
                        recipient.spigot().sendMessage(
                                ChatMessageType.CHAT,
                                Translator.trans(core.callOnChat(
                                        new IChatEvent(
                                                new PlayerImpl(event.getPlayer()),
                                                new PlayerImpl(recipient),
                                                event.getMessage()
                                        )
                                ))
                        );
                    }catch (Exception e){
                        getLogger().log(Level.WARNING,e.getMessage(),e);
                    }
                }
            });
        }
    }

    @Override
    public void onDisable() {
        if(handleThread.isAlive()){
            handleThread.interrupt();
        }
    }

    @Override
    public Path getConfigPath() {
        return getDataFolder().toPath();
    }

    @Override
    public String replace(IPlayer player, String text) {
        try {
            return PlaceholderAPI.setPlaceholders(
                    ((PlayerImpl) player).getHandle(),
                    text);
        }catch (NoClassDefFoundError e){
            getLogger().warning("No PlaceholderAPI found.");
            return text;
        }
    }
}