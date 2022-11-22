/*
Scrivere in C o Java un programma che:

    apra una connessione verso l'host www.dmi.unict.it, port 80
    invii la stringa "GET /pappalardo/prova/08.aux\n"
    nello stream testo ricevuto, salti le prime 22015 righe e scriva sulla standard output le righe dalla 22016 alla 22023
    salti altre 3828 righe in avanti
    legga il numero intero all'inizio della nuova riga corrente: si tratta di un ulteriore numero di riga, che diremo n
    scriva le righe dalla n alla n+8

*/

import java.io.*;
import java.net.*;

public class es08 {

	public static void main(String[] args) {

		String tmp = "", tmp2 = "";
		int n = 0, i;


		try {
			InetAddress address = InetAddress.getByName("www.dmi.unict.it");

			Socket client = new Socket(address, 80);

			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("GET /pappalardo/prova/08.aux");

			for (i = 0;  i < 22023; i++) {

				tmp = in.readLine();

				if (i >= 22016 && i < 22023)
					tmp2 += tmp + "\n";
			}

			tmp2 += "\n";

			for (; i < 22023 + 3828 + 1; i++)
				tmp = in.readLine();

			n = Integer.parseInt(tmp);

			for (; (tmp = in.readLine()) != null; i++) {

				if (n != 0 && i >= n-1 && i <= n+6)
					tmp2 += tmp + "\n";
			}


			System.out.println(tmp2);
		}
		catch (Exception e) { e.printStackTrace(); }

	}
}

