import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class regExam extends HttpServlet {
	private void saveToFile(HttpServletRequest req) throws IOException {
		// Def path tomcat/bin :/
		BufferedWriter bw = new BufferedWriter(new FileWriter("../webapps/esami-tpcd/exams.txt", true));

		bw.append(req.getParameter("name")+" "+req.getParameter("surnm")+
			" "+req.getParameter("id")+" "+req.getParameter("vote")+"\n"
		);

		bw.close();
	}

	private int getMedia(String id) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("../webapps/esami-tpcd/exams.txt"));
		int ret = 0, cnt = 0;
		String line;

		while ((line = br.readLine()) != null) {
			if (line.contains(id)) {
				ret += Integer.parseInt(line.split(" ")[3]);

				++cnt;
			}
		}

		br.close();
		return ret/cnt;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();

		this.saveToFile(req);
		res.setContentType("text/plain");
		out.println("Success.\nMedia: "+getMedia(req.getParameter("id")));
	}
}
