package tirofune;
import java.io.*;
import java.util.*;
import tirofune.*;

public class Posizione
{
	private int posizione;
	private int vittorieTp0;
	private int vittorieTp1;
	public Posizione()
	{
		posizione=0;
		vittorieTp0=0;
		vittorieTp1=0;
	}
	public synchronized void setPosizione(int posizione)
	{
		this.posizione=posizione;
	}
	public synchronized int getPosizione()
	{
		return posizione;
	}
	public synchronized void setVittorieTp0(int vittorieTp0)
	{
		this.vittorieTp0=vittorieTp0;
	}
	public synchronized int getVittorieTp0()
	{
		return vittorieTp0;
	}
	public synchronized void setVittorieTp1(int vittorieTp1)
	{
		this.vittorieTp1=vittorieTp1;
	}
	public synchronized int getVittorieTp1()
	{
		return vittorieTp1;
	}
    public synchronized boolean winner()
    {
        if((vittorieTp0>=10) || (vittorieTp1>=10))
        {
            return true;
        }
        return false;
    }
    public synchronized void p1(int forza)
    {
        if(posizione>=10)
        {
            vittorieTp1++;
            posizione=0;
            notifyAll();
        }
        else
        {
            posizione-=forza;
            if(posizione<=-10)
            {
                System.out.println("P1 vince!");
                if(winner())
                {
                    return;
                }
                try
                {
                    wait();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
    public synchronized void p2(int forza)
    {
        if(posizione<=-10)
        {
            vittorieTp0++;
            posizione=0;
            notifyAll();
        }
        else
        {
            posizione+=forza;
            if(posizione>=10)
            {
                System.out.println("\t\t\tP2 vince!");
                if(winner())
                {
                    return;
                }
                try
                {
                    wait();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
