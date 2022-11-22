import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;

class Item {
	private int id;
	private int n;

	public Item(int id, int n) {
		this.id = id;
		this.n = n;
	}

	public int getId() { return id; }
	public int getN() { return n; }
}

public class RMIServer implements RMIInt {
	private static List<Item> itmList = new ArrayList<Item>();

	private static void initItemsList() {
		Random rnd = new Random();

		for (int i=0; i<10; ++i)
			itmList.add(new Item(i, rnd.nextInt(40)));
	}

	private static void printMagazzino() {
		System.out.println("Magazzino");

		for (Item i: itmList)
			System.out.println(i.getId()+":"+i.getN());
	}

	public RMIServer() throws RemoteException {
		super();
	}

	public String isItemQtiAvail(String req) {
		String[] data = req.split(":");
		int id = Integer.parseInt(data[0]);
		int n = Integer.parseInt(data[1]);

		for (Item i: itmList) {
			if (i.getId() == id && i.getN() >= n)
				return "True";
		}

		return "False";
	}

	public static void main(String args[]) {
		try {
			RMIInt rmiServ = new RMIServer();
			RMIInt stub = (RMIInt) java.rmi.server.UnicastRemoteObject.exportObject(rmiServ, 0);
			Registry reg = LocateRegistry.createRegistry(1099);

			initItemsList();
			printMagazzino();
			reg.rebind("magazzinoServ", stub);

		} catch (Exception e) {
			System.out.println("RMIServer err " + e.getMessage());
			System.exit(1);
		}
	}
}
