package pariDispari;
import java.io.*;
import java.util.*;
import pariDispari.*;

public class TD extends Thread
{
	private M m;
	public TD(M m)
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
			m.threadD();
		}
	}
}
