package com.example.semproj.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static ServerSocket serverSocket;
    public static final int PORT = 8000;
    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);

        while (true)
        {

            ClientConnections client = new ClientConnections();
            Socket socket = serverSocket.accept();
                new Thread(() ->
                {
                    try{
                        client.RequestHandler(socket);
                    } catch (IOException e)
                    {
                        //throw new RuntimeException(e);
                    }
                }).start();
        }
    }
}
