import java.io.*;
import java.net.*;

public class Client {
	private static final int port = 7777;

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		InetAddress adr = null;

		if (args.length == 0) {
			System.out.println("client [address]");
			System.exit(1);
		}

		try {
			sock = new DatagramSocket();
			adr = InetAddress.getByName(args[0]);

		} catch (Exception e) {
			System.out.println("sock err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				String cmd = System.console().readLine("Command: ");

				try {
					DatagramPacket pkg = du.buildPkg(adr, port, cmd);
					DatagramPacket rpkg;
					byte[] buf = new byte[100];
					String re = "";

					sock.send(pkg);

					rpkg = new DatagramPacket(buf, buf.length);

					sock.receive(rpkg);

					re = du.getCont(rpkg);

					System.out.println(re);

				} catch (Exception e) {
					System.out.println("unk err " + e.getMessage());
					System.exit (1);
				}
			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				System.exit (1);
			}
		}
	}
}
