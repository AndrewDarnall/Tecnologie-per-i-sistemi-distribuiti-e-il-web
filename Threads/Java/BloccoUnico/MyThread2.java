public class MyThread2 extends Thread {
	String threadName;					// differs from SimpleThread
    public MyThread2(String str) {	// different initialization
        super();
        threadName = str;
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(threadName + "@ iter: " + i);
            try {  // sleep() may throw InterruptedException
                sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) {}
        }
        System.out.println("DONE! " + threadName);
    }
}
