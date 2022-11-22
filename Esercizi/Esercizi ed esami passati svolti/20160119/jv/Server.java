import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.rmi.*;
import java.rmi.registry.*;

public class Server {
	private static final int port = 39999;

	private static boolean isValid(String req) {
		return Pattern.matches("^[0-9]+:[0-9]+$", req);
	}

	private static String handleReq(String req, RMIInt serv) {
		String ret = "False";

		try {
			ret = serv.isItemQtiAvail(req);

		} catch (Exception e) {
			System.out.println("rmi err " + e.getMessage());
			return "RMI Err.";
		}

		return ret;
	}

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		RMIInt magazzino = null;

		try {
			Registry reg = LocateRegistry.getRegistry();

			sock = new DatagramSocket(port);
			magazzino = (RMIInt) reg.lookup("magazzinoServ");

		} catch (RemoteException e) {
			System.out.println("rmi err " + e.getMessage());

		} catch (SocketException e) {
			System.out.println("sock err " + e.getMessage());
			System.exit(1);

		} catch (Exception e) {
			System.out.println("unk err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[50];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String ret = "";

				sock.receive(pkg);

				ret = du.getCont(pkg);
				if (!isValid(ret)) {
					spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), "invalid input");

					System.out.println("invalid input");
					sock.send(spkg);
					continue;
				}

				ret = handleReq(ret, magazzino);
				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), ret);

				sock.send(spkg);

			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
			}
		}
	}
}
