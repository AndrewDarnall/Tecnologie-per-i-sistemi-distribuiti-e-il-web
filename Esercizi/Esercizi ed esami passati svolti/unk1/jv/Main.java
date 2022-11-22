import java.io.*;
import java.util.concurrent.locks.*;
import java.util.*;

class Tdata {
	private final ReentrantLock mux = new ReentrantLock();
	private int x = 0;

	public void lock() { mux.lock(); }
	public void unlck() { mux.unlock(); }
	public int getX() { return x; }
	public void incX() { ++this.x; }
}

class MT extends Thread {
	private int hit = 0;
	private Tdata d;

	public MT(Tdata data) {
		this.d = data;
	}

	public void run() {
		Random rnd = new Random();

		while (d.getX() < 500) {
			try {
				Thread.sleep(rnd.nextInt(5) + 1);
				d.lock();

				try {
					d.incX();
					++hit;

				} finally {
					d.unlck();
				}
			} catch (Exception e) {
				System.out.println("run excp " + e.getMessage());
			}
		}

		System.out.println("Thread "+Thread.currentThread().getId()+" hits: "+hit);
	}
}

public class Main {
	public static void main(String args[]) {
		Tdata data = new Tdata();
		List<Thread> thds = new ArrayList() {{
			add(new MT(data));
			add(new MT(data));
		}};

		for (Thread t: thds)
			t.start();

		try {
			for (Thread t: thds)
				t.join();

		} catch (Exception e) {
			System.out.println("No join :(");
		}
	}
}
