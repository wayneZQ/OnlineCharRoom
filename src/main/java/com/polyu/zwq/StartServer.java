package com.polyu.zwq;

import com.polyu.zwq.Bootstrap.ChatRoomServer;

public class StartServer {
    public static void main(String[] args) {
        new ChatRoomServer(7000).run();
    }
}
