import java.rmi.*;
import java.rmi.registry.*;

public class ClientRMI
{
    private static byte mode;
	
     // prende in input una stringa e verifica se essa rappresenta un intero
    public static boolean isInt(String str)
    {
        try
        {
            Integer.parseInt(str);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    public static void main(String[] args)
    {
        String utilizzo = "\nUtilizzo: java ClientRMI operazione parametro\nsaldo(int n): java ClientRMI saldo n\nversamento(int n, int cifra): java ClientRMI versamento n cifra\n";
	 
	// verifico la correttezza dei parametri
        if (args.length != 2 && args.length != 3)
        {
            System.out.println(utilizzo);
            return;
        }
        
        // verifico ancora la correttezza dei parametri e stabilisco la modalita' (1 = saldo, 2 = versamento)
        if (args[0].equals("saldo"))
        {
            if (args.length != 2 || !isInt(args[1]))
            {
                System.out.println(utilizzo);
                return;
            }
            mode = 1;
        } 
        else if (args[0].equals("versamento"))
        {
            if (args.length != 3 || !isInt(args[1]) || !isInt(args[2]))
            {
                System.out.println(utilizzo);
                return;
            }
            mode = 2;
        }
        else
        {
            System.out.println(utilizzo);
            return;
        }
        
	// eseguo l'operazione richiesta
        try
        {
            Registry reg = LocateRegistry.getRegistry("localhost");
            interfacciaEsame stub = (interfacciaEsame) reg.lookup("ContoCorrente");
            
            switch(mode)
            {
                case 1: // saldo
                    System.out.println("Il saldo "+args[1]+" vale: "+stub.saldo(Integer.parseInt(args[1])));
                    break;
                case 2: // versamento
                    stub.versamento(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    System.out.println("Versati: "+args[2]+" sul conto numero: "+args[1]);
                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
