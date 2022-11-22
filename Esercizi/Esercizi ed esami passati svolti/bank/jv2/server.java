import java.io.*;
import java.net.*;
import java.util.regex.*;

public class server {
	private static int[] conto = new int[] {0,0,0,0,0,0,0,0,0,0};
	private static int cidx = -1;

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static boolean isValid(String s) {
		return Pattern.matches("^\\[(U[0-9]|S|[VP][0-9]{4})\\]$", s);
	}

	private static String handleReq(String s) {
		String ret = "";

		switch (s.charAt(1)) {
			case 'U': {
				cidx = Character.getNumericValue(s.charAt(2));
				ret = "conto "+cidx+" selezionato";
			}
				break;
			case 'V':
			case 'P': {
				int v = Integer.parseInt(s.substring(2, s.length()-1));

				if (s.charAt(1) == 'P' && (conto[cidx]-v) < 0) {
					ret = "invalid operation";
					break;

				} else if (cidx == -1) {
					ret = "selezionare conto";
					break;
				}

				conto[cidx] = s.charAt(1) == 'V' ? conto[cidx]+v:conto[cidx]-v;
				ret = "success";
			}
				break;
			case 'S': {
				if (cidx == -1) {
					ret = "selezionare conto.";
					break;
				}

				ret = "Saldo: "+conto[cidx];
			}
				break;
			default:
				ret = "ERROR";
				break;
		}

		return ret;
	}

	public static void main(String[] args) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(7777);

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

				pw.println(handleReq(s));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
