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

class A extends Thread {
	Tdata td;
	int c;

	public A(Tdata t) {
		td = t;
		c = 0;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep((int)(Math.random()*10+1));

			} catch (Exception e) {
				System.out.println("B sleep err "+e.getMessage());
			}

			synchronized (td) {
				td.incX();
				++c;

				if (td.getX() > 300)
					break;
			}
		}

		System.out.println("A done: "+c);
	}
}

class B extends Thread {
	Tdata td;
	int c;

	public B(Tdata t) {
		td = t;
		c = 0;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep((int)(Math.random()*10+1));

			} catch (Exception e) {
				System.out.println("B sleep err "+e.getMessage());
			}

			synchronized (td) {
				td.incX();
				++c;

				if (td.getX() > 300)
					break;
			}
		}

		System.out.println("B done: "+c);
	}
}

public class Main {
	public static void main(String[] args) {
		Tdata td = new Tdata();

		Thread[] thds = new Thread[] {
			new A(td),
			new B(td)
		};

		for (Thread t: thds)
			t.start();

		try {
			for (Thread t: thds)
				t.join();

		} catch (Exception e) {
			System.out.println("no join "+e.getMessage());
		}
	}
}
