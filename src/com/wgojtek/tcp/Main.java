package com.wgojtek.tcp;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(5991)){
            while(true){
                new Server(serverSocket.accept()).start();
            }
        }catch(IOException e){
            System.out.println("Server exception " + e.getMessage());
        }
    }
}