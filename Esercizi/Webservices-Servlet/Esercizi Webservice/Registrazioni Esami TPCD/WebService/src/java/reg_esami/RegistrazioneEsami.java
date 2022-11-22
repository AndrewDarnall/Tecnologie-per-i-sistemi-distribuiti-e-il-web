package reg_esami;

import java.io.*;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "RegistrazioneEsami")
public class RegistrazioneEsami
{
    @WebMethod(operationName = "registrazione")
    public boolean registrazione(@WebParam(name = "nome") String nome, @WebParam(name = "cognome") String cognome, @WebParam(name = "matricola") String matricola, int voto, boolean lode)
                throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("db.txt", true));
        String l;
        
        if (lode)
            l = "Si";
        else
            l = "No";
        
        try
        {
            writer.append("Nome:"+nome+"; Cognome:"+cognome+"; Matricola:"+matricola+"; Voto:|"+voto+"|; Lode: "+l+".\n");
        }
        catch(IOException e)
        {
            return false;
        }
        finally
        {
            writer.close();
        }
        
        return true;
    }
    
    @WebMethod(operationName = "media")
    public String media() throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader("db.txt"));
        String media, tmp;
        float count = 0, sum = 0, voto;
        
        while ((tmp = reader.readLine()) != null)
        {
            count++;
            voto = myParser(tmp);
            
            if (voto == -1)
            {
                sum = -1;
                break;
            }
            
            sum += voto;
        }
        
        if (sum == -1)
            media = "Errore nel calcolo della media";
        else
            media = "Media: "+(sum/count);
        
        return media;
    }
    
    public int myParser(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '|')
            {
                int x = i+1;
                
                while (str.charAt(x) != '|')
                    x++;
                
                return Integer.parseInt(str.substring(i+1, x));
            }
        }
        return -1;
    }
}
