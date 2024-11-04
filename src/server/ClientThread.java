package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private ChatServer server;

    public ClientThread(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }
    private PrintWriter getWriter(){
        return clientOut;
    }

    @Override
    public void run() {
        try {
            // setup

            /* OLD NOTES FROM FIRST ITERATION
            socket.getOutputStream() retrieves the raw OutputStream associated with this
            specific socket. This OutputStream is a low-level, byte-based stream that allows
            data to be sent from the server to the client. However, handling raw bytes is
            inconvenient for text-based communication, so we wrap this OutputStream with a
            PrintWriter which provides convenient methods for sending formatted text data.
            The second parameter, true, enables auto-flushing for the PrintWriter. When
            auto-flush is enabled, any call to println(), printf(), or format() will
            automatically flush the stream, sending data immediately to the client. Why
            Auto-Flush Matters: In a chat server, real-time communication is essential.
            By setting autoFlush to true, the server ensures that messages are sent to the
            client without unnecessary delays.
            */

           this.clientOut = new PrintWriter(socket.getOutputStream(), false);
           Scanner in = new Scanner(socket.getInputStream());

           //start communicating
            while(!socket.isClosed()) {
               if(in.hasNextLine()) {
                   String input = in.nextLine();
                   // NOTE: if you want to check server can read input, uncomment next line and check
                   // server file console
                   // System.out.println(input);
                   for(ClientThread thatClient : server.getClients()) {
                       PrintWriter thatClientOut = thatClient.getWriter();
                       if(thatClientOut != null) {
                           thatClientOut.write(input + "\r\n");
                       }
                   }
               }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}