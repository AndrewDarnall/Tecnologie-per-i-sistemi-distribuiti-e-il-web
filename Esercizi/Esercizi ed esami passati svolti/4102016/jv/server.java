import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class server {
	private static final int port = 3333;

	private static boolean isValidInp(String s) {
		return Pattern.matches("^[VF]+$", s);
	}

	private static String and(String s) {
		return Pattern.matches("^[V]+$", s) ? "Vero":"Falso";
	}

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;

		try {
			sock = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.println("sock err" + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[50];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String s = "";

				sock.receive(pkg);

				s = du.getCont(pkg);
				if (!isValidInp(s)) {
					System.out.println("invalid input");

				} else {
					spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), and(s));

					System.out.println("msg: " + s);
					sock.send(spkg);
				}

			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				System.exit(1);
			}
		}
	}
}
