// Copiata da qualcuno, ripulita ed adattata al caso specifico

import java.net.*;
import java.io.*;

public class DgramUtils {
    public DatagramPacket buildPkg(InetAddress addr, int port, int mdata) {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        DataOutputStream dstream = new DataOutputStream(bstream);
        byte[] data = null;

		try {
	        dstream.writeInt(mdata);

			data = bstream.toByteArray();

		} catch (IOException e) {
			System.out.println("DgramUtils err: " + e.getMessage());
		}

        return new DatagramPacket(data, data.length, addr, port);
    }

    public int getCont(DatagramPacket pkg) {
        DataInputStream dstream = new DataInputStream(
            new ByteArrayInputStream(pkg.getData(), 0, pkg.getData().length)
        );
        int r = -1;

        try {
            r = dstream.readInt();

        } catch (EOFException e) {
            System.out.println("getCont err: "+e.getMessage());

        } catch (IOException e) {
            System.out.println("getCont err: "+e.getMessage());
        }

        return r;
    }
}

