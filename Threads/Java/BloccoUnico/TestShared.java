/* TestShared.java */

public class TestShared {

	static SharedArea shrd = new SharedArea();  // oppure var locale

	public static void main(String[] args) {
//		SharedArea Shrd = new SharedArea();

		MyWriter th1 = new MyWriter(    "Thread", 100, shrd);
		MyWriter th2 = new MyWriter("\t\t\t\t Thread lento", 2500, shrd);
		th1.start();
		th2.start();
		try {
			th1.join();
			th2.join();
			System.out.println("Thread terminati\n");
		}
		catch (InterruptedException e) { }
	}
}
