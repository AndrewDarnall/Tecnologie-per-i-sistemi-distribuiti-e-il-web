import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class JetGreeter extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html><head><title>Jetty Servlet</title></head><body>");
        out.println("<h1> Jetty says HELLO !</h1></body></html>");

    }

}
/**
 * 
 * Maven compilation errors will result in build failures
 * 
 */