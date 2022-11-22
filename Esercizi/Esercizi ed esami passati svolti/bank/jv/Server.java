import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Server {
	private static final int port = 7777;
	private static int[] conto = new int[10];
	private static int curIdx = -1;

	private static boolean isValid(String req) {
		return Pattern.matches("^\\[S\\]$|^\\[(V|P)[0-9]{4}\\]$|^\\[U[0-9]\\]$", req);
	}

	private static String handleReq(String req) {
		char cmd = req.charAt(1);
		String ret = "ERROR";

		if (curIdx == -1 && cmd != 'U')
			return ret;
		else if (!isValid(req))
			return "invalid input";

		switch (cmd) {
			case 'U': {
				int idx = Integer.parseInt(""+req.charAt(2));

				if (idx > conto.length)
					return ret;

				curIdx = idx;
				ret = "Sel conto "+idx;
			}
				break;
			case 'V': {
					  System.out.println("entro v");
				Scanner scan = new Scanner(req);
				MatchResult m;
				int q;

				scan.findInLine("\\[V(\\d+)\\]");

				m = scan.match();
				q = Integer.parseInt(m.group(1));

				conto[curIdx] += q;
				ret = "Verso ["+curIdx+"] "+q;
				System.out.println(ret);
			}
				break;
			case 'P': {
				Scanner scan = new Scanner(req);
				MatchResult m;
				int q;

				scan.findInLine("\\[P(\\d+)\\]");

				m = scan.match();
				q = Integer.parseInt(m.group(1));

				if (q > conto[curIdx])
					return ret;

				conto[curIdx] -= q;
				ret = "Prendo ["+curIdx+"] "+q;
			}
				break;
			case 'S': {
				ret = "Saldo ["+curIdx+"] "+conto[curIdx];
			}
				break;
			default:
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
			System.out.println("sock err");
			System.exit(1);
		}

		while (true) {
			byte[] buf = new byte[20];
			DatagramPacket pkg = new DatagramPacket(buf, buf.length);
			DatagramPacket spkg;
			String ret = "";

			curIdx = 0;

			try {
				sock.receive(pkg);

				ret = handleReq(du.getCont(pkg));
				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), ret);

				sock.send(spkg);

			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				continue;
			}
		}
	}
}
