import java.io.*;
import java.util.concurrent.locks.*;
import java.util.*;

class Tdata {
	private final ReentrantLock mux = new ReentrantLock();
	private int sample = 50;

	public void lock() { mux.lock(); }
	public void unlck() { mux.unlock(); }
	public int getS() { return sample; }
	public void setS(int s) { sample = s; }
}

class MT extends Thread {
	private Tdata d;

	public MT(Tdata data) {
		this.d = data;
	}

	public void run() {
		Random rnd = new Random();
		long tid = Thread.currentThread().getId();

		while (true) {
			d.lock();

			try {
				int x = rnd.nextInt(81) + 10;

				if (x == d.getS())
					break;

				System.out.println("Thread "+tid+", sample era "+d.getS()+" ora è "+x);
				d.setS(x);

			} catch (Exception e) {
				System.out.println("exp " + e.getMessage());

			} finally {
				d.unlck();
			}
		}

		System.out.println("Thread "+tid+" done");
	}
}

public class Main {
	public static void main(String args[]) {
		Tdata data = new Tdata();
		List<Thread> thds = new ArrayList() {{
			add(new MT(data));
			add(new MT(data));
			add(new MT(data));
		}};

		for (Thread t: thds)
			t.start();

		try {
			for (Thread t: thds)
				t.join();

		} catch (Exception e) {
			System.out.println("I'm alone :(");
		}

		System.out.println("The end: "+data.getS());
	}
}
