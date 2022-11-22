import java.io.*;

class Tdata {
	int sample;

	public Tdata() {
		sample = 50;
	}

	public int getSample() {
		return sample;
	}

	public void setSample(int s) {
		sample = s;
	}
}

class MThd extends Thread {
	Tdata td;

	public MThd(Tdata t) {
		td = t;
	}

	public void run() {
		while (true) {
			int x = (int)(Math.random()*80+10);

			synchronized (td) {
				if (x == td.getSample()) {
					System.out.println(Thread.currentThread().getName()+" done :D ");
					return;
				}

				System.out.println(Thread.currentThread().getName()+": old="+td.getSample()+" new="+x);
				td.setSample(x);
			}
		}
	}
}

public class Main {
	public static void main(String[] args) {
		Tdata td = new Tdata();
		Thread[] thds = new Thread[] {new MThd(td), new MThd(td), new MThd(td)};

		for (Thread t: thds)
			t.start();

		try {
			for (Thread t: thds)
				t.join();

		} catch (Exception e) {
			System.out.println("no join");
		}

		System.out.println("main done - "+td.getSample());
	}
}
