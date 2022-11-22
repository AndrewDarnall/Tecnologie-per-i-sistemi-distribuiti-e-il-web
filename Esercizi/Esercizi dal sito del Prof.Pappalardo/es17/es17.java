import java.rmi.*;

public interface es17 extends Remote
{
	public int hash(String s, int n) throws RemoteException;
}
