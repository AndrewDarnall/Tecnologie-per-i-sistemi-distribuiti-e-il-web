/*
Scrivere in C o Java un programma che:

1)    apra una connessione verso l'host www.dmi.unict.it, port 80
2)    invii la stringa "GET /pappalardo/prova/07.aux\n"
3)    nello stream di byte ricevuti, cerchi il primo contenente il carattere '-'
4)    memorizzi quindi i successivi 31 byte
5)    scriva questi 31 byte sulla standard output
*/


import java.io.*;
import java.net.*;

public class es07 {

	public static void main(String[] args) {

		try {
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");

			Socket client = new Socket(address, 80);

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("GET /pappalardo/prova/07.aux");

			String tmp, tmp2 = "", s = "";

			while ((tmp = in.readLine()) != null)
				tmp2 += in.readLine();

			for (int i = 0; i < tmp2.length(); i++) {

				if (tmp2.charAt(i) == '-') {

					for (int j = 1; j <= 32; j++) {
						s+= tmp2.charAt(i+j);
					}
					break;
				}
			}

			System.out.println(s);

		}
		catch (Exception e) { e.printStackTrace(); }

	}
}
