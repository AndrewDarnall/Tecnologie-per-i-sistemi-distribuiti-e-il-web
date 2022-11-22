import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;

public class server {
	private static List<InetAddress> banList = new ArrayList<InetAddress>();

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static boolean tryBan(InetAddress cadr, InetAddress padr, Instant prev) {
		Instant now = Instant.now();

		if (!cadr.equals(padr))
			return false;

		if (banList.contains(cadr))
			return true;

		if (Duration.between(prev, now).toSeconds() > 4)
			return false;

		banList.add(cadr);
		return true;
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;
		InetAddress padr = null;
		Instant prev = null;

		try {
			ssock = new ServerSocket(3233);

		} catch (Exception e) {
			abort("sock err", e);
		}

		try {
			while (true) {
				Socket sock = ssock.accept();
				BufferedReader br = su.getReader(sock);
				PrintWriter pw = su.getWriter(sock);
				String s = br.readLine();

				if (tryBan(sock.getInetAddress(), padr, prev))
					pw.println("BANNED");
				else if (s.equals("TIME"))
					pw.println(System.nanoTime());
				else
					pw.println("N/A");

				sock.close();

				prev = Instant.now();
				padr = sock.getInetAddress();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
