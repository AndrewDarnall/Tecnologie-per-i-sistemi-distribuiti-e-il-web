import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.rowset.serial.SerialException;

public class StaticPage extends HttpServlet {
 
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Sample Servlet </title>");
        out.println("<style> h1 {text-align: center; border: solid 1px rgba(0,0,0,0.5);} </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div id = \"main\" style = \"background-color: blue;\">");
        out.println("<h1>Hello and welcome to Java Servlets!</h1>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }   

}

/**
 * 
 * La servlet serve solamente ad inviare codice HTML statico
 * 
 * accertarsi di avere avviato Apache Tomcat prima di far partire la
 * servlet
 * 
 * NOTICE: NON includere il codice sorgente nella directory di Tomcat
 *         e' assolutamente una BAD PRACTICE includere il sorgente nel
 *         server!
 * 
 * --> Se NON si ha familiarita' con HTML, consultare i link nel branch: main
 * 
 * per compilare:
 * 
 * javac -cp /path-che-va-a-tomcat/lib/servlet-api.jar StaticPage.java
 * 
 * e mettere il bytecode creato nella directory 'classes' dentro WEB-INF
 * 
 */