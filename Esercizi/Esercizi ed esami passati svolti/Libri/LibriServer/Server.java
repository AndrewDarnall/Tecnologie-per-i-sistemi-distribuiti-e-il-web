package LibriServer;
import java.io.*;
import java.util.*;
import java.net.*;
import LibriServer.*;

public class Server
{
	public static void stampaSocket(Socket remoteSock)
	{
		System.out.println("Local address: " + remoteSock.getLocalAddress());
		System.out.println("Local port: " + remoteSock.getLocalPort());
		System.out.println("Remote address: " + remoteSock.getInetAddress());
		System.out.println("Remote port: " + remoteSock.getPort());
	}
	public static void main(String[] args)
	{
        int porta=8080;
        if(args.length==1)
        {
            porta=Integer.parseInt(args[0]);
        }
		try
        {
            Libro libro=new Libro();
            ServerSocket localSock=new ServerSocket(porta);
            while(true)
            {
                try
                {
                    System.out.println(">>Wait for connection on port " + porta + "…");
                    Socket remoteSock=localSock.accept();
                    stampaSocket(remoteSock);
                    InputStreamReader input1=new InputStreamReader(remoteSock.getInputStream());
                    BufferedReader in=new BufferedReader(input1);
                    OutputStreamWriter output1=new OutputStreamWriter(remoteSock.getOutputStream());
                    PrintWriter out=new PrintWriter(output1,true);
                    String str=in.readLine();
                    if(libro.isAvailable(str)==1)
                    {
                        out.println("Disponibile");
                    }
                    else if(libro.isAvailable(str)==0)
                    {
                        out.println("In prestito");
                    }
                    else if(libro.isAvailable(str)==-1)
                    {
                        out.println("Inesistente");
                    }
                    in.close();
                    out.close();
                    remoteSock.close();
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
            System.exit(1);
        }
	}
}
