/**
 * Soluzione proposta -> Andrew R. Darnall
 * 
 */

import java.util.Random;

//Shared object
class Shared {

    private int turn = 0;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int indx) {
        turn = indx;
    }

}

class MyThread extends Thread {

    private Shared shm;
    private String tName = "Thread[";
    private int waitTime = 500;
    private int position = 0;
    private int index;
    private Random rand = new Random();

    //Tramite questo costruttore, la classe si aggancia all'oggetto condiviso
    public MyThread(Shared mem, int index) {
        this.shm = mem;
        this.index = index;
        this.tName = tName + index + "]";
    }

    @Override
    public void run() {

        while(true) {

            try{
                sleep(waitTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            //Le operazioni condivise vanno messe dentro questa sezione
            synchronized (shm) {

                if(shm.getTurn() == index) {
                    
                    position += ((rand.nextInt(10)) + 1);
                    System.out.println(tName + " advancing -> pos:\t" + position);
                    if(position >= 100) {
                        System.out.println(tName + " wins!");
                        //E' importante notare che ogni oggetto in Java ha un monitor
                        shm.setTurn(-1);
                        shm.notify();
                        break;
                    }

                    shm.setTurn(1 - index);
                    shm.notify();
                    
                    try {
                        shm.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if(shm.getTurn() == -1) {
                    break;
                }

            }

        }

    }

}


public class Main {

    public static void main(String[] args) {


        Shared shared = new Shared();
        MyThread t0 = new MyThread(shared, 0);
        MyThread t1 = new MyThread(shared, 1);

        t0.start();
        t1.start();

        try{
            t0.join();
            t1.join();
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("Game Over!");

    }
    
}