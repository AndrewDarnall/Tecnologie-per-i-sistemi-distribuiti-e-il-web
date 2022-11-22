package attesaPariDispari;
import java.io.*;
import java.util.*;
import attesaPariDispari.*;

public class MattesaPariDispari
{
	public static void main(String[] args)
	{
        M m=new M();
        T1 t1=new T1(m);
        T2 t2=new T2(m);
        T3 t3=new T3(m);
        try
        {
            t1.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        try
        {
            t2.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        try
        {
            t3.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
	}
}
