import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class FakeFlix extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = null;

        try {
            out = response.getWriter();
            response.setContentType("text/html");

            out.println("<html><head><title> FakeFlix </title> </head> <body>");
            out.println("<div id=\"main\"> <h1 style=\"text-align: center;\"> Welcome to FakeFlix </h1> </div>");
            out.println("</body></html>");

            try{
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
/**
 * Note, the manual deployment in Apache Tomcat works falwlessly, even with Docker
 * however using the Maven project ... problems happen, possibly due to the nature
 * of the directory structure --> CONFIRMED! ~ This could be a problem
 * 
 * Jetty works fine just remember!! --> localhost:8080/test/showHome and NOT localhost:8080/FakeNetflix/showHome
 * Jetty is much simpler!
 * 
 */