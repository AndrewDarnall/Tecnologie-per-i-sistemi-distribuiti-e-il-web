import java.io.*;
import java.net.*;
import java.util.regex.*;

public class server {
	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static String cmd(String c, String[] aList, int[] vList) {
		String ret = "";

		switch (c.charAt(1)) {
			case 'G': {
				for (String sa: aList)
					ret += sa+" ";
			}
				break;
			case 'v': {
				int k = Integer.parseInt(c.substring(2, c.length()-1));

				if (k >= vList.length) {
					ret = "ERROR";
					break;
				}

				int tot = 0;

				++vList[k];

				for (int v: vList)
					tot += v;

				for (int i=0, l=vList.length; i<l; ++i) {
					float vv = (float)vList[i]/tot*100;

					ret += aList[i]+":"+vv+"%,";
				}

				ret = ret.substring(0, ret.length()-1);
			}
				break;
			default:
				break;
		}

		return ret;
	}

	private static boolean isValid(String s) {
		return Pattern.matches("^\\[(G|v[0-9]+)\\]$", s);
	}

	public static void main(String[] args) {
		String[] a = {"cane","gatto","paperino"};
		int[] v = {0,0,0};
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(7777);

		} catch (Exception e) {
			abort("sock err", e);
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
					pw.println("ERROR");
					sock.close();
					continue;
				}

				pw.println(cmd(s, a, v));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err",e);
		}
	}
}
