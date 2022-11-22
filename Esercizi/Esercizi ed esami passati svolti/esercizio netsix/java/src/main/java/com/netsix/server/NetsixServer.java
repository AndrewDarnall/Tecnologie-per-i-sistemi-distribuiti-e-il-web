package com.netsix.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetsixServer {
    protected ServerSocket serverSocket;
    protected ShowList showList;

    public NetsixServer(int port, ShowList showList) {
        this.showList = showList;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("[NetfixServer] Unable to create server socket");
		}
    }

    public void waitForRequests() {
        Socket requestSocket = null;

        boolean listen = true;

        while(listen) {
            try {
                System.out.println("Listening for connections...");

                requestSocket = serverSocket.accept();
                
                System.out.println("Connection enstablished, waiting for a request... (" + requestSocket + ")");

                BufferedReader inReader = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
                PrintWriter outWriter = new PrintWriter(requestSocket.getOutputStream(), true);

                String message[] = inReader.readLine().split(",");
                String reply = "";

                if(message.length != 2) {
                    reply = "Unknown message format";
                } else {
                    int episode = -1;
                    
                    try {
                        episode = Integer.parseInt(message[1].replace(" ", ""));
                    
                        reply = showList.isAvailable(message[0], episode) ? "Disponibile" : "ComingSoon";
                    } catch(NumberFormatException e) {
                        reply = "Episode must be a number";
                    }
                }

                outWriter.println(reply);
                requestSocket.close();
            } catch (IOException e) {
                System.err.println("Unable to accept and/or to elaborate a request");
            }
        }
    }
}