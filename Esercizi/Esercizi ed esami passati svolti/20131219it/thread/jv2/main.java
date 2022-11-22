import java.io.*;
import java.util.*;

class Tdata { // and sync obj
	private int m;

	Tdata() {
		m = (int)Math.random()*10+1;
	}

	public void setM(int x) {
		m = x;
	}

	public int getM() {
		return m;
	}
}

class p1 extends Thread {
	private Tdata td;

	public p1(Tdata t) {
		td = t;
	}

	public void run() {
		while (true) {
			synchronized(td) {
				try {
					System.out.println(td.getM());
					if (td.getM() < 6) {
						td.setM((int)(Math.random()*10+1));
						System.out.println(td.getM());
						td.notifyAll();

					} else {
						td.wait();
					}

				} catch (Exception e) {}
			}
		}
	}
}

class p2 extends Thread {
	private Tdata td;

	public p2(Tdata t) {
		td = t;
	}

	public void run() {
		while (true) {
			synchronized (td) {
				try {
					if (td.getM() > 5) {
						td.setM((int)(Math.random()*10+1));
						System.out.println("\t"+td.getM());
						td.notifyAll();

					} else {
						td.wait();
					}

				} catch (Exception e) {}
			}
		}
	}
}

public class main {
	public static void main(String[] args) {
		Tdata td = new Tdata();
		List<Thread> thds = new ArrayList<Thread>() {{
			add(new p1(td));
			add(new p2(td));
		}};

		for (Thread t: thds)
			t.start();

		try {
			for (Thread t: thds)
				t.join();

		} catch (Exception e) {
			System.out.println("no join");
		}
	}
}
