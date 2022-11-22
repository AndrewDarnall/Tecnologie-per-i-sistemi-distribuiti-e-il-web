import java.rmi.*;

public interface periodChecker extends Remote
{
	public boolean finiscePeriodo(String str) throws RemoteException;
}
