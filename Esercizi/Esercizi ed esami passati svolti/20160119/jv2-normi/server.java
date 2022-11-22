import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class server {
	private static List<String> magz = new ArrayList<String>() {{
		add("p1:0");
		add("p2:20");
		add("p3:1");
		add("p4:5");
		add("p5:5");
		add("p6:51");
		add("p7:6");
		add("p8:3");
		add("p9:22");
		add("p10:5");
	}};

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static boolean isValid(String s) {
		return Pattern.matches("^[0-9]:[0-9]+$", s);
	}

	private static boolean isAvailable(String itm) {
		String[] t = itm.split(":");
		String[] t1 = magz.get(Integer.parseInt(t[0])).split(":");

		if (Integer.parseInt(t[1]) <= Integer.parseInt(t1[1]))
			return true;

		return false;
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(39999);

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
