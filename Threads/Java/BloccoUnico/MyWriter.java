/* MyWriter.java */

public class MyWriter extends Thread {

	String threadName;
	int delay;
	SharedArea sharedA;  // NB: analogia con var globali dei thread C
						 // e` un riferimento a un oggetto condiviso
						 // passato dal costruttore (vedere main())

	public MyWriter(String name,  int delay, SharedArea shrd) {
		super();
		threadName = name;
		this.delay = delay;
		sharedA = shrd;
	}

	public void run() {
		for (int i = 0; i < 6; i++) {
			sharedA.writeBuffer(threadName + " al ciclo: " + i);
			try {
				sleep(delay);
			} catch (InterruptedException e) {}
		}
	}
}
