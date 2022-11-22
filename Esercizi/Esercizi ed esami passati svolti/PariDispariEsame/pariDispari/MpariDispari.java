package pariDispari;
import java.io.*;
import java.util.*;
import pariDispari.*;

public class MpariDispari
{
	public static void main(String[] args)
	{
		M m=new M();
		TP tp=new TP(m);
		TD td=new TD(m);
		try
		{
			tp.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		try
		{
			td.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
}
