import java.io.*;
import java.net.*;
import java.util.regex.*;

public class server {
	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static boolean isValid(String s) {
		return Pattern.matches("^[+-][0-9]{4}$", s);
	}

	private static Integer decodeAndCompute(String s) {
		Integer n = Character.getNumericValue(s.charAt(1));

		switch (s.charAt(0)) {
			case '+': {
				for (int i=2; i<5; ++i)
					n += Character.getNumericValue(s.charAt(i));
			}
				break;
			case '-': {
				for (int i=2; i<5; ++i)
					n -= Character.getNumericValue(s.charAt(i));
			}
				break;
			default:
				break;
		}

		return n;
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(4242);

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
					pw.println("inv input");
					sock.close();
					continue;
				}

				pw.println(decodeAndCompute(s));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
