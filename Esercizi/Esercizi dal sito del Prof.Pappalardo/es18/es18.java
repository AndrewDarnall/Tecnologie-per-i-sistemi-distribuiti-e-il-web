import java.rmi.*;

public interface es18 extends Remote
{
	public int hash(String s, int n) throws RemoteException;
	public int ask(String ip, int port, String msg) throws RemoteException;
}