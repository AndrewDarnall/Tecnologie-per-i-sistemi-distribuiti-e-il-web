import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private static final int port = 7777;
	private static final int maxReqLen = 10;
	private static List<String> que = new ArrayList<String>();

	private static String handleReq(String req) {
		String ret = "";

		switch (req) {
			case "LIST": {
				for (int i=0, len=que.size(); i<len; ++i)
					ret += que.get(i) + "\n";
			}
				break;
			default: {
				if (que.contains(req)) {
					ret = "Presente";

				} else {
					ret = "Inserita";

					if (que.size() == maxReqLen) {
						Random r = new Random();
						int idx = r.nextInt(10) + 1;

						que.set(idx, req);

					} else {
						que.add(req);
					}
				}
			}
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
				byte[] buf = new byte[50];
				DatagramPacket pkg = new DatagramPacket(buf, buf.length);
				DatagramPacket spkg;
				String s;

				sock.receive(pkg);

				s = handleReq(du.getCont(pkg));
				spkg = du.buildPkg(pkg.getAddress(), pkg.getPort(), s);

				sock.send(spkg);

			} catch (IOException e) {
				System.out.println("IO err " + e.getMessage());
				continue;
			
			} catch (Exception e) {
				System.out.println("unk err " + e.getMessage());
				sock.close();
				System.exit(1);
			}
		}
	}
}
