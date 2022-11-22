package ServerC;
import java.io.*;
import java.util.*;
import java.net.*;
import ServerC.*;

public class server_c
{
	public static final int port=3333;
    public static int contaNumeri(String s)
    {
        return 1;
    }
	public static void stampaSocket(Socket remoteSock)
	{
		System.out.println("local address: " + remoteSock.getLocalAddress());
		System.out.println("local port: " + remoteSock.getLocalPort());
		System.out.println("remote address: " + remoteSock.getInetAddress());
		System.out.println("remote port: " + remoteSock.getPort());
	}
	public static void main(String[] args)
	{
		try
        {
            ServerSocket localSock=new ServerSocket(port);
            while(true)
            {
                try
                {
                    System.out.println(">>Wait for connection on port " + port + "…");
                    Socket remoteSock=localSock.accept();
                    System.out.println(">Connection accepted!");
                    stampaSocket(remoteSock);
                    InputStreamReader input1=new InputStreamReader(remoteSock.getInputStream());
                    BufferedReader in=new BufferedReader(input1);
                    OutputStreamWriter output1=new OutputStreamWriter(remoteSock.getOutputStream());
                    PrintWriter out=new PrintWriter(output1,true);
                    String str=in.readLine();
                    System.out.println(str);
                    out.println(contaNumeri(str));
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
        }
	}
}

