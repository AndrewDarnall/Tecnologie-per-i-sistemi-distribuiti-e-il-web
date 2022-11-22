package ShowListServer;
import java.io.*;
import java.util.*;
import java.net.*;
import ShowListServer.*;

public class Server
{
	public static final int porta=3333;
    public static void printSocketInfo(Socket socket)
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
            while(true)
            {
                try
                {
                    System.out.println(">>Wait for connections on port" + porta + "…");
                    
                    Socket remote_sock=local_sock.accept();
                    System.out.println("> Connection received!");
                    printSocketInfo(remote_sock);
                    
                    InputStreamReader input1=new InputStreamReader(remote_sock.getInputStream());
                    BufferedReader in=new BufferedReader(input1);
                    
                    OutputStreamWriter output1=new OutputStreamWriter(remote_sock.getOutputStream());
                    PrintWriter out=new PrintWriter(output1,true);
                    
                    ShowList serie=new ShowList();
                    String str=in.readLine();
                    String vett[]=null;
                    vett=str.split(",");
                    if(serie.isAvailable(vett[0],Integer.parseInt(vett[1])))
                    {
                        out.println("Disponibile");
                    }
                    else
                    {
                        out.println("Coming Soon");
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
