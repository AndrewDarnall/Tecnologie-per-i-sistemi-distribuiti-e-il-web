import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class Greeter extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html><head><title>Maven Servlet Response</title></head><body>");
        out.println("<h1> The Maven HTTP Servlet says HELLO!</h1></body></html>");

    }

}

/**
 * Don't forget to add the proper servlet-api.jar dependency in the pom.xml
 */