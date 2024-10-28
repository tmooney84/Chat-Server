package server;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer{
    //create a static Server socket at the beginning of main in the ChatServer class and instantiate
    //with a port number (>1000)
    public static int portNumber = 4444;
    public static List<ClientThread> clients = null;

    public static void main(String[] args){
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(portNumber);
            //Do I need to pass in the instantiated serverSocket in acceptClients?
            acceptClients(serverSocket);
        } catch (IOException e){
            System.err.println("Could not listen on port: " + portNumber);
            System.exit(1);
        }
    }

    public static void acceptClients(ServerSocket serverSocket){

        clients = new ArrayList<ClientThread>();
        while(true){
            try {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch(IOException e){
                System.out.println("Accept failed on: "+ portNumber);
            }
            }
    }
}