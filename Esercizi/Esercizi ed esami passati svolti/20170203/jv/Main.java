import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

class Tdata {
	private final ReentrantLock mux = new ReentrantLock();
	private int n = 0;

	public void lock() { mux.lock(); }
	public void unlck() { mux.unlock(); }
	public void sumN(int n) { this.n += n; }
	public int getN() { return n; }
}

class To extends Thread {
	private Tdata d;
	int c = 0;

	public To(Tdata data) {
		this.d = data;
	}

	public void run() {
		Random rnd = new Random();

		while (true) {
			++c;

			try {
				int x;

				Thread.sleep(200);

				while ((x = rnd.nextInt(50) + 1) % 2 != 0);

				d.lock();

				try {
					d.sumN(x);

				} catch (Exception e) {
					System.out.println("run excp " + e.getMessage());

				} finally {
					d.unlck();

					if ((c >= 10 && d.getN() % 2 == 0) || c >= 1000) {
						System.out.println("Thread 1 done, "+d.getN());
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("run excp " + e.getMessage());
			}
		}
	}
}

class Te extends Thread {
	private Tdata d;
	int c = 0;

	public Te(Tdata data) {
		this.d = data;
	}

	public void run() {
		Random rnd = new Random();

		while (true) {
			++c;

			try {
				int x;

				Thread.sleep(200);

				while ((x = rnd.nextInt(50) + 1) % 2 == 0);

				d.lock();

				try {
					d.sumN(x);

				} catch (Exception e) {
					System.out.println("run excp " + e.getMessage());

				} finally {
					d.unlck();

					if ((c >= 10 && d.getN() % 2 != 0) || c >= 1000) {
						System.out.println("Thread 2 done, "+d.getN());
						break;
					}
						
				}
			} catch (Exception e) {
				System.out.println("run excp " + e.getMessage());
			}
		}
	}
}

public class Main {
	public static void main(String args[]) {
		Tdata data = new Tdata();

		List<Thread> thds = new ArrayList() {{
			add(new To(data));
			add(new Te(data));
		}};

		for (Thread t: thds)
			t.start();

		try {
			for (Thread t: thds)
				t.join();

		} catch (Exception e) {
			System.out.println("No join here");
		}
	}
}
