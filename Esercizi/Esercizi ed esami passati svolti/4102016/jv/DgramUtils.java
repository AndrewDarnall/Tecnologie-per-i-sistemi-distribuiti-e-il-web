import java.io.*;
import java.net.*;

public class DgramUtils {
	public DatagramPacket buildPkg(InetAddress addr, int port, String mdata) {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		DataOutputStream ds = new DataOutputStream(bs);
		byte[] buf = null;

		try {
			ds.writeUTF(mdata);

			buf = bs.toByteArray();

		} catch (IOException e) {
			System.out.println("DgramUtils err " + e.getMessage());
		}

		return new DatagramPacket(buf, buf.length, addr, port);
	}

	public String getCont(DatagramPacket pkg) {
		DataInputStream di = new DataInputStream(
			new ByteArrayInputStream(pkg.getData(), 0, pkg.getData().length)
		);
		String ret = "";

		try {
			ret = di.readUTF();

		} catch (EOFException e) {
			System.out.println("DgramUtils err " + e.getMessage());

		} catch (IOException e) {
			System.out.println("DgramUtils err " + e.getMessage());
		}

		return ret;
	}
}
