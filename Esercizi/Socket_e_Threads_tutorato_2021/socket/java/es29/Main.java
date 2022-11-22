import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
Implementare in Java o C un server che comunica tramite socket.

Il server attende sul port 33333 e memorizza in ordine tutte le stringhe che riceve (di fatto nell'implementazione ci si può limitare a memorizzare solo le ultime 100, se lo si desidera).

In risposta ad ogni nuova stringa proveniente da un client gli risponde con tutte le stringhe ricevute fino a quel momento (compresa l'ultima), con queste eccezioni:

    a partire dalla quinta stringa memorizzata, il server risponderà: LIMITE RAGGIUNTO;
    in risposta alla stringa RESET, il server azzera l'elenco delle stringhe memorizzate fino ad allora e risponde OK.

Per collaudare il server implementato, si può comunicare con esso (sotto linux) con il comando:
telnet localhost 33333
*/

public class Main {
    static ArrayList<String> receivedRequests = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello World!");

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(33333);
        } catch (IOException e) {
            System.err.println("Unable to bind socket: " + e);
            System.exit(1);
        }

        System.out.println("Socket bound");

        while (true) {
            try {
                System.out.println("Waiting for a client...");

                Socket clientSocket = serverSocket.accept();

                System.out.println("Received client connection");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());

                String request = reader.readLine();
                String response = handleRequest(request);

                writer.write(response);
                writer.flush();

                System.out.println("Closing connection...");

                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Unable to open connection: " + e);
                continue;
            }
        }
    }

    static String handleRequest(String request) {
        System.out.println("Received request: " + request);

        if (request.equals("RESET")) {
            System.out.println("Received reset request, emptying the requests list");
            receivedRequests.clear();
            return "LISTA RESETTATA\n";
        } else {
            receivedRequests.add(request);

            if (receivedRequests.size() >= 5) {
                System.out.println("List over limits");
                return "LIMITE RAGGIUNTO\n";
            } else {
                System.out.println("Sending requests list");

                StringBuilder responseBuilder = new StringBuilder();
                responseBuilder.append("Received requests: \n");

                // Riscrivere questo for utilizzata la stream programming
                for (String str : receivedRequests) {
                    responseBuilder.append(str).append("\n");
                }

                return responseBuilder.toString();
            }
        }
    }
}