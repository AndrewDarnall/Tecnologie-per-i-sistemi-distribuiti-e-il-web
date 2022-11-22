import java.rmi.*;

public interface RMIInt extends Remote {
	String isItemQtiAvail(String req) throws RemoteException;
}
