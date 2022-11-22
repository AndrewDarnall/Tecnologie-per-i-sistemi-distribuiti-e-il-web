package TIMEServer;
import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;
import TIMEServer.*;

public class Server
{
	public static final int porta=3233;
    public static void stampaSocket(Socket socket)
    {
        System.out.println("Local address: " + socket.getLocalAddress());
        System.out.println("Local port: " + socket.getLocalPort());
        System.out.println("Remote address: " + socket.getInetAddress());
        System.out.println("Remote port: " + socket.getPort());
    }
	public static void main(String[] args)
	{
		try
        {
            ServerSocket local_sock=new ServerSocket(porta);
            LinkedList<InetAddress> l=new LinkedList<InetAddress>();
            while(true)
            {
                try
                {
                    System.out.println(">>Wait connections on port: " + porta + "…");
                    Socket remote_sock=local_sock.accept();
                    System.out.println("> Connection received!");
                    stampaSocket(remote_sock);
                    InputStreamReader input1=new InputStreamReader(remote_sock.getInputStream());
                    BufferedReader in=new BufferedReader(input1);
                    OutputStreamWriter output1=new OutputStreamWriter(remote_sock.getOutputStream());
                    PrintWriter out=new PrintWriter(output1,true);
                    String str=null;
                    if(l.contains(remote_sock.getInetAddress())==false)
                    {
                        if(in.readLine().equals("TIME"))
                        {
                            str=Long.toString(System.currentTimeMillis());
                            out.println(str);
                        }
                        else
                        {
                            str="N/A";
                            out.println(str);
                        }
                        l.clear();
                        l.add(remote_sock.getInetAddress());
                    }
                    else
                    {
                        out.println("BANNED");
                    }
                    remote_sock.close();
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
