package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer{
    //create a static Server socket at the beginning of main in the ChatServer class and instantiate
    //with a port number (>1000)
    public static int portNumber = 4444;
    public static List<ClientThread> clients = new ArrayList<>();

    public static void main(String[] args){
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(portNumber);
            //Do I need to pass in the instantiated serverSocket in acceptClients?
            System.out.println("Server started. Listening on port " + portNumber);
            acceptClients(serverSocket);
        } catch (IOException e){
            System.err.println("Could not listen on port: " + portNumber);
            System.exit(1);
        }
    }

    public static void acceptClients(ServerSocket serverSocket){
        while(true){
            try {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);
                synchronized (clients){
                    clients.add(client);
                }
                new Thread(client).start();
            } catch(IOException e){
                System.out.println("Accept failed on: "+ portNumber);
            }
            }
    }
//      what do I need for the demo to function correctly...
//    public static void broadcastMessage(String message){
//        synchronized (clients){
//            for (ClientThread client : clients) {
//                client.getWriter().println(message); //sends message to each client
//            }
//        }
//    }
}