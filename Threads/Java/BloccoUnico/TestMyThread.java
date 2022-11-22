/* TestMyThread.java */

public class TestMyThread {
	public static void main(String[] args) {
		MyThread th1 = new MyThread(    "Thread 1");     // refs th1, th2
		MyThread th2 = new MyThread("\t\t\t\tThread 2"); // for future use
		th1.start();
		th2.start();
		try {	// join() may throw InterruptedException
			th1.join();
			th2.join();
			System.out.println("main: Thread terminati\n");
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println("main: Thread interrotto\n");
		}
	}
}
