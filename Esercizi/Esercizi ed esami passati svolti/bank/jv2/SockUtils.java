import java.io.*;
import java.net.*;

public class SockUtils {
	public BufferedReader getReader(Socket s) throws Exception {
		return new BufferedReader(
				new InputStreamReader(s.getInputStream())
			);
	}

	public PrintWriter getWriter(Socket s) throws Exception {
		return new PrintWriter(
				new OutputStreamWriter(s.getOutputStream()),
				true
			);
	}
}
