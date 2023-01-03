import java.io.*;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Completed extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = null;

        try {
            out = response.getWriter();
            response.setContentType("text/html");

            out.println("<html> <head> <title> Completed Page </title> <style> * { text-align: center; } input { width: 100%; } </style> </head> <body> <h1> Movie Succesfully added to Wish List! </h1> ");
            out.println("<form action=\"./showHome\" method=\"GET\"> <input type=\"submit\" vaue=\"Home\"> </form> </body> </html>");


        } catch( Exception e) {
            System.err.println("Caught exception: " + e);
            e.printStackTrace();
        }

    }

}