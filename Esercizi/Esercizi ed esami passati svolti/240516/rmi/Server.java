import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.*;

public class Server {
	private static final int port = 3333;

	private static int cubo(int n, RMIInt serv) throws RemoteException {
		return serv.cubo(n);
	}

	public static void main(String[] args) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		RMIInt rmiServ = null;

		try {
			Registry reg = LocateRegistry.getRegistry();
			
			sock = new DatagramSocket(port);
			rmiServ = (RMIInt) reg.lookup("cuboServ");

		} catch (RemoteException e) {
			System.out.println("rmi err " + e.getMessage());
			System.exit(1);

		} catch (SocketException e) {
			System.out.println("sock err: " + e.getMessage());
			System.exit(1);

		} catch (Exception e) {
			System.out.println("unk err: " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[100];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				int n;

				sock.receive(pkg);

				n = Server.cubo(du.getCont(pkg), rmiServ);
				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), n);

				sock.send(spkg);

			} catch (IOException e) {
				System.out.println("IO err: " + e.getMessage());
				continue;

			} catch (Exception e) {
				System.out.println("unk err: " + e.getMessage());
				e.printStackTrace();
				sock.close();
				System.exit(1);
			}
		}
	}
}
