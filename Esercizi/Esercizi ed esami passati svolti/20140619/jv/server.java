import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	private static final int port = 44332;

	private static String makeRandString(int len) {
		String ret = "";

		for (int i=0; i<len; ++i) {
			int j = (int)(Math.random() * 2 + 1);

			ret += (j < 1 ? '0':'1');
		}

		return ret;
	}

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;

		try {
			sock = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.println("sock err");
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[5];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String re = "";
				int n, len, np, i=0;

				sock.receive(pkg);

				n = Integer.parseInt(du.getCont(pkg));
				len = (int)(Math.random()*n*100);
				re = makeRandString(len);
				np = (int)Math.floor(len/20);

				while (np-- > 0) {
					DatagramPacket ack = new DatagramPacket(new byte[1], 1);
					int m = i+18 >= len ? len-1:i+18;
					
					spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), re.substring(i, m));
					i = m+18 >= len ? len-1:i+18;
					
					sock.send(spkg);
					sock.receive(ack);
				}

				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), "");
				sock.send(spkg);

				System.out.println("recv "+n+", sending..."+re);

			} catch (Exception e) {
				System.out.println("unk err "+e.getMessage());
				System.exit(1);
			}
		}
	}
}
