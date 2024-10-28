package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends ChatServer implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            /*
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
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //while the socket is still alive
            while(!socket.isClosed()) {
                String input = in.readLine();
                if(input != null){
                    for(ClientThread client : clients) {
                        client.getWriter().write(input);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PrintWriter getWriter() {
        return out;
    }
}
