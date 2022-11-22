package com.netsix.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    public static void main(String[] args) {
        System.out.println("Welcome to NetSix Client!");
        Socket socket = null;
        BufferedReader inReader = null, replyReader = null;
        PrintWriter outWriter = null;

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 3333);
       
            replyReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (UnknownHostException e) {
           System.out.println("Connection failed: unknown hostname."); 
           System.exit(2);
        } catch (IOException e) {
           System.out.println("Connection failed: " + e); 
           System.exit(2);
		}

        inReader = new BufferedReader(new InputStreamReader(System.in));

        String cmd = "";

        System.out.println("Type your request: ");

        try {
            StringBuilder requestBuilder = new StringBuilder();
            System.out.print("Show name: ");
            requestBuilder.append(inReader.readLine());
            System.out.print("Episode: ");
            requestBuilder.append(",").append(inReader.readLine());

            String request = requestBuilder.toString();

            System.out.println("Sent request: " + request);
            outWriter.println(request);

            System.out.println("Server replied: " + replyReader.readLine());
        } catch (IOException e) {
            System.err.println("Unable to send request: " + e);
        }
    }
}