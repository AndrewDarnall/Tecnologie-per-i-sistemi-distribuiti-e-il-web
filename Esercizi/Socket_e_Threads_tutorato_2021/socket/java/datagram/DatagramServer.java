import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class DatagramServer {
    public static int PORT = 9000;
    public static int BUFFER_SIZE = 256;

    public static void main(String[] args) {
        System.out.println("Hello World! I am the server.");

        DatagramSocket socket = createSocket();
        listenForMessages(socket);
    }

    private static void listenForMessages(DatagramSocket socket) {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket receivedPacket = new DatagramPacket(buffer, BUFFER_SIZE);

        while(true) {
            try {
                System.out.println("Listening for requests...");

                // 1. Il client invia un messaggio 'Hello' al server 
                String helloMessage = listenForMessage(socket, receivedPacket);
                if(!isMessageEqualsTo(helloMessage, "Hello")) {
                    continue;
                }

                // 2. Il server risponde con una conferma di ricezione
                sendReceipt(socket, receivedPacket, "HELLO");

                // 3. Il client invia il messaggio passato da linea di comando
                String contentMessage = listenForMessage(socket, receivedPacket);
                System.out.println(String.format("Received content message: '%s'", contentMessage));

                // 4. Il server risponde con una conferma di ricezione
                sendReceipt(socket, receivedPacket, "CONTENT");
            } catch (IOException e) {
                System.err.println("Unable to listen for messages: " + e);
                System.exit(2);
            }
        }
    }

    private static String listenForMessage(DatagramSocket socket, DatagramPacket receivedPacket) throws IOException {
        socket.receive(receivedPacket);
        return extractMessage(receivedPacket);
    }

    private static void sendReceipt(DatagramSocket socket, DatagramPacket receivedPacket, String messageType) throws IOException {
        DatagramPacket receiptMessage = buildResponseMessage("OK_" + messageType, receivedPacket);
        socket.send(receiptMessage);
    }

    private static boolean isMessageEqualsTo(String message, String expectedContent) {
        if(!message.equals(expectedContent)) {
            System.err.println(String.format(
                "Communication error: Unexpected message '%s'", message));
            return false;
        }

        return true;
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

    private static DatagramPacket buildResponseMessage(String message, DatagramPacket receivedPacket) {
        byte[] messageData = message.getBytes();

        return new DatagramPacket(
            messageData, messageData.length, receivedPacket.getAddress(), receivedPacket.getPort());
    }

    private static DatagramSocket createSocket() {
        try {
            return new DatagramSocket(PORT);
        } catch (SocketException e) {
            System.err.println("Unable to create socket: " + e);
            System.exit(1);
        }

        return null;
    }
}
