//  LineClient.java

import java.io.*;
import java.net.*;
import java.util.*;

public class LineClient {

    public static final int PORT = 4445;
    // porta al di fuori del range 1-1024

    public static void main(String[] args) {

        DatagramSocket socket=null;

        // creazione della socket datagram con un timeout di 3
        try{
            socket = new DatagramSocket();
            socket.setSoTimeout(3000);
            System.out.println("\nLineClient: avviato");
            System.out.println("Creata la socket: "+ socket);
        }
        catch (SocketException e) {
            System.out.println("Problemi nella creazione della socket: ");
            e.printStackTrace(); 
            System.out.println("LineClient: interrompo...");
            System.exit(1);
        }

        InetAddress addr = null;

        try{
            if (args.length == 0) addr = InetAddress.getByName(null);
            else addr = InetAddress.getByName(args[0]);
        }
        catch(UnknownHostException e){
            System.out.println("Problemi nel determinare l'indirizzo del server: ");
            e.printStackTrace(); 
            System.out.println("LineClient: interrompo...");
            System.exit(2);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String richiesta=null;
        System.out.print("\n^D(Unix)/^Z(Win) per uscire, invio per continuare: ");

        try{
            while (stdIn.readLine()!=null){
                try{
                    System.out.print("Nome del file (con estensione)? ");
                    String nomeFile = stdIn.readLine();
                    System.out.print("Numero della linea? ");
                    String numLinea = stdIn.readLine(); // eliminato Integer.parseInt();
                    richiesta = nomeFile+" "+numLinea;
                }
                catch (Exception e) {
                    System.out.println("Problemi nell'interazione da console: ");
                    e.printStackTrace(); 
                    System.out.print("\n^D(Unix)/^Z(Win) per uscire, invio per continuare: ");
                    continue;
                    // il client continua l'esecuzione riprendendo dall'inizio del ciclo
                }

                // creazione e invio del pacchetto
                try{
                    DatagramPacket packetOUT = 
						DatagramUtility.buildPacket(addr, PORT, richiesta);
                    socket.send(packetOUT);
                    System.out.println("Richiesta inviata a " + addr + ", " + PORT);
                }
                catch (IOException e) {
                    System.out.println("Problemi nell'invio della richiesta: ");
                    e.printStackTrace(); 
                    System.out.print("\n^D(Unix)/^Z(Win) per uscire, invio per continuare: ");
                    continue;
        	}

                // ricezione e stampa della risposta
                DatagramPacket packetIN=null;
                try{
                    byte[] buf = new byte[256];
                    packetIN = new DatagramPacket(buf, buf.length);
                    socket.receive(packetIN);
                    // bloccante solo per i millisecondi indicati, dopo solleva una eccezione
                }
                catch (IOException e) {
                    System.out.println("Problemi nella ricezione del datagramma: ");
                    e.printStackTrace();
                    System.out.print("\n^D(Unix)/^Z(Win) per uscire, invio per continuare: ");
                    continue;
                    // il client continua l'esecuzione riprendendo dall'inizio del ciclo
                }

                String risposta = new String(packetIN.getData());
                System.out.println("Risposta: " + risposta);

                // tutto ok, pronto per nuova richiesta
                System.out.print("\n^D(Unix)/^Z(Win) per uscire, invio per continuare: ");
            }	// while
        }
        // qui catturo le eccezioni non catturate all'interno del while
        // in seguito alle quali il client termina l'esecuzione
        catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("LineClient: termino...");
        socket.close();
    }
}
