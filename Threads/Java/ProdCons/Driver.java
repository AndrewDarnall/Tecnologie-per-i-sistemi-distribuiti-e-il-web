package ProdCons;

import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {
        ArrayList<Thread> threads = new ArrayList<>();
        ProdCons prodCons = new ProdCons(10);

        threads.add(new Producer(prodCons, 10));
        threads.add(new Producer(prodCons, 15));
        threads.add(new Producer(prodCons, 7));
        threads.add(new Producer(prodCons, 8));

        threads.add(new Consumer(prodCons));
        threads.add(new Consumer(prodCons));

        threads.forEach(thread -> thread.start());

        // Mi metto in attesa dei soli produttori.
        threads.forEach(thread -> {
            if (thread instanceof Producer) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Dopo che tutti i produttori hanno finito, notifico i consumatori.
        // Questi consumeranno tutti i prodotti e dopo termineranno.
        threads.forEach(thread -> {
            if (thread instanceof Consumer)
                thread.interrupt();
        });

    }
}
