
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class CookerBooker extends HttpServlet {
    

    // I am using a hashmap instead of connecting to a database
    private final Map books = new HashMap<>();
    
    private final String[] bookNames = {"C++","Java","Python","Haskell","Erlang","Brainfuck"};
    private final String[] isbns = {"01234567891","01234567892","01234567893","01234567894","01234567895","01234567896"};

    public void init() {
        for(int i = 0; i < 6; i++) {
            books.put(bookNames[i],isbns[i]);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String language = request.getParameter("language");
        String isbn = books.get(language).toString();

        // Created a cookie
        Cookie cookie = new Cookie(language,isbn);

        response.addCookie(cookie);
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Response page</title></head>");
        out.println("<body><div id=\"main\"> <p> " + language + " was selected! </p></div>");        
        out.println("</body>");
        out.println("<footer><a href=\"./index.html\">Go back to main page<br><a href=\"./form.html\">Go back to form</footer></html>");

        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie cookies[] = request.getCookies();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title> User Preferences </title></head><body>");
        out.println("<div id=\"main\">");
        if(cookies!= null && cookies.length != 0){
            out.println("<h1 style=\"text-align: center;\"> Recomendations </h1>");
            out.println("<p>");
        
            
            for(int i = 0; i < cookies.length; i++) {
                out.println(cookies[i].getName() + " ISBN:\t" + cookies[i].getValue() + "<br>");
                out.println("</p>");
            }
        } else {
            out.println("<h1 style=\"text-align: center;\"> There are no user recomendations as of now. </h1>");
            out.println("<p> Please go t the form and select a language!</p>");
        }
        out.println("</div></body>");
        out.println("<footer><a href=\"./index.html\">Back to main page</footer></html>");

        out.close();

    }


}

/**
 * 
 * I am fully aware that the source code must NOT be included
 * with the servlet however this is an accademic example
 * 
 * I am fully aware that the mecchanism could've been implemented 
 * differently
 * 
 */