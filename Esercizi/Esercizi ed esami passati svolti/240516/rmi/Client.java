import java.io.*;
import java.net.*;

public class Client {
	private static final int port = 3333;

	public static void main(String[] args) throws IOException {
		System.setProperty("java.net.preferIPv4Stack" , "true"); // forse

		InetAddress saddr = null;
		DatagramSocket sock = null;
		DgramUtils du = new DgramUtils();

		if (args.length == 0) {
			System.out.println("client [address]");
			System.exit(1);
		}

		try {
			sock = new DatagramSocket();
			saddr = InetAddress.getByName(args[0]);

		} catch (SocketException e) {
			System.out.println("sock err: " + e.getMessage());
			System.exit(1);

		} catch (UnknownHostException e) {
			System.out.println("unk host: " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				String n = System.console().readLine("Enter a number: ");

				try {
					byte[] buf = new byte[100];
					DatagramPacket pkg = du.buildPkg(saddr, port, Integer.parseInt(n));
					DatagramPacket rpkg;

					sock.send(pkg);

					rpkg = new DatagramPacket(buf, buf.length);

					sock.receive(rpkg);
					System.out.println(du.getCont(rpkg));

				} catch (Exception e) {
					System.out.println("invalid input");
					continue;
				}
			} catch (Exception e) {
				System.out.println("unk err: " + e.getMessage());
				sock.close();
				break;
			}
		}
	}
}
