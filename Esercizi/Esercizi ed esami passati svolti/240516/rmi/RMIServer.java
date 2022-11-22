import java.rmi.*;
import java.rmi.registry.*;

public class RMIServer implements RMIInt {
	public int cubo(int n) {
		return (int)Math.pow(n, 3);
	}

	public RMIServer() throws RemoteException {
		super();
	}

	public static void main(String args[]) {
		try {
			RMIInt rmiSer = new RMIServer();
			RMIInt stub = (RMIInt)java.rmi.server.UnicastRemoteObject.exportObject(rmiSer, 0);
			Registry reg = LocateRegistry.createRegistry(1099);

			reg.rebind("cuboServ", stub);

		} catch (Exception e) {
			System.out.println("rmi err " + e.getMessage());
			System.exit(1);
		}
	}
}
