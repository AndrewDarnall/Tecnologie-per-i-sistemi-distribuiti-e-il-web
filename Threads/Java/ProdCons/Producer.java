package ProdCons;

import java.util.Random;

public class Producer extends Thread {

    static int staticProdNum = 0;

    ProdCons prodCons;
    int prodNum;
    int toProduce;

    public Producer(ProdCons prodCons, int toProduce) {
        super();
        this.prodCons = prodCons;

        staticProdNum++;
        prodNum = staticProdNum;

        this.toProduce = toProduce;

        setName("Producer " + prodNum);

    }

    public void run() {
        Random random = new Random();
        for (int i = 0; i < toProduce; i++) {
            try {
                sleep(random.nextInt(1500) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int n = random.nextInt(90) + 10;

            prodCons.insert(n);

        }
        System.out.println(getName() + " ho finito!");
    }
}
