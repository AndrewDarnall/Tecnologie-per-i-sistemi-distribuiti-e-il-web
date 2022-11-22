/*  LineUtility.java
    Classe usata per definire alcuni metodi utili nella selezione delle linee di un file 
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class LineUtility {	
    // metodo per recuperare una certa linea di un certo file
    static String getLine(String nomeFile, int numLinea) {
        String linea = null;
        BufferedReader in = null;
        // associazione di uno stream di input al file da cui estrarre le linee
        try {
            in = new BufferedReader(new FileReader(nomeFile));
            System.out.println("File aperto: "+nomeFile);
        } 
        catch (FileNotFoundException e) {
            System.out.println("File non trovato: ");
            e.printStackTrace();
            return linea = "File non trovato";
        }
        try {
            for (int i=1; i<numLinea; i++) in.readLine();
            if ((linea = in.readLine()) == null) {
                System.out.println("Linea non trovata");
                linea = "Linea non trovata";
            }
        } 
        catch (IOException e) {
            System.out.println("Linea non trovata: ");
            e.printStackTrace();
            return linea = "Linea non trovata";
        }
        System.out.println("Linea selezionata: "+linea);
        return linea;
    } // getLine

    //metodo per recuperare la linea successiva di un file gia' aperto in precedenza
    static String getNextLine(BufferedReader in) {
        String linea = null;
        try {
            if ((linea = in.readLine()) == null) {
                in.close();
                linea = "Nessuna linea disponibile";
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("File non trovato: ");
            e.printStackTrace();
            return linea = "File non trovato";
        }
        catch (IOException e) {
            System.out.println("Problemi nell'estrazione della linea: ");
            e.printStackTrace();
            linea = "Nessuna linea disponibile";
        }
        return linea;
    } //getNextLine
}
