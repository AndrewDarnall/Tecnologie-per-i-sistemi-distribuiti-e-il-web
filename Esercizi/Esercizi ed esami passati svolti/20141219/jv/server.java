import java.io.*;
import java.net.*;

public class server {
	private static String[] ban = new String[] {"esame", "difficile", "prolungamento"};

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static String muteStr(String s) {
		for (String b: ban)
			s = s.replaceAll(b, "***");

		return s;
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(12345);

		} catch (Exception e) {
			abort("sock err", e);
		}

		try {
			while (true) {
				Socket sock = ssock.accept();
				BufferedReader br = su.getReader(sock);
				PrintWriter pw = su.getWriter(sock);
				String s = br.readLine();

				pw.println(muteStr(s));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
