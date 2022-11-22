import java.io.*;
import java.net.*;

public class Client {
	private static final int port = 7777;

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
			System.out.println("host err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				String cmd = System.console().readLine("command: ");

				if (cmd.length() > 10) {
					System.out.println("max 10 chars, retry..");
					continue;
				}

				try {
					byte[] buf = new byte[50];
					DatagramPacket pkg = du.buildPkg(saddr, port, cmd);
					DatagramPacket rpkg;

					sock.send(pkg);

					rpkg = new DatagramPacket(buf, buf.length);

					sock.receive(rpkg);
					System.out.println(du.getCont(rpkg));

				} catch (Exception e) {
					System.out.println("invalid input");
				}
			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				System.exit(1);
			}
		}
	}
}
