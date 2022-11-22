package client;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import client.*;

public class Reverse extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        String casa=request.getParameter("casa");
        try (PrintWriter out = response.getWriter()) 
        {
            out.println("<html>\n" +
"    <head>\n" +
"        <title>Reverse</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"        <style>\n" +
"            .ciao\n" +
"            {\n" +
"                position: fixed;\n" +
"                top: 0px;\n" +
"                left: 0px;\n" +
"                width:100%;\n" +
"                height:100px;\n" +
"                line-height: 85px;\n" +
"                background-color:#00BFFF;\n" +
"                z-index: 1;\n" +
"            }\n" +
"        </style>\n" +
"    </head>\n" +
"    <body>\n" +
"        <div class=\"ciao\" align=\"center\"> <a href=\"http://localhost:8084/WBCReverse/\" style=\"text-decoration:none\"> <font face=\"Comic Sans MS\" size=\"7\" color=\"white\"> Reverse </font> </a> </div> <br> <br> <br>\n" +
"        <table style=\"margin:50px 0px 0px 550px\">\n" +
"            <tr>\n" +
"                <td> <font face=\"Comic Sans MS\" size=\"3\" color=\"black\">" + casa + "</font> <font face=\"Comic Sans MS\" size=\"3\" color=\"red\"> reverse → </font> <font face=\"Comic Sans MS\" size=\"3\" color=\"black\">" + reverse(casa) + " </font> </td>                                    \n" +
"            </tr>\n" +
"        </table>\n" +
"    </body>\n" +
"</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }
    
    private static String reverse(java.lang.String casa) 
    {
        server.ServerReverse_Service service = new server.ServerReverse_Service();
        server.ServerReverse port = service.getServerReversePort();
        return port.reverse(casa);
    }
}
