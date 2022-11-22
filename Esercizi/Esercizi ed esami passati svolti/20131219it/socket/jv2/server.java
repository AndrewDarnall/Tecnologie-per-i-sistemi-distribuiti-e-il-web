import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	private static final int port = 7777;

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static void handleReq(String rq, List<String> que, PrintWriter pw) {
		switch (rq) {
			case "LIST": {
				for (String r: que)
					pw.println(r);
			}
				break;
			default: {
				if (que.contains(rq)) {
					pw.println("presente");

				} else if (que.size() == 10) {
					que.set((int)Math.random()*9+1, rq);
					pw.println("inserito");

				} else {
					que.add(rq);
					pw.println("inserito");
				}
			}
				break;
		}
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;
		List<String> que = new ArrayList<String>();

		try {
			ssock = new ServerSocket(port);

		} catch (Exception e) {
			abort("sock err", e);
		}

		try {
			while (true) {
				BufferedReader br;
				PrintWriter pw;
				Socket sock;
				String s;

				sock = ssock.accept();
				br = su.getReader(sock);
				pw = su.getWriter(sock);
				s = br.readLine();

				handleReq(s, que, pw);
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
