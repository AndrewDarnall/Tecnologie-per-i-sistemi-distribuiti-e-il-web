/* SharedArea.java */

import java.util.*;

public class SharedArea {
	String sharedBuffer;

	public synchronized void writeBuffer(String msg) {

		// prova poi a eliminare synchronized...
		// public void writeBuffer(String msg) {

		sharedBuffer = // riinizializza buffer
				(new Date(System.currentTimeMillis())).toString();
		sharedBuffer = sharedBuffer + ": " + msg;
		System.out.println(sharedBuffer);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
}
