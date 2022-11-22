import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Regole per la comunicazione:
 * 1. Il client invia un messaggio 'Hello' al server 
 * 2. Il server risponde con una conferma di ricezione
 * 3. Il client invia il messaggio passato da linea di comando
 * 4. Il server risponde con una conferma di ricezione
 * 5. La connessione termine
 */

public class DatagramClient {
    public static int SERVER_PORT = 9000;
    public static int BUFFER_SIZE = 256;

    public static void main(String[] args) {
        System.out.println("Hello World! I am the client. args: " + Arrays.toString(args));

        DatagramSocket socket = createSocket();

        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket receivedPacket = new DatagramPacket(buffer, BUFFER_SIZE);

        System.out.println("Sending 'Hello' message...");

        // 1. Il client invia un messaggio 'Hello' al server 
        DatagramPacket requestPacket = buildMessage("Hello");
        System.out.println("Sending hello message");
        sendRequest(socket, requestPacket); 

        // 2. Il server risponde con una conferma di ricezione
        waitForReceipt(socket, receivedPacket, "HELLO");

        // 3. Il client invia il messaggio passato da linea di comando
        String content = String.join(" ", args);
        System.out.println(String.format("Sending content message '%s'", content));
        sendRequest(socket, buildMessage(content));

        // 4. Il server risponde con una conferma di ricezione
        waitForReceipt(socket, receivedPacket, "CONTENT");

        System.out.println("Done");
    }

    private static void waitForReceipt(DatagramSocket socket, DatagramPacket receivedPacket, String messageType) {
        try {
            socket.receive(receivedPacket);
            String receiptMessage = extractMessage(receivedPacket);
            
            if(!receiptMessage.equals("OK_" + messageType)) {
                System.err.println(String.format(
                    "Communication error: Unexpected receipt message '%s'", receiptMessage));
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.print("Unable to receive response: " + e);
            System.exit(1);
        }
    }

    private static String extractMessage(DatagramPacket receivedPacket) {
        String message = new String(Arrays.copyOfRange(
            receivedPacket.getData(), 0, receivedPacket.getLength()));

        System.out.println(String.format("Received request from %s:%d. The message is: '%s'", 
            receivedPacket.getAddress(), 
            receivedPacket.getPort(),
            message));

        return message;
    }

    private static void sendRequest(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.err.print("Unable to send request: " + e);
            System.exit(1);
        }
    }

    private static DatagramPacket buildMessage(String message) {
        byte[] messageData = message.getBytes();

        try {
            return new DatagramPacket(
                messageData, messageData.length, InetAddress.getLocalHost(), SERVER_PORT);
        } catch (UnknownHostException e) {
            System.err.print("Unable to create hello message: " + e);
            System.exit(1);
        }

        return null;
    }

    private static DatagramSocket createSocket() {
        try {
            return new DatagramSocket();
        } catch (SocketException e) {
            System.err.println("Unable to create socket: " + e);
            System.exit(1);
        }

        return null;
    }
}
