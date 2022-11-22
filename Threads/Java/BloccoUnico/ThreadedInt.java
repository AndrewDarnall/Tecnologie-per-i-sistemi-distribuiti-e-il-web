/* ThreadedInt.java */

/* Se si vuole eseguire un'attivita` come thread, questa dev'essere   *
 * run() di una classe, p.es. ThreadedInt, che extends Thread;        *
 * ma cio` impedira` che ThreadedInt sia poi derivata da una classe,  *
 * p.es. MyInt, perche' ThreadedInt non puo` estendere due classi.    *
 *                                                                    *
 * Alternativa (vedi anche TestRunnable.java):                        *
 *	- ThreadedInt extends MyInt implements Runnable (interfaccia)     *
 *	- Runnable e` un'interfaccia di sistema, con il solo metodo run() *
 *	- quindi ThreadedInt deve implementare run()                      *
 *	- per un oggetto, ThreadedInt tn, non si puo` chiamare start()    *
 *	- ma, il costruttore Thread accetta un argomento Runnable come tn:*
 *		Thread th = new Thread(tn);                                   *
 *	- e, chiamare start() per th, avvia proprio run() di tn           *
 *                                                                    *
 * In sostanza, la strategia e` di abbinare:                          *
 *	- a un oggetto threaded che implementi Runnable                   *
 *	- un vero thread                                                  *
 *                                                                    *
 * NB: anche la classe Thread implements l'interfaccia Runnable,      *
 *     infatti ha il metodo run()                                     *
 */

public class ThreadedInt extends MyInt
	implements Runnable {
	String threadName;
	public ThreadedInt(String name) {
		super((int) (Math.random() * 1000));
		threadName = name;
	}
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(threadName + "@"
			                   + i + "->" +getVal() );
			try {
				Thread.sleep(100);
				/* Note method sleep() is in Thread; "Thread." is	*
				 *  needed, because MyRunnable is no Thread!		*/
			}
			catch (InterruptedException e) { }
		}
	}
}

class MyInt {
	int val;
	MyInt(int initVal) {
		val = initVal;
	}
	int getVal() {
		return val;
	}
}
