package edu.cooper.ece465;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

public class HistogramClient {

    public static void main(String[] args) throws IOException {
        
        /*
        if (args.length != 3) {
            System.err.println("Usage: java EchoClient <host name> <port number> <Image File>");
            System.exit(1);
        }
        */

        String hostName = "127.0.0.1";
        // String hostName = args[0];

        int portNumber = 3333;
        // int portNumber = Integer.parseInt(args[1]);
        
        String imgPath = "img/demo.jpg";
        String newImgPath = "img/new_demo.jpg";
        
        try {
            
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("Connection Established");
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            BufferedImage bimg = ImageIO.read(new File(imgPath));
            // BufferedImage bimg = ImageIO.read(new File(args[2]));
            
            System.out.println("Image read in");
            
            // ImageIO.write(bimg, "JPG", out);

            ByteArrayOutputStream byteArrayO = new ByteArrayOutputStream();
            ImageIO.write(bimg, "JPG", byteArrayO);
            byte[] byteArray = byteArrayO.toByteArray();
            out.writeInt(byteArray.length);
            out.write(byteArray);
            // out.flush();
            System.out.println("Image sent over socket");
            
            BufferedImage result = ImageIO.read(in);
            System.out.println("Result received");
            
            ImageIO.write(result, "JPG", new File(newImgPath));
            // ImageIO.write(result, "JPG", new File("new-" + args[2]));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
