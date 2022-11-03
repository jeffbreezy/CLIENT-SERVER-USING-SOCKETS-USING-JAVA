import java.io.*;
import java.net.*;
public class Server {
    public static float getTax(float basic) {
        float tax = 0, taxable = 0;
        if (basic > 0) {
            taxable = (basic >= 10000) ? 10000 : basic;
            tax += taxable * 0.12;
            System.out.println(tax);
        }
        if (basic >= 10001) {
            taxable = (basic >= 20000) ? 10000 : basic - 10000;
            tax += taxable * 0.10;
            System.out.println(tax);
        }
        if (basic >= 20001) {
            taxable = (basic >= 40000) ? 20000 : basic - 20000;
            tax += taxable * 0.08;
            System.out.println(tax);
        }
        if (basic >= 40001) {
            taxable = (basic >= 60000) ? 20000 : basic - 40000;
            tax += taxable * 0.06;
            System.out.println(tax + " taxable=" + taxable);
        }
        if (basic >= 60001) {
            taxable = (basic >= 80000) ? 20000 : basic - 60000;
            tax += taxable * 0.04;
            System.out.println(tax);
        }
        if (basic >= 80001) {
            taxable = basic - 80000;
            tax += taxable * 0.02;
            System.out.println(tax);
        }
        return tax;
    }

    public static void main(String args[]) {
        try {
            ServerSocket srvr = new ServerSocket(5000);
            boolean running = true;
            while (running) {
                System.out.println("Server waiting for incoming connections");
                Socket skt = srvr.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                String req = in.readLine();
                System.out.println("Server: Received string '" + req + "'");
                //System.out.print("Server has connected to " + skt.getInetAddress() + "!\n");
                String rep = "";
                if (req.startsWith("upper")) {
                    String input = req.substring(req.indexOf(":") + 1);
                    rep = input.toUpperCase();
                } else if (req.startsWith("count")) {
                    String input = req.substring(req.indexOf(":") + 1);
                    rep = input.length() + " CHARACTERS";
                } else if (req.startsWith("tax")) {
                    String input = req.substring(req.indexOf(":") + 1);
                    try {
                        float basic = Float.parseFloat(input);
                        float tax = getTax(basic);
                        rep = "tax for " + basic + " is " + tax;
                    } catch (Exception e) {
                        rep = "Invalid input: '" + input + "'";
                    }
                } else if (req.equalsIgnoreCase("shut")) {
                    running = false;
                    rep = "Server shutting down";
                } else {
                    rep = "Request processed";
                }
                PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
                System.out.println("Server: Sending string '" + rep + "'\n");
                out.println(rep);
            }
            System.out.println("Server has shut down");
        } catch (Exception e) {
            System.out.print("Whoops! Server didn't work!\n" + e);
        }
    }
}