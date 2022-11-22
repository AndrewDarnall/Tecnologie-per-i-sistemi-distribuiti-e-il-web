import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

public class RMIServer implements RMIInt {
	public RMIServer() throws RemoteException {
		super();
	}

	public int conta(String s) {
		return s.length();
	}

	public static void main(String args[]) {
		try {
			RMIInt rmiS = new RMIServer();
			RMIInt stub = (RMIInt)java.rmi.server.UnicastRemoteObject.exportObject(rmiS, 0);
			Registry reg = LocateRegistry.createRegistry(1099);

			reg.rebind("contaServ", stub);

		} catch (Exception e) {
			System.out.println("rmi err " + e.getMessage());
			System.exit(1);
		}
	}
}
