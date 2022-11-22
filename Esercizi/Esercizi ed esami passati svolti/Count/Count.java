package Count;
import java.io.*;
import java.util.*;
import Count.*;

public class Count extends Thread
{
	public static Random rand=new Random();
	public static Integer x=0;
	public static void A()
	{
		int cnt=0;
		while(true)
		{
            try
            {
                sleep(Long.valueOf(rand.nextInt(3000)+100));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
			if(x>=301)
			{
                System.out.println("A -> cnt=" + cnt);
                return;
			}
			synchronized(x)
			{
				x++;
				cnt++;
				System.out.println("A ->      " + x + "    " + cnt);
			}
		}
	}
	public static void B()
	{
		int cnt=0;
		while(true)
		{
            try
            {
                sleep(Long.valueOf(rand.nextInt(3000)+100));
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
			if(x>=301)
			{
                System.out.println("B -> cnt=" + cnt);
                return;
			}
			synchronized(x)
			{
				x++;
				cnt++;
				System.out.println("B ->      " + x + "    " + cnt);
			}
		}
	}
	public static void main(String[] args)
	{
        System.out.println("Thread    x    cnt");
        new Thread(Count::A).start();
        new Thread(Count::B).start();
	}
}
