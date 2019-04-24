package edu.cooper.ece465;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * @author tonykuo
 *
 */
public class KKMultiServer {
    
    public static void main(String[] args) throws IOException {
        
        /*
        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }
        */

        // int portNumber = Integer.parseInt(args[0]);
        int portNumber = 3333;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                System.out.println("New Connection");
                new ImageThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
    
}
