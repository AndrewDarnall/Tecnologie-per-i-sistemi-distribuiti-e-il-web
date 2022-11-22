package SommaCifre;
import java.io.*;
import java.util.*;
import java.net.*;
import SommaCifre.*;

public class Server
{
	public static final int porta=33333;
	public static Integer sommaTotale=0; //per far funzionare il synchronized sommaTotale deve essere di tipo Integer anzichè int
	public static void stampaSocket(Socket remoteSock)
	{
		System.out.println("Local address: " + remoteSock.getLocalAddress());
		System.out.println("Local port: " + remoteSock.getLocalPort());
		System.out.println("Remote address: " + remoteSock.getInetAddress());
		System.out.println("Remote port: " + remoteSock.getPort());
	}
	public static void main(String[] args)
	{
		try
        {
            ServerSocket localSock=new ServerSocket(porta);
            while(true)
            {
                try
                {
                    System.out.println(">>Wait for connection on port " + porta + "…");
                    Socket remoteSock=localSock.accept(); //accept() è bloccante
                    stampaSocket(remoteSock);
                    Connect net=new Connect(remoteSock);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
}
