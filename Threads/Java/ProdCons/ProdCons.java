package ProdCons;

import java.util.ArrayList;
import java.util.List;

public class ProdCons {

    public final int DIM;
    List<Integer> buffer;
    public boolean finished;

    public ProdCons(int DIM) {
        this.DIM = DIM;
        buffer = new ArrayList<>();
        finished = false;
    }

    synchronized public void insert(int val) {
        String nome = Thread.currentThread().getName();
        while (buffer.size() >= DIM) {
            System.out.println(nome + " mi blocco!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Qui ho ricevuto notify, ma non è detto che il buffer abbia spazio!
            // While FONDAMENTALE
        }
        System.out.println(nome + " mi sblocco e aggiungo " + val);
        buffer.add(val);
        System.out.println(buffer);

        // Sveglio UN THREAD A CASO (Prod? Cons? Non so) *vedi commento sopra*
        notify();

    }

    synchronized public int remove() {
        String nome = Thread.currentThread().getName();

        // Mi blocco se la coda è piena MA NON se il processo di produzione è finito
        while (buffer.size() == 0 && finished == false) {

            System.out.println(nome + " mi blocco!");

            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // SE FINISHED == TRUE e la coda è vuota, mando -1 per dire al
        // consumatore di terminare. Uso questo metodo per non chiamare .stop()
        // in quanto deprecato

        if (finished == true && buffer.size() == 0)
            return -1;

        int lastIndex = buffer.size() - 1;
        int val = buffer.get(lastIndex);

        System.out.println(nome + " mi sblocco e rimuovo l'ultimo elemento " + val);

        buffer.remove(lastIndex);
        System.out.println(buffer);
        notify();

        return val;
    }
}
