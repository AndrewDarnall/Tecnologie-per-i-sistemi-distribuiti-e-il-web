package Client;
import java.io.*;
import java.util.*;
import java.net.*;
import Client.*;

public class client
{
	public static void main(String[] args)
    {
        InetAddress ip=null;
        int port=0;
        if(args.length>=1)
        {
            try
            {
                ip=InetAddress.getByName(args[0]);
            }
            catch(UnknownHostException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("usage: <ip address> <port>");
            System.exit(1);
        }
        if(args.length>=2)
        {
            port=Integer.parseInt(args[1]);
        }
        else
        {
            System.err.println("usage: <ip address> <port>");
            System.exit(1);
        }
        try
        {
            Socket remoteSock=new Socket(ip,port);
            InputStreamReader input1=new InputStreamReader(remoteSock.getInputStream());
            BufferedReader in=new BufferedReader(input1);
            OutputStreamWriter output1=new OutputStreamWriter(remoteSock.getOutputStream());
            PrintWriter out=new PrintWriter(output1,true);
            BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
            out.println(input.readLine());
            System.out.println(in.readLine());
            input.close();
            in.close();
            out.close();
            remoteSock.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
