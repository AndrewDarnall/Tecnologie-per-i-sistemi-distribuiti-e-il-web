package attesaPariDispari;
import java.io.*;
import java.util.*;
import attesaPariDispari.*;

public class T3 extends Thread
{
	private M m;
	public T3(M m)
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
				Thread.sleep(2000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			m.thread3();
		}
	}
}
