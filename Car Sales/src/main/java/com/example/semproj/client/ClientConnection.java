package com.example.semproj.client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection {
    public static Socket socket;
    public static ObjectOutputStream writer;
    public static ObjectInputStream reader;

    public static void ConnectToServer() throws IOException
    {
        socket = new Socket("localhost", 8000);
        writer = new ObjectOutputStream(socket.getOutputStream());
        reader = new ObjectInputStream(socket.getInputStream());
    }
}
