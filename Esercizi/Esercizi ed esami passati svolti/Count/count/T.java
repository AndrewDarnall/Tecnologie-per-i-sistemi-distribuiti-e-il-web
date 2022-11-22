package count;
import java.io.*;
import java.util.*;
import count.*;

public class T extends Thread
{
	private int cnt;
	private X x;
	private char id;
	public T(X x, char id)
	{
		this.x=x;
		this.id=id;
		cnt=0;
		this.start();
	}
	public void run()
	{
		while(x.incrementa(id))
		{
            cnt++;
			//System.out.println(id + " -> cnt=" + cnt);
		}
		System.out.println(id + " -> cnt=" + cnt);
	}
}
