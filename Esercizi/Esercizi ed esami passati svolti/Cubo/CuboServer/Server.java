package CuboServer;
import java.io.*;
import java.util.*;
import java.math.*;
import java.net.*;
import CuboServer.*;

public class Server
{
	public static boolean isInt(String str)
	{
		try
		{
            Integer.parseInt(str);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public static int cubo(int n)
	{
		return (int)Math.pow((double)n,3);
	}
	public static final int porta=3333;
	public static void stampaSocket(Socket remoteSock)
	{
		System.out.println("Local address: " + remoteSock.getLocalAddress());
		System.out.println("Local port" + remoteSock.getLocalPort());
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
                    Socket remoteSock=localSock.accept();
                    stampaSocket(remoteSock);
                    InputStreamReader input1=new InputStreamReader(remoteSock.getInputStream());
                    BufferedReader in=new BufferedReader(input1);
                    OutputStreamWriter output1=new OutputStreamWriter(remoteSock.getOutputStream());
                    PrintWriter out=new PrintWriter(output1,true);
                    String str=in.readLine();
                    if(isInt(str))
                    {
                        out.println(cubo(Integer.parseInt(str)));
                    }
                    else
                    {
                        out.println("Non covertibile");
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
        }
	}
}
