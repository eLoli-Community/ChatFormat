package com.eloli.chatformat.channel;

import com.eloli.chatformat.models.IPlayer;
import com.eloli.sodioncore.channel.ClientPacket;
import com.eloli.sodioncore.channel.ServerPacket;
import com.eloli.sodioncore.channel.util.FieldWrapper;
import com.eloli.sodioncore.channel.util.Priority;

import java.util.List;

public class ReloadPackage extends ClientPacket {
    public static final List<FieldWrapper> fieldWrapperList = resolveFieldWrapperList(ReloadPackage.class);

    public ReloadPackage(){

    }

    @Override
    public List<FieldWrapper> getFieldWrapperList() {
        return fieldWrapperList;
    }
}
