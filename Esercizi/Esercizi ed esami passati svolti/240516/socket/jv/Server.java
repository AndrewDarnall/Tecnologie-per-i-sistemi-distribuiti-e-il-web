import java.io.*;
import java.net.*;

public class Server {
	private static final int port = 3333;

	private static int cubo(int n) { return (int)Math.pow(n, 3); }

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true"); // forse

		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;

		try {
			sock = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.println("sock err: " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[100];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				int n;

				sock.receive(pkg);

				n = Server.cubo(du.getCont(pkg));
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
