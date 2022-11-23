/**
 * - Server_B
 * Soluzione proposta -> Andrew R. Darnall
 * 
 */
import java.io.*;
import java.net.*;
import java.nio.Buffer;

public class Server_B {

    private static final int PORT = 3333;

    public static void main(String[] args) {

        ServerSocket server = null;

        try {
            server = new ServerSocket(PORT);
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Server_A started, listening on PORT: " + PORT);
        Socket s = null;
        BufferedReader in = null;
        PrintWriter out = null;
        

        try{

            s = server.accept();

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            String msg = in.readLine();
            System.out.println("From client:\t" + msg);

            out.print("OK\r\n");


        } catch(IOException e) {
            System.err.println("Error accepting the connection!");
            e.printStackTrace();
        }

        try {
            out.close();
            in.close();
            s.close();
            server.close();
        } catch (IOException e) {
            System.err.println("Error closing the socket");
            e.printStackTrace();
        }

    }    

}