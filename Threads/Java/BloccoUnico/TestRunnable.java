/* TestRunnable.java */

public class TestRunnable {
	public static void main(String[] args) {
		ThreadedInt tn1, tn2;
		Thread th1, th2;

		tn1 = new ThreadedInt(    "Thread 1");
		th1 = new Thread(tn1);
		th1.start();

		tn2 = new ThreadedInt("\t\t\t\tThread 2");
		th2 = new Thread(tn2);
		th2.start();

		try {
			th1.join();
			th2.join();
			System.out.println("\n" + th1.toString() + " " + th2
			                   + " terminated");
		}
		catch (InterruptedException e) { 
		}
	}
}
