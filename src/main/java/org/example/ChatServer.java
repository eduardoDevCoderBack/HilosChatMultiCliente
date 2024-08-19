package org.example;


import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();


    public static void main(String[] args) {
        System.out.println("chat server started...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New cliente connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcastMessage (String message, ClientHandler excludeHandler) {
        for (ClientHandler handler : clientHandlers) {
            if (handler != excludeHandler) {
                handler.sendMessage(message);
            }
        }
    }

    static void removeClient (ClientHandler handler) {
        clientHandlers.remove(handler);
        System.out.println("Client disconnected...");
    }

}