import java.io.*;

class Tdata {
	int x;

	public Tdata() {
		x = 0;
	}

	public int getX() {
		return x;
	}

	public void incX() {
		++x;
	}
}

class Mthd extends Thread {
	Tdata td;
	int hit;

	public Mthd(Tdata t) {
		td = t;
		hit = 0;
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep((int)(Math.random()*10+1));

				synchronized (td) {
					if (td.getX() > 500) {
						System.out.println("hit: "+hit);
						return;
					}

					td.incX();
					++hit;
				}
			}

		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName()+" err "+e.getMessage());
		}
	}
}

public class Main {
	public static void main(String[] args) {
		Tdata td = new Tdata();
		Thread[] thds = new Thread[] {new Mthd(td), new Mthd(td)};

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
