// MulticastServer.java

// Problemi su OSX con IPv6, non li ho risolti, 
// malgrado https://stackoverflow.com/questions/18747134/getting-cant-assign-requested-address-java-net-socketexception-using-ehcache

// Invece funziona su Linux (immagino con IPv4)

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServer {
	
    public static final int PORT = 4446; 
    // porta al di fuori del range 1-1024 !
    // dichiarata come statica perche' caratterizza il server

    public static final String FILE = "saggezza.txt";
    static BufferedReader in = null;
    static boolean moreLines = true; 		
		
    public static void main(String[] args) { 
        long WAIT = 1000;
        int count=0;
        MulticastSocket socket = null;
      
        System.out.println("MulticastServer: avviato");
      
        // creazione della socket datagram
        try {
            socket = new MulticastSocket(PORT);
            System.out.println("Socket: " + socket);
    	} 
        catch (IOException e) {
            System.out.println("Problemi nella creazione della socket: ");
            e.printStackTrace();
            System.exit(1);
        }
        // associazione di uno stream di input al file da cui estrarre le linee
        try {
            in = new BufferedReader(new FileReader(FILE));
            System.out.println("File "+FILE+" aperto");
        } 
        catch (FileNotFoundException e) {
            System.out.println("Problemi nell'apertura del file: ");
            e.printStackTrace();
            System.exit(2);
        }
        // creazione del gruppo associato all'indirizzo multicast
        InetAddress group=null;
        try{
            group = InetAddress.getByName("231.0.0.1");
            socket.joinGroup(group);
            System.out.println("Creazione del gruppo "+ group);
        }
        catch (IOException e) {
            System.out.println("Problemi nella creazione del gruppo: ");
            e.printStackTrace();
            System.exit(3);
        }

        while (moreLines) {
            count++;
            byte[] buf = new byte[256];
            // estrazione della linea               
            String linea = LineUtility.getNextLine(in);
            if (linea.equals("Nessuna linea disponibile"))
                moreLines = false;
            System.out.println("Estratta linea # "+count+" :   "+linea);

            // costruzione del datagramma contenente la linea 
            try {
                DatagramPacket packet = 
					DatagramUtility.buildPacket(group, PORT, linea);
                // invio della linea al gruppo
                socket.send(packet);
                System.out.println("Linea inviata");
            }
            catch (Exception e) {
                System.out.println("Problemi nell'invio del datagramma: ");
                e.printStackTrace();
                continue;
            }

            // attesa tra un invio e l'altro...
            try {
                Thread.sleep((long)(Math.random() * WAIT));
            } catch (InterruptedException e) { }
        } // while
        System.out.println("File terminato");
        System.out.println("MulticastServer: termino...");
        socket.close();
    }
}
