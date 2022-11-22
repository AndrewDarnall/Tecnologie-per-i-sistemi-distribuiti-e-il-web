import java.io.*;
import java.net.*;
import java.util.regex.*;

public class server {
	private static final int port = 3333;

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static boolean isValid(String s) {
		return Pattern.matches("^[VF]+$", s);
	}

	private static String and(String s) {
		if (Pattern.matches("^[V]+$", s))
			return "Vero";
		else
			return "Falso";
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(port);

		} catch (Exception e) {
			abort("sock err",e);
		}

		try {
			while (true) {
				BufferedReader br = null;
				PrintWriter pw = null;
				Socket sock = null;
				String s = null;

				sock = ssock.accept();
				br = su.getReader(sock);
				pw = su.getWriter(sock);

				s = br.readLine();

				if (!isValid(s)) {
					pw.println("inv inpt");
					sock.close();
					continue;
				}

				pw.println(and(s));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err",e);
		}
	}
}
