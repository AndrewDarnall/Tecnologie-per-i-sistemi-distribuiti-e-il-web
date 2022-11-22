import java.io.*;
import java.net.*;

public class client {
	private static final int port = 7777;

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("client cmd");
			System.exit(1);
		}

		SockUtils su = new SockUtils();
		InetAddress adr = null;
		Socket sock = null;

		try {
			adr = InetAddress.getByName("localhost");
			sock = new Socket(adr, port);

		} catch (Exception e) {
			abort("sock err",e);
		}

		try {
			BufferedReader br = su.getReader(sock);
			PrintWriter pw = su.getWriter(sock);
			String s = null, c = null;

			pw.println(args[0]);
			System.out.println(br.readLine());
			sock.close();

		} catch (Exception e) {
			abort("unk err",e);
		}
	}
}
