import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class calculator extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		Integer n1 = Integer.parseInt(req.getParameter("n1")),
			n2 = Integer.parseInt(req.getParameter("n2"));
		int ret = n1;

		switch (req.getParameter("op")) {
			case "+": ret += n2; break;
			case "-": ret -= n2; break;
			case "/": ret /= n2; break;
			case "*": ret *= n2; break;
		}

		res.setContentType("text/plain");
		out.println("Result: "+ret);
	}
}
