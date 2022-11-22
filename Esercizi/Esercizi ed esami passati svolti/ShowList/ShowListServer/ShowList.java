package ShowListServer;
import java.io.*;
import java.util.*;
import java.net.*;
import ShowListServer.*;

public class ShowList
{
    private HashMap<String,Integer> NetSix;
	public ShowList()
    {
        NetSix=new HashMap<String, Integer>();
        NetSix.put("House of Karts", 5);
        NetSix.put("Strange Things", 10);
        NetSix.put("Baking Brad", 12);
        NetSix.put("Marcos", 7);
        NetSix.put("Black Mails", 15);
        NetSix.put("Rick e Morto", 4);
    }
    public boolean isAvailable(String nome_serie, int n)
    {
        if(NetSix.get(nome_serie)!=null)
        {
            if((n>=1) && (n<=NetSix.get(nome_serie)))
            {
                return true;
            }
        }
        return false;
    }
}
