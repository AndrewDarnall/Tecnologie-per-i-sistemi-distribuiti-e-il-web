package tirofune;
import java.io.*;
import java.util.*;
import tirofune.*;

public class TiroFune
{
	public static void main(String[] args)
	{
        Posizione posizione=new Posizione();
        Tp0 tp0=new Tp0(posizione);
        Tp1 tp1=new Tp1(posizione);
        try
        {
            tp0.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        try
        {
            tp1.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("P1 -> NumVittorie " + posizione.getVittorieTp0());
        System.out.println("P2 -> NumVittorie " + posizione.getVittorieTp1());
        if(posizione.getVittorieTp0()==posizione.getVittorieTp1())
        {
            System.out.println("Pareggio!");
        }
        if(posizione.getVittorieTp0()>posizione.getVittorieTp1())
        {
            System.out.println("Il P1 Vince!");
        }
        else
        {
            System.out.println("Il P2 Vince!");
        }
	}
}
