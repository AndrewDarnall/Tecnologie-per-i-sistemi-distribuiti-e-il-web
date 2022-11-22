/**
 * NO IFDEFS :(
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private static final int port = 9999;
	private static HashMap<String, String> books = new HashMap<String, String>();

	private static void initMap() {
		for (int i=0; i<5; ++i)
			books.put("Book"+i, "sasasa "+i);
	}

	private static String inizioFine(String title) {
		if (books.containsKey(title))
			return books.get(title);
		else
			return "not found";
	}

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;

		try {
			sock = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.println("sock err " + e.getMessage());
			System.exit(1);
		}

		initMap();

		while (true) {
			try {
				byte[] buf = new byte[20];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String ret = "";

				sock.receive(pkg);

				ret = inizioFine(du.getCont(pkg));
				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), ret);

				sock.send(spkg);

				System.out.println("recv: "+du.getCont(pkg));

			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				sock.close();
				System.exit(1);
			}
		}
	}
}

