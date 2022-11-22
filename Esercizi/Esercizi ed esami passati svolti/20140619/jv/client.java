import java.io.*;
import java.net.*;

public class client {
	private static final int port = 44332;

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;
		InetAddress adr = null;

		if (args.length == 0) {
			System.out.println("client address");
			System.exit(1);
		}

		try {
			sock = new DatagramSocket();
			adr = InetAddress.getByName(args[0]);

		} catch (Exception e) {
			System.out.println("sock err");
			System.exit(1);
		}

		try {
			Integer n = (int)(Math.random()*256+1);
			String sn = n.toString();
			DatagramPacket pkg = du.buildPkg(adr, port, sn);
			byte[] buf = null;
			DatagramPacket rpkg = null;
			String re = "", c = "";

			sock.send(pkg);

			while(true) {
				DatagramPacket ack = du.buildPkg(adr, port, "");

				buf = new byte[20];
				rpkg = new DatagramPacket(buf, buf.length);

				sock.receive(rpkg);

				c = du.getCont(rpkg);

				if (c.equals(""))
					break;

				re += c;

				sock.send(ack);
			}

			System.out.println(re);

		} catch (Exception e) {
			System.out.println("unk err "+e.getMessage());
			System.exit(1);
		}
	}
}
