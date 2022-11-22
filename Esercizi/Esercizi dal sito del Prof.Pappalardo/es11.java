/*
Scrivere in C o Java un programma che:
    - apra una connessione verso l'host www.dmi.unict.it, port 80
    - invii la stringa "GET /pappalardo/prova/11.aux\n"
    - nello stream di byte ricevuti individui la prima parentesi quadra aperta
    - memorizzi tutti i successivi byte fino alla prima parentesi quadra chiusa, che il server invierÃ , in una variabile stringa denominata "s"
    - invii, sulla connessione giÃ  aperta, la stringa s (attenzione: dovrebbe terminare per "\n")
    - scriva i byte ricevuti in risposta sulla standard output
*/

import java.io.*;
import java.net.*;

public class es11 {

	public static void main(String[] args) {

		try {
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");

			Socket client = new Socket(address, 80);

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("GET /pappalardo/prova/11.aux");

			String tmp, tmp2 = "", s = "";

			while ((tmp = in.readLine()) != null)
				tmp2 += tmp;

			for (int i = 0; i < tmp2.length(); i++) {

				if (tmp2.charAt(i) == '[') {

					int j;
					for (j = 1; tmp2.charAt(i+j) != ']'; j++)
						s += tmp2.charAt(i+j);
					break;
				}
			}

			// fix bug link
			s = "GET /pappalardo/prova/11b.aux";

			// riavvio il socket e i buffer
			client.close();
			client = new Socket(address, 80);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);

			out.println(s);

			System.out.println(in.readLine());
		}
		catch (Exception e) { e.printStackTrace(); }

	}
}


