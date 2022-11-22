import java.io.*;
import java.net.*;

class Book {
	private String title;
	private String txt;

	public Book(String t, String t2) {
		title = t;
		txt = t2;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return txt;
	}
}

public class server {
	private static Book[] books = new Book[] {
		new Book("Titolo accattivante 1", "bel testo"),
		new Book("Titolo accattivante 2", "più bel testo"),
		new Book("Titolo accattivante 3", "crypt"),
		new Book("Titolo accattivante 4", "oh"),
		new Book("Titolo accattivante 5", "ah")
	};

	private static void abort(String m, Exception e) {
		System.out.println(m+" "+e.getMessage());
		System.exit(1);
	}

	private static String inizioFine(String s) {
		for (Book b: books) {
			if (s.equals(b.getTitle())) {
				return b.getText();
			}
		}

		return "not found";
	}

	public static void main(String args[]) {
		SockUtils su = new SockUtils();
		ServerSocket ssock = null;

		try {
			ssock = new ServerSocket(9999);

		} catch (Exception e) {
			abort("sock err", e);
		}

		try {
			while (true) {
				Socket sock = ssock.accept();
				BufferedReader br = su.getReader(sock);
				PrintWriter pw = su.getWriter(sock);
				String s = br.readLine();

				System.out.println("recv: "+s);
				pw.println(inizioFine(s));
				sock.close();
			}

		} catch (Exception e) {
			abort("unk err", e);
		}
	}
}
