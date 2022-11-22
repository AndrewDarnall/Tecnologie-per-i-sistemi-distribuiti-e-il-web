import java.rmi.*;

public interface RMIInt extends Remote {
	int cubo(int n) throws RemoteException;
}
