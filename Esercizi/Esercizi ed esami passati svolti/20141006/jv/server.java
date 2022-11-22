import java.io.*;
import java.net.*;
import java.util.regex.*;

public class server {
	private static String[] bibl = new String[] {"lal,3", "aha,4", "opp,10", "rrr,5"};

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static boolean isValid(String s) {
		return Pattern.matches("^[\\w\\d]+,[0-9]+$", s);
	}

	private static boolean isAvailable(String s) {
		String[] t = s.split(",");

		for (String b: bibl) {
			String[] t1 = b.split(",");

			if (t[0].equals(t1[0]) && Integer.parseInt(t[1]) <= Integer.parseInt(t1[1]))
				return true;
		}
		
		return false;
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(39939);

		} catch (Exception e) {
			abort("sock err", e);
		}

		try {
			while (true) {
				Socket sock = ssock.accept();
				BufferedReader br = su.getReader(sock);
				PrintWriter pw = su.getWriter(sock);
				String s = br.readLine();

				if (!isValid(s)) {
					pw.println("inv inpt");
					sock.close();
					continue;
				}

				pw.println(isAvailable(s));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
