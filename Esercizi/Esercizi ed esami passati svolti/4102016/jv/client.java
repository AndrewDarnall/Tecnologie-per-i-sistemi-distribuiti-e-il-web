import java.io.*;
import java.net.*;

public class client {
	private static final int port = 3333;

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		InetAddress saddr = null;

		if (args.length == 0) {
			System.out.println("client [address]");
			System.exit(1);
		}

		try {
			sock = new DatagramSocket();
			saddr = InetAddress.getByName(args[0]);

		} catch (SocketException e) {
			System.out.println("sock err " + e.getMessage());
			System.exit(1);

		} catch (UnknownHostException e) {
			System.out.println("addr err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				String ss = System.console().readLine("Enter string: ");

				try {
					byte[] buf = new byte[50];
					DatagramPacket pkg = du.buildPkg(saddr, port, ss);
					DatagramPacket rpkg = new DatagramPacket(buf, buf.length);
					String s = "";

					sock.send(pkg);
					sock.receive(rpkg);

					s = du.getCont(rpkg);

					System.out.println("re: "+s);

				} catch (Exception e) {
					System.out.println("wrong input");
					continue;
				}

			} catch (Exception e) {
				System.out.println("" + e.getMessage());
				sock.close();
				System.exit(1);
			}
		}
	}
}
