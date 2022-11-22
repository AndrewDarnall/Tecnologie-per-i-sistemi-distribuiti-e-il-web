package count;
import java.io.*;
import java.util.*;
import count.*;

public class Open
{
	public static void main(String[] args)
	{
        X x=new X();
        T a=new T(x,'A');
        T b=new T(x,'B');
        try
        {
            a.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        try
        {
            b.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
	}
}
