package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String host = "localhost";
    private static final int portNumber = 4444;

    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner userInputScanner;

    public static void main(String[] args){
        String readName = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input username:");
        while(readName == null || readName.trim().equals("")){
            //null, empty, whitespace(s) not allowed
            readName = scan.nextLine();
            if(readName == null || readName.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }
    }
}