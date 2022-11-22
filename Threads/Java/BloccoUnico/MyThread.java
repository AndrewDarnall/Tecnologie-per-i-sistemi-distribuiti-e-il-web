/* MyThread.java */

public class MyThread extends Thread {
    public MyThread(String str) {
        super(str);
//		commentando super(str), prende un nome di default        
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + "@ iter: " + i);
            try {  // sleep() may throw InterruptedException
                sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) { }
        }
        System.out.println(getName() + " DONE!");
    }
}
