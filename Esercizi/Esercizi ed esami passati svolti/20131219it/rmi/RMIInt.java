import java.rmi.*;

public interface RMIInt extends Remote {
	int conta(String s) throws RemoteException;
}
