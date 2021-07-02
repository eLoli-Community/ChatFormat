package com.eloli.chatformat.bungee;

import com.eloli.chatformat.Core;
import com.eloli.chatformat.channel.ChatPackage;
import com.eloli.chatformat.channel.ReloadPackage;
import com.eloli.chatformat.message.IChatEvent;
import com.eloli.chatformat.message.components.Component;
import com.eloli.chatformat.models.ILoader;
import com.eloli.chatformat.models.IPlayer;
import com.eloli.sodioncore.channel.BadSignException;
import com.eloli.sodioncore.channel.ServerPacket;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;

public class BungeeLoader extends Plugin implements ILoader, Listener {
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

        getProxy().registerChannel(core.getChannel().name);

        getProxy().getPluginManager().registerCommand(this, new Command("chatformat") {
            @Override
            public void execute(CommandSender sender, String[] args) {
                if(sender instanceof ProxiedPlayer){
                    ((ProxiedPlayer) sender).getServer().sendData(
                            core.getChannel().name,
                            core.getChannel().getClientFactory(ReloadPackage.class).encode(
                                    new ReloadPackage())
                    );
                }

                if (!sender.hasPermission("chatformat.reload")) {
                    sender.sendMessage("[ChatFormat-BungeeCord] permission denied.");
                    return;
                }
                sender.sendMessage("[ChatFormat-BungeeCord] reloading");
                queue.addFirst(() -> {
                    try {
                        core.init();
                        sender.sendMessage("[ChatFormat-BungeeCord] reloaded");
                    } catch (Exception e) {
                        sender.sendMessage("[ChatFormat-BungeeCord] failed");
                        getLogger().log(Level.WARNING, e.getMessage(), e);
                    }
                });
            }
        });
        getProxy().getPluginManager().registerListener(this, this);
        handleThread = new Thread(() -> {
            while (true) {
                try {
                    queue.take().run();
                }catch (InterruptedException ignore){
                    //
                } catch (Exception e) {
                    getLogger().log(Level.WARNING, e.getMessage(), e);
                }
            }
        });

        queue.add(() -> {
            try {
                core.init();
            } catch (ScriptException | IOException e) {
                getLogger().log(Level.WARNING, e.getMessage(), e);
            }
        });

        handleThread.start();
    }

    @Override
    public void onDisable() {
        if (handleThread.isAlive()) {
            handleThread.interrupt();
        }
    }

    @EventHandler
    public void onMessage(PluginMessageEvent event) throws BadSignException {
        if (event.getSender() instanceof Server
                && event.getReceiver() instanceof ProxiedPlayer) {
            if(event.getTag().equals(core.getChannel().name)){
                ServerPacket packet = core.getChannel().getServerFactory(event.getData())
                        .parser(event.getData());
                if(packet instanceof ChatPackage) {
                    queue.add(() -> {
                        for (ProxiedPlayer recipient : getProxy().getPlayers()) {
                            if (recipient.getServer().equals(event.getSender())) {
                                // continue;
                            }
                            try {
                                Component component = core.callOnChat(
                                        new IChatEvent(
                                                new PlayerImpl((ProxiedPlayer) event.getReceiver()),
                                                new PlayerImpl(recipient),
                                                ((ChatPackage) packet).message
                                        ));
                                recipient.sendMessage(Translator.trans(component));
                            } catch (Exception e) {
                                getLogger().log(Level.WARNING, e.getMessage(), e);
                            }
                        }
                    });
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    public Path getConfigPath() {
        return getDataFolder().toPath();
    }

    @Override
    public String replace(IPlayer player, String text) {
        return text;
    }

    @Override
    public boolean isBungee() {
        return true;
    }
}
