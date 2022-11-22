package LibriServer;
import java.io.*;
import java.util.*;
import java.net.*;
import LibriServer.*;

public class Libro
{
    private HashMap<String,Boolean> libro;
	public Libro()
	{
		libro=new HashMap<String,Boolean>();
		libro.put("Harry Potter",true);
		libro.put("Dragon ball",true);
		libro.put("My Hero Accademia",false);
		libro.put("Winx",true);
		libro.put("Miu Miu",false);
		libro.put("Inferno",true);
		libro.put("I fiori del male",true);
		libro.put("La divina commedia",true);
		libro.put("50 sfumature di grigio",false);
		libro.put("Il codice da Vinci",true);
	}
	public int isAvailable(String Nome)
	{
		if(libro.containsKey(Nome))
        {
            if(libro.get(Nome))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        return -1;
	}
}
