package pariDispari;
import java.io.*;
import java.util.*;
import pariDispari.*;

public class M
{
	private int m;
	private Random rand;
	public M()
	{
		m=0;
		rand=new Random();
	}
	public synchronized void setM(int m)
	{
		this.m=m;
	}
	public synchronized int getM()
	{
		return m;
	}
	public synchronized void threadP()
	{
		if(m%2==0)
		{
			System.out.println("TP -> " + m);
			do{
				m=rand.nextInt(9);
			}while(m%2==0);
			notifyAll();
		}
		else
		{
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
	public synchronized void threadD()
	{
		if(m%2!=0)
		{
			System.out.println("\t\t\tTD -> " + m);
			m=rand.nextInt(9);
			notifyAll();
		}
		else
		{
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
