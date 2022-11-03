import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
    public static void main(String args[]) {
        try {
            boolean running=true;
            while (running) {
                Socket skt = new Socket("192.168.0.21", 5000);
                System.out.println("\n\nEnter a server request");
                Scanner read = new Scanner(System.in);
                String req = read.nextLine();
                if(req.equalsIgnoreCase("shut")){
                    running=false;
                }
                PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                System.out.println("Client: Sending '" + req + "'\n");
                out.println(req);
                //out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                // while (!in.ready()) {
                //}
                String rep = in.readLine(); // Read one line and output it
                System.out.print("Client: received '" + rep + "'\n");
            }
        } catch (Exception e) {
            System.out.print("Whoops! Client didn't work!\n" + e);
        }
    }
}