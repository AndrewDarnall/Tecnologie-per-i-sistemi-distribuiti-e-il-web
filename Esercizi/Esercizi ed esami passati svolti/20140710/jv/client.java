import java.io.*;
import java.net.*;

public class client {
	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("client sXXXX");
			System.exit(1);
		}

		SockUtils su = new SockUtils();
		InetAddress adr = null;
		Socket sock = null;

		try {
			adr = InetAddress.getByName("localhost");
			sock = new Socket(adr, 4242);

		} catch (Exception e) {
			abort("sock err", e);
		}

		try {
			BufferedReader br = su.getReader(sock);
			PrintWriter pw = su.getWriter(sock);

			pw.println(args[0]);
			System.out.println(br.readLine());
			sock.close();

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
