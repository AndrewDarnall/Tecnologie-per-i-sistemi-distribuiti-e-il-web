import java.io.*;
import java.net.*;

public class client {
	private static final int port = 3333;

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("client msg");
			System.exit(1);
		}

		Socket sock = null;
		InetAddress adr = null;
		SockUtils su = new SockUtils();

		try {
			adr = InetAddress.getByName("localhost");
			sock = new Socket(adr, port);

		} catch (Exception e) { abort("sock err", e); }

		try {
			BufferedReader br = su.getReader(sock);
			PrintWriter pw = su.getWriter(sock);

			pw.println(args[0]);

			System.out.println("res: "+br.readLine());
			sock.close();

		} catch (Exception e) { abort("unk err", e); }
	}
}
