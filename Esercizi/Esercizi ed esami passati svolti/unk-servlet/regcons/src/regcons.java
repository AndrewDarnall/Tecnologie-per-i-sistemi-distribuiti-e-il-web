import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class regcons extends HttpServlet {
	private final String docp1 = "<!DOCTYPE html><html><head><title>Regcons</title></head><body>";
	private final String docp3 = "</body></html>";
	private final String backBtn = "<form method=\"post\" action=\"./\">" +
					"<input type=\"submit\" value=\"Indietro\">" +
					"</form>";

	private String login(HttpServletRequest req) throws IOException {
		HttpSession sess = req.getSession();
		Object uname = sess.getAttribute("uname");
		Object utype = sess.getAttribute("ulev");
		String ret;

		if (uname == null) { // no session
			sess.setAttribute("uname", req.getParameter("cf"));
			sess.setAttribute("ulev", req.getParameter("utype"));

			uname = sess.getAttribute("uname");
			utype = sess.getAttribute("ulev");

		}

		ret = "Benvenuto " + uname + "<br><br>";

		if (uname.equals("")) {
			ret += "Matricola non inserita<br>" + backBtn;

		} else if (utype.equals("st")) {
			ret += "<form method=\"post\" action=\"./reg\">" +
			      "<label>Nome</label><br>" +
			      "<input type=\"text\" name=\"name\"><br>" +
			      "<label>Cognome</label><br>" +
			      "<input type=\"text\" name=\"surnm\"><br>" +
			      "<label>Voto</label><br>" +
			      "<input type=\"number\" name=\"vote\" min=\"0\">" +
			      "<input type=\"hidden\" name=\"id\" value=\""+uname+"\"><br>" +
			      "<input type=\"hidden\" name=\"cmd\" value=\"reg\">" +
			      "<input type=\"submit\" value=\"Submit\">" +
			      "</form><br>" + 
			      "<form method=\"post\" action=\"./media\">" +
			      "<input type=\"hidden\" name=\"id\" value=\""+uname+"\"><br>" +
			      "<input type=\"hidden\" name=\"cmd\" value=\"media\">" +
			      "<input type=\"submit\" value=\"Media\">" +
			      "</form><br>" +
			      backBtn;

		} else if (utype.equals("dc")) {
			BufferedReader br = new BufferedReader(new FileReader("../webapps/regcons/exams.txt"));
			String line;

			ret += "<p>";
			
			while ((line = br.readLine()) != null)
				ret += line + "<br>";

			ret += "</p>" + backBtn;

		} else {
			ret += "unknown command!<br>" + backBtn;
		}

		return ret;
	}

	private void register(HttpServletRequest req) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("../webapps/regcons/exams.txt", true));

		bw.append(req.getParameter("name")+" "+req.getParameter("surnm")+" "+
				req.getParameter("vote")+" "+req.getParameter("id")+"\n");

		bw.close();
	}

	private int getMedia(HttpServletRequest req) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("../webapps/regcons/exams.txt"));
		int ret = 0, cnt = 0;
		String id = req.getParameter("id");
		String line;

		while ((line = br.readLine()) != null) {
			if (line.contains(id)) {
				ret += Integer.parseInt(line.split(" ")[2]);

				++cnt;
			}
		}

		return ret/cnt;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		String cmd = req.getParameter("cmd");

		out.println(docp1);

		switch (cmd) {
			case "log": // standard login
				req.getSession().invalidate();
			case "slog": // session login
				out.println(this.login(req));
				break;
			case "reg":
				this.register(req);
				out.println("Success!");
				out.println(backBtn);
				break;
			case "media":
				out.println("media: " + this.getMedia(req));
				out.println(backBtn);
				break;
			default:
				break;
		}

		out.println(docp3);
	}
}
