package ProdCons;

import java.util.Random;

public class Consumer extends Thread {
    ProdCons prodCons;
    static int staticConsNum = 0;
    int consNum;

    public Consumer(ProdCons prodCons) {
        super();
        this.prodCons = prodCons;
        staticConsNum++;
        consNum = staticConsNum;
        setName("Consumer " + consNum);
    }

    public void run() {
        Random random = new Random();
        while (true) {
            try {
                sleep(random.nextInt(1500) + 500);
            } catch (InterruptedException e) {

                // Qui ricevo una sorta di "notifica" dal main che la creazione di
                // prodotti è terminata.
                prodCons.finished = true;
            }

            int val = prodCons.remove();

            if (val == -1) { // Il meteodo remove restituisce
                             // -1 come valore di terminazione del thread
                System.out.println(getName() + " termino!");
                return;
            }
        }
    }
}