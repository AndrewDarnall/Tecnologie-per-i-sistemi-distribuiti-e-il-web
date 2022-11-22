/*  DatagramUtility.java

Classe usata per definire alcuni metodi utili nella gestione dei datagrammi, 
utilizzati sia dal client che dal server. 

*/

import java.io.*;
import java.net.*;
import java.util.*;

public class DatagramUtility {	
    // metodo per creare un pacchetto da una stringa
    static protected DatagramPacket buildPacket(InetAddress addr, 
							int port, String msg) throws IOException {
		// la stringa msg va convertita in byte array
        ByteArrayOutputStream boStream = new ByteArrayOutputStream();
        DataOutputStream doStream = new DataOutputStream(boStream);
        doStream.writeUTF(msg);
        byte[] data = boStream.toByteArray();  

		// pronto arg per costruttore DatagramPacket()
        return new DatagramPacket(data, data.length, addr, port);
        // per la creazione di un pacchetto da inviare e' necessario 
		// usare il costruttore con destinatario addr/port
    }
	
    // metodo per recuperare una stringa da un pacchetto 
    static protected String getContent(DatagramPacket dp) throws IOException {
        ByteArrayInputStream biStream = 
			new ByteArrayInputStream(dp.getData(), 0, dp.getLength());
        DataInputStream diStream = new DataInputStream(biStream);
        String msg = diStream.readUTF();
        return msg;
    }	
}
