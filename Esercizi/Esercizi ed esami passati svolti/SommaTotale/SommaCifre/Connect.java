package SommaCifre;
import java.io.*;
import java.util.*;
import java.net.*;
import SommaCifre.*;

public class Connect extends Thread
{
	private Socket remoteSock;
	private BufferedReader in;
	private PrintWriter out;
	public Connect(Socket remoteSock)
	{
		this.remoteSock=remoteSock;
		this.start(); //fa partire Run()
	}
	public boolean isInt(String cifra)
	{
		try
		{
			Integer.parseInt(cifra);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public void run()
	{
        try
        {
            String str;
            int i=0, somma=0;
            InputStreamReader input1=new InputStreamReader(remoteSock.getInputStream());
            OutputStreamWriter output1=new OutputStreamWriter(remoteSock.getOutputStream());
            in=new BufferedReader(input1);
            out=new PrintWriter(output1,true);
            str=in.readLine();
            //System.out.println("ciao");
            while(i<=str.length()-1)
            {
                if(isInt(str.charAt(i)+"")) //aggiungendo "" la prende come una stringa
                {
                    somma+=Integer.parseInt(str.charAt(i)+"");
                }
                i++;
            }
            synchronized(Server.sommaTotale)
            {
                Server.sommaTotale+=somma;
                //System.out.println("ciao");
            }
            out.println("Somma: " + somma + "\nSomma Totale: " + Server.sommaTotale);
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
