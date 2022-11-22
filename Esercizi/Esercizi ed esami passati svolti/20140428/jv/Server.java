import java.io.*;
import java.net.*;
import java.util.*;
import java.time.*;

public class Server {
	private static final int port = 3233;
	private static List<InetAddress> banList = new ArrayList<InetAddress>();

	private static boolean tryBan(InetAddress adr, InetAddress padr, Instant firstReq) {
		Instant now = Instant.now();

		if (!adr.equals(padr))
			return false;

		for (InetAddress a: banList) {
			if (a.equals(adr))
				return true;
		}

		if (Duration.between(firstReq,now).toSeconds() > 4)
			return false;

		banList.add(adr);

		return true;
	}

	private static String handleReq(String req) {
		if (req.equals("TIME"))
			return Instant.now().toString();

		return "N/A";
	}

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		InetAddress padr = null;
		Instant firstReq = null;

		try {
			sock = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.println("Sock err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[50];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String res = "";

				sock.receive(pkg);

				if (tryBan(pkg.getAddress(), padr, firstReq))
					res = "BANNED";
				else
					res = handleReq(du.getCont(pkg));

				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), res);

				sock.send(spkg);

				firstReq = Instant.now();
				padr = pkg.getAddress();

			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
			}
		}
	}
}
