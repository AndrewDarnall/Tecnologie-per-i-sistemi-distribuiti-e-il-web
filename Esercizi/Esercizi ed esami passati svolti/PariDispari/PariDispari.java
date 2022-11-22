package PariDispari;
import java.io.*;
import java.util.*;
import PariDispari.*;

public class PariDispari extends Thread
{
	public static Integer x=0;
	public static void A()
	{
		while(true)
		{
			synchronized(x)
			{
				if((x<=100) && (x%2==0))
                {
                    System.out.println("A -> " + x);
                    x++;
                }
                //interrupt();
			}
            if(x>=100)
            {
                System.exit(1);
            }
		}
	}
	public static void B()
	{
		while(true)
		{
			synchronized(x)
			{
				if((x<=100) && (x%2!=0))
                {
                    System.out.println("B -> " + x);
                    x++;
                }
                //interrupt;
			}
            if(x>=100)
            {
                System.exit(1);
            }
		}
	}
    
	public static void main(String[] args)
	{
        new Thread(PariDispari::A).start(); //Per eseguire Thread statici (nella stessa classe del main)
        new Thread(PariDispari::B).start();
	}
}
