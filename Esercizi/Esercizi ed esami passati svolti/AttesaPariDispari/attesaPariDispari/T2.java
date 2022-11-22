package attesaPariDispari;
import java.io.*;
import java.util.*;
import attesaPariDispari.*;

public class T2 extends Thread
{
	private M m;
	public T2(M m)
	{
		this.m=m;
		this.start();
	}
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(300);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			m.thread2();
		}
	}
}
