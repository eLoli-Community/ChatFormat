package com.eloli.chatformat.message;

import com.eloli.chatformat.models.IPlayer;

public class IChatEvent {
    public IPlayer sender;
    public IPlayer receiver;
    public String message;

    public IChatEvent(IPlayer sender, IPlayer receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }
}
