//  LineServer.java

import java.io.*;
import java.net.*;
import java.util.*;

public class LineServer {
    
    public static final int PORT = 4445;
    // porta al di fuori del range 1-1024

    public static void main(String[] args) {
        System.out.println("LineServer: avviato");
        DatagramSocket socket = null;
        try {
            // creazione della socket datagram
            socket = new DatagramSocket(PORT);
            // a differenza che in C, non serve pubblicare la socket
            System.out.println("Creata la socket: " + socket);
        }
        catch (SocketException e) {
            System.out.println("Problemi nella creazione della socket: ");
            e.printStackTrace();
            System.exit(1);
        }
        try{
            while (true) {
                System.out.println("\nIn attesa di richieste...");
                // ricezione del datagramma
                DatagramPacket packet=null;
                InetAddress mittAddr=null;
                int mittPort=0;
                try{
                    byte[] buf = new byte[256];
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    mittAddr = packet.getAddress();
                    mittPort = packet.getPort();
                    System.out.println("Ricevuta richiesta da "+mittAddr+", "+mittPort);
                }
                catch(IOException e){
                    System.err.println("Problemi nella ricezione del datagramma: " + e.getMessage()); 
                    e.printStackTrace();
                    continue;
                    // il server continua a fornire il servizio ricominciando dall'inizio del ciclo
                }  
                // lettura della richiesta 
                String nomeFile;
                int numLinea; 
                try{
                    String richiesta = DatagramUtility.getContent(packet);
                    StringTokenizer st = new StringTokenizer(richiesta);
                    nomeFile = st.nextToken();
                    numLinea = Integer.parseInt(st.nextToken());
                    System.out.println("Richiesta linea "+numLinea+" del file "+nomeFile);    
                }
                catch(Exception e){
                    System.err.println("Problemi nella lettura della richiesta: " + e.getMessage()); 
                    e.printStackTrace(); 
                    continue;
                    // il server continua a fornire il servizio ricominciando dall'inizio del ciclo 
                } 
                // preparazione della linea e invio della risposta      
                try{          
                    String linea =  LineUtility.getLine(nomeFile, numLinea);
                    byte[] data = new byte[256];
                    data = linea.getBytes();
                    packet = new DatagramPacket(data, data.length, mittAddr, mittPort);
                    socket.send(packet);
                    System.out.println("Risposta inviata a "+mittAddr+", "+mittPort);
                }
                catch(IOException e){
                    System.err.println("Problemi nell'invio della risposta: " + e.getMessage()); 
                    e.printStackTrace(); 
                    continue;
                    // il server continua a fornire il servizio ricominciando dall'inizio del ciclo 
                }   
            } // while
        }
        // qui catturo le eccezioni non catturate all'interno del while  
        // in seguito alle quali il server termina l'esecuzione   
        catch(Exception e){
            e.printStackTrace(); 
        }
        System.out.println("LineServer: termino...");
        socket.close();
    }
}
