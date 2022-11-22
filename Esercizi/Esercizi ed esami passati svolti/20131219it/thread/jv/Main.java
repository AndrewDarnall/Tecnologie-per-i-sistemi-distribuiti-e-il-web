import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

class Tdata {
	private final ReentrantLock mux = new ReentrantLock();
	private final Condition p1Turn = mux.newCondition();
	private final Condition p2Turn = mux.newCondition();
	private int m;

	public Tdata() {
		Random rnd = new Random();

		this.m = rnd.nextInt(10) + 1;
	}

	public int getM() { return this.m; }
	public void setM(int m) { this.m = m; }
	public void lock() { mux.lock(); }
	public void unlck() { mux.unlock(); }
	public void waitP1() throws Exception { p1Turn.await(); }
	public void waitP2() throws Exception { p2Turn.await(); }
	public void wakeP1() throws Exception { p2Turn.signal(); }
	public void wakeP2() throws Exception { p1Turn.signal(); }
}

class MTP1 extends Thread {
	private Tdata d;

	public MTP1(Tdata data) {
		this.d = data;
	}

	public void run() {
		Random rnd = new Random();

		while(true) {
			d.lock();

			try {
				if (d.getM() < 6) {
					try {
						System.out.println("Waaaake P2 "+d.getM());
						d.wakeP2();
						d.setM(rnd.nextInt(10) + 1);
						System.out.println("P1 new m: "+d.getM());

					} catch (Exception e) {
						System.out.println("Shhh! P2 is sleeping ;( "+d.getM());
						break;
					}
				} else {
					try {
						System.out.println("ZzZzZzZz - P1 "+d.getM());
						d.waitP2();

					} catch (Exception e) {
						System.out.println("I don't want to wait for P2 <.<");
						break;
					}
				}
			} finally {
				d.unlck();
			}
		}
	}
}

class MTP2 extends Thread {
	Tdata d;

	public MTP2(Tdata data) {
		this.d = data;
	}

	public void run() {
		Random rnd = new Random();

		while(true) {
			d.lock();

			try {
				if (d.getM() >= 6) {
					try {
						System.out.println("Waaaake P1 "+d.getM());
						d.wakeP1();
						d.setM(rnd.nextInt(10) + 1);
						System.out.println("P2 new m: "+d.getM());

					} catch (Exception e) {
						System.out.println("Shhh! P1 is sleeping ;(");
						break;
					}
				} else {
					try {
						System.out.println("ZzZzZzZz - P2 "+d.getM());
						d.waitP1();

					} catch (Exception e) {
						System.out.println("I don't want to wait for P1 <.<");
						break;
					}
				}
			} finally {
				d.unlck();
			}
		}
	}
}

public class Main {
	public static void main(String args[]) {
		Tdata data = new Tdata();
		List<Thread> thdList = new ArrayList() {{
			add(new MTP1(data));
			add(new MTP2(data));
		}};

		for (Thread t: thdList)
			t.start();

		try {
			for (Thread t: thdList)
				t.join();

		} catch (Exception e) {
			System.out.println("Wanna join? No thanks!");
		}
	}
}
