import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 33333;
    private static boolean running = true;

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        while (running) {
            try {
                Socket client = server.accept();
                System.out.println("Connessione accettata dal client con addr = " + client.getInetAddress() + " port = "
                        + client.getPort());
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),
                        true);
                String input = in.readLine();
                System.out.println("Richiesta: " + input);
                out.println(input);
                client.close();
                System.out.println("Connessione conclusa\n-----------------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
