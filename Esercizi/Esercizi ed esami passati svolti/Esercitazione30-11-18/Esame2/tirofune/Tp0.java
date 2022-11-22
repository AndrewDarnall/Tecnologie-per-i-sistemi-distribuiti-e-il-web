package tirofune;
import java.io.*;
import java.util.*;
import tirofune.*;

public class Tp0 extends Thread
{
	private Random rand;
	private Posizione posizione;
	public Tp0(Posizione posizione)
	{
		this.posizione=posizione;
		rand=new Random();
		this.start();
	}
	public void run()
	{
        int recupero=0;
        int forza=0;
		while(!posizione.winner())
		{
            recupero=rand.nextInt(3);
            System.out.println("P1 -> recupero=" + recupero);
            forza=rand.nextInt(5);
            System.out.println("P1 -> forza=" + forza);
            try
            {
                Thread.sleep(recupero*1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
                System.exit(1);
            }
			posizione.p1(forza);
		}
	}
}
