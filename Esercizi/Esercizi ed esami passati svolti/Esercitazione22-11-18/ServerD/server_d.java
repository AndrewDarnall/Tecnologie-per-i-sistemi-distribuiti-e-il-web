package ServerD;
import java.io.*;
import java.util.*;
import java.net.*;
import ServerD.*;

public class server_d
{
	public static final int port=3333;
    public static int contaNumeri(String s)
    {
        int x=0;
        for(int i=0; i<=s.length()-1; i++)
        {
            if((s.charAt(i)=='a') || (s.charAt(i)=='e') || (s.charAt(i)=='i') || (s.charAt(i)=='o') || (s.charAt(i)=='u') || (s.charAt(i)=='A') || (s.charAt(i)=='E') || (s.charAt(i)=='I') || (s.charAt(i)=='O') || (s.charAt(i)=='U'))
            {
                x++;
            }
        }
        return x;
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


