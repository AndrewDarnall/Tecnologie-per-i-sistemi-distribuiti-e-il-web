import java.io.*;

class Tdata {
	int n;

	public Tdata() {
		n = 0;
	}

	public int getN() {
		return n;
	}

	public void incN(int x) {
		this.n += x;
	}
}

class Te extends Thread {
	private Tdata td;
	int run = 0;

	public Te(Tdata t) {
		td = t;
	}

	public void run() {
		try {
			while (true) {
				int x;

				Thread.sleep(200);
				while ( (x = (int)(Math.random()*1000+1)) % 2 != 0);

				synchronized(td) {
					td.incN(x);

	System.out.println("te run "+run+" n "+td.getN());
					if (run == 10 && td.getN() % 2 == 0)
						return;
				}
				if (run == 1000)
					return;

				++run;
			}
 
		} catch (Exception e) {
			System.out.println("can i sleep!?!? "+e.getMessage());
		}
	}
}

class To extends Thread {
	private Tdata td;
	int run = 0;

	public To(Tdata t) {
		td = t;
	}

	public void run() {
		try {
			while (true) {
				int x;

				Thread.sleep(200);
				while ( (x = (int)(Math.random()*1000+1)) % 2 == 0);

				synchronized(td) {
					td.incN(x);

	System.out.println("to run "+run+" n "+td.getN());
					if (run == 10 && td.getN() % 2 != 0)
						return;
				}

				if (run == 1000)
					return;

				++run;
			}
 
		} catch (Exception e) {
			System.out.println("can i sleep!?!? "+e.getMessage());
		}
	}
}


public class Main {
	public static void main(String[] args) {
		Tdata td = new Tdata();
		Thread[] thds = new Thread[] {new To(td), new Te(td)};

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
