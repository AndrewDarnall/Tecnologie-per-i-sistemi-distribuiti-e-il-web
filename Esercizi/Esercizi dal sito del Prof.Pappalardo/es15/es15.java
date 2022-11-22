import java.rmi.*;

public interface es15 extends Remote
{
	public int validate(String str) throws RemoteException;
}
