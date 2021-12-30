package com.polyu.zwq;

import com.polyu.zwq.Bootstrap.ChatRoomClient;

import java.util.Scanner;

public class StartClient {
    public static void main(String[] args) {
        new ChatRoomClient("127.0.0.1",7000).run();

    }
}
