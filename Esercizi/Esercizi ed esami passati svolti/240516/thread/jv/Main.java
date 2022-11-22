import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

class ThdData {
	private int x = 0;
	private final ReentrantLock mux = new ReentrantLock();

	public int getX() { return this.x; }
	public void incX() { ++this.x; }
	public void lock() { mux.lock(); }
	public void unlck() { mux.unlock(); }
}

class MThread extends Thread {
	private int cnt = 0;
	private ThdData d;

	public MThread(ThdData data) {
		this.d = data;
	}

	public void run() {
		Random rand = new Random();

		while (d.getX() <= 300) {
			d.lock();

			try {
				if (d.getX() <= 300) {
					d.incX();
					++cnt;
				}
			} finally {
				d.unlck();

				try {
					Thread.currentThread().sleep(rand.nextInt(6));

				} catch (InterruptedException e) {
					System.out.println("sleep ex " + e.getMessage());
					break;
				}
			}
		}

		System.out.println("thd " + Thread.currentThread().getId() + " done, cnt:" + cnt);
	}
}

public class Main {
	public static void main(String args[]) {
		ThdData tdata = new ThdData();
		List<MThread> thdList = new ArrayList() {{
			add(new MThread(tdata));
			add(new MThread(tdata));
		}};

		for (MThread t: thdList)
			t.start();

		try {
			for (MThread t: thdList)
				t.join();

		} catch (InterruptedException e) {
			System.out.println("thd err " + e.getMessage());
		}
	}
}
