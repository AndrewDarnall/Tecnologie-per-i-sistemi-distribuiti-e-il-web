import java.io.*;
import java.net.*;
import java.util.regex.*;

public class server {
	private static final int port = 3333;

	private static boolean isValid(String s) {
		return Pattern.matches("[0-9]+", s);
	}

	private static int cubo(int n) {
		return (int)Math.pow(n, 3);
	}

	public static void main(String[] args) {
		ServerSocket ssock = null;
		SockUtils su = new SockUtils();

		try {
			ssock = new ServerSocket(port);

		} catch (Exception e) {
			System.out.println("sock err "+e.getMessage());
			System.exit(1);
		}

		try {
			while (true) {
				Socket sock = ssock.accept();
				BufferedReader br = su.getReader(sock);
				PrintWriter pw = su.getWriter(sock);
				String s = br.readLine();

				if (!isValid(s)) {
					System.out.println("inv input");
					sock.close();
					continue;
				}

				pw.println(cubo(Integer.parseInt(s)));

				sock.close();
			}
		} catch (Exception e) {
			System.out.println("unk err "+e.getMessage());
			System.exit(1);
		}

	}
}
