import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ServerRMI implements interfacciaEsame
{
    private int conto[] = new int[20];
    
    @Override
    public int saldo(int n)
    {
        if (n < 0 || n > 19)
            return -1;

        return conto[n];
    }
    
    @Override
    public void versamento(int n, int cifra)
    {
        if (n < 0 || n > 19)
            return;
        conto[n] += cifra;
    }
    
    public static void main(String args[])
    {
        try
        {
            ServerRMI obj = new ServerRMI();
            interfacciaEsame stub = (interfacciaEsame) UnicastRemoteObject.exportObject(obj, 0);
            
            Registry reg = LocateRegistry.getRegistry("localhost");
            reg.bind("ContoCorrente", stub);
            
            System.out.println("Server ready (CTRL+C per terminare)");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}