package count;
import java.io.*;
import java.util.*;
import count.*;

public class X
{
	private int x;
	public X()
	{
		x=0;
	}
	public synchronized void setX(int x)
	{
		this.x=x;
	}
	public synchronized int getX()
	{
		return x;
	}
	public synchronized boolean incrementa(char id)
	{
		if(x>=301)
		{
			return false;
		}
		x++;
		System.out.println(id + " -> x=" + x);
		return true;
	}
}
