import java.io.*;
import java.net.*;

public class Client {
	private static final int port = 3233;

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("client address");
			System.exit(1);
		}

		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		InetAddress adr = null;

		try {
			sock = new DatagramSocket();
			adr = InetAddress.getByName(args[0]);

		} catch (SocketException e) {
			System.out.println("sock err " + e.getMessage());
			System.exit(1);
	
		} catch (UnknownHostException e) {
			System.out.println("host err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				String cmd = System.console().readLine("Command: ");

				try {
					DatagramPacket pkg = du.buildPkg(adr, port, cmd);
					DatagramPacket rpkg;
					byte[] buf = new byte[50];

					sock.send(pkg);

					rpkg = new DatagramPacket(buf, buf.length);

					sock.receive(rpkg);

					System.out.println("resp: " + du.getCont(rpkg));

				} catch (Exception e) {
					System.out.println("unk err " + e.getMessage());
				}
			} catch (Exception e) {
				System.out.println("invalid input");
			}
		}
	}
}
