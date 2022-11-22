// MulticastClient.java

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastClient {
	
	
	private static InetAddress address;

    public static void main(String[] args) {

        // creazione della socket multicast
        MulticastSocket socket=null;
        try{
            socket = new MulticastSocket(MulticastServer.PORT);
            socket.setSoTimeout(20000); // 20 secondi
            System.out.println("\nMulticastClient: avviato");
            System.out.println("Creata la socket: "+ socket);
        }
        catch (IOException e) {
            System.out.println("Problemi nella creazione della socket: ");
            e.printStackTrace(); 
            System.exit(1);
        }  
 
        // adesione al gruppo associato all'indirizzo multicast 
        try{
            address = InetAddress.getByName("231.0.0.1");
            socket.joinGroup(address);
            System.out.println("Adesione al gruppo "+ address);
        }
        catch (IOException e) {
            System.out.println("Problemi nell'adesione al gruppo: ");
            e.printStackTrace(); 
            System.exit(2);
        }  
        
        DatagramPacket packet;
    
        // ricezione di alcune linee    
        for (int i = 0; i < 5; i++) {
            System.out.println("\nIn attesa di un datagramma... ");

            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            try{
                socket.receive(packet);	// ricezione datagram
            }
            catch (IOException e) {
                System.out.println("Problemi nella ricezione del datagramma: ");
                e.printStackTrace(); 
                continue;
                // anche se ci sono problemi riprende il ciclo di ricezioni    		
            }
            try{
                String linea=null;
                linea = DatagramUtility.getContent(packet);
                System.out.println("Linea ricevuta: " + linea);
            }
            catch (IOException e) {
                System.out.println("Problemi nella lettura del datagramma: ");
                e.printStackTrace(); 
                continue;
                // anche se ci sono problemi riprende il ciclo di ricezioni 
            }
        } //chiusura for

        // uscita dal gruppo e chiusura della socket
        System.out.println("\nUscita dal gruppo");
        try{
            socket.leaveGroup(address);
        }
        catch (IOException e) {
            System.out.println("Problemi nell'uscita dal gruppo: ");
            e.printStackTrace(); 
        }
        System.out.println("MulticastClient: termino...");
        socket.close();
    }
}
