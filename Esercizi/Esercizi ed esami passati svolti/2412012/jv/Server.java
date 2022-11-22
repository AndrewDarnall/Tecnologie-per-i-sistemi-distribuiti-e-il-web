import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

class Animal {
	String name;
	int v;

	Animal(String n) {
		this.name = n;
		this.v = 0;
	}

	void incV() { ++this.v; }
	int getV() { return this.v; }
	String getName() { return this.name; }
}

public class Server {
	private static final int port = 7777;
	private static int totV = 0;
	private static final List<Animal> alist = new ArrayList() {{
		add(new Animal("cane"));
		add(new Animal("gatto"));
		add(new Animal("paperino"));
		add(new Animal("pluto"));
		add(new Animal("pikachu"));
	}};

	private static boolean isValid(String cmd) {
		return Pattern.matches("^\\[(G|v[0-9]+)\\]$", cmd);
	}

	private static String handleReq(String req) {
		char cmd = req.charAt(1);
		String ret = "";

		switch (cmd) {
			case 'G': {
				for (Animal a: alist)
					ret += a.getName() + ",";
				
				ret = ret.substring(0, ret.length()-1);
			}
				break;
			case 'v': {
				int idx = Integer.parseInt(req.substring(2, req.length()-1));

				if (idx >= alist.size()) {
					ret = "ERROR";
					break;
				}

				alist.get(idx).incV();
				++totV;

				for (Animal a: alist) {
					float p = (float)a.getV() / totV * 100;
					String ps = String.format("%.0f", p);

					ret += a.getName() + ":" + ps + "%,";
				}

				ret = ret.substring(0, ret.length()-1);
			}
				break;
			default:
				ret = "ERROR";
				break;
		}

		return ret;
	}

	public static void main(String args[]) {
		DgramUtils du = new DgramUtils();
		DatagramSocket sock = null;

		try {
			sock = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.println("sock err " + e.getMessage());
			System.exit(1);
		}

		while (true) {
			try {
				byte[] buf = new byte[100];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String re = "";

				sock.receive(pkg);

				re = handleReq(du.getCont(pkg));

				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), re);

				sock.send(spkg);

			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				System.exit(1);
			}
		}
	}
}
