package com.eloli.chatformat.channel;

import com.eloli.chatformat.models.IPlayer;
import com.eloli.sodioncore.channel.PluginMessagePacket;
import com.eloli.sodioncore.channel.ServerPacket;
import com.eloli.sodioncore.channel.util.FieldWrapper;
import com.eloli.sodioncore.channel.util.Priority;

import java.util.List;
import java.util.UUID;

public class ChatPackage extends ServerPacket {
    public static final List<FieldWrapper> fieldWrapperList = resolveFieldWrapperList(ChatPackage.class);

    @Priority(0)
    public String message;

    public ChatPackage(){

    }

    public ChatPackage(IPlayer player, String message){
        this.message = message;
    }

    @Override
    public List<FieldWrapper> getFieldWrapperList() {
        return fieldWrapperList;
    }
}
