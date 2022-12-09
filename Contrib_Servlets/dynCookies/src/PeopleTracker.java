import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.CookieManager;
import java.util.*;

public class PeopleTracker extends HttpServlet {

    private int clicks = 0;
    private String click = "0";

    public void incrementClicks() {
        clicks += 1;
    }

    public int getClicks() {
        return clicks;
    }

    public String getStringClick() {
        incrementClicks();
        click = "" + getClicks();
        return click;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String clicked = request.getParameter("button");

        Cookie cookie = new Cookie("connection_" + getStringClick(),getStringClick());

        response.addCookie(cookie);

        out.println("<p>You clicked me</p>");
        out.println("<a href=\"./index.jsp\">Back to main page");

        out.close();

    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Cookie cookies[] = request.getCookies();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(cookies != null && cookies.length != 0) {
            //out.println(cookies[0].getValue());
            int va = cookies.length;
            out.println(va);
        } else {
            out.println("<p>This is the first time the user visits the website!</p>");
        }

        out.close();

    }

}
/**
 * 
 * I am fully aware that the exception handling is horrible
 * but this is a toy example
 * 
 */