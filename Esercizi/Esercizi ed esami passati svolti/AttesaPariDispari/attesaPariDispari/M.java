package attesaPariDispari;
import java.io.*;
import java.util.*;
import attesaPariDispari.*;

public class M
{
	private int m;
	private Random rand;
	public M()
	{
		rand=new Random();
		m=0;
	}
	public synchronized void setM(int m)
	{
		this.m=m;
	}
	public synchronized int getM()
	{
		return m;
	}
	public synchronized void thread1()
	{
		int mtemp=m;
		System.out.println("T1 -> " + m);
		m=rand.nextInt(10);
		if(mtemp%2!=0)
		{
			System.out.println("T1 si mette in attesa");
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
	public synchronized void thread2()
	{
		int mtemp=m;
		System.out.println("\t\t\tT2 -> " + m);
		m=rand.nextInt(10);
		if(mtemp%2==0)
		{
			System.out.println("\t\t\tT2 si mette in attesa");
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
	public synchronized void thread3()
	{
		notifyAll();
	}
}
