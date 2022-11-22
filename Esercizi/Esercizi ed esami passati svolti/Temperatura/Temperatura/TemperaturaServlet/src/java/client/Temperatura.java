package client;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import client.*;

@WebServlet(name = "Temperatura", urlPatterns = {"/Temperatura"})
public class Temperatura extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            String city=request.getParameter("city");
            
            out.println("<html>\n" +
"    <head>\n" +
"        <title>Temperatura</title>\n" +
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
"            .ciao2\n" +
"            {\n" +
"                position: fixed;\n" +
"                top: 13px;\n" +
"                left: -10px;\n" +
"                width:100%;\n" +
"                height:100px;\n" +
"                z-index: 1;\n" +
"            }\n" +
"        </style>\n" +
"    </head>\n" +
"    <body>\n" +
"        <div class=\"ciao\" align=\"center\"> <a href=\"http://localhost:8084/TemperaturaServlet/\" style=\"text-decoration:none;\"> <font face=\"Comic Sans MS\" size=\"7\" color=\"white\"> Temperatura </font> </a> </div> <br> <br> <br>\n" +
"        <table style=\"margin:50px 0px 0px 550px\">\n" +
"            <tr>\n" +
"                <td> <font face=\"Comic Sans MS\" size=\"5\" color=\"black\"> ");
            if("Tutte".equals(city))
            {
                String[] c=getAllTemperature().toArray(new String[8]);
                for(int i=0; i<=7; i++)
                {
                    out.println(c[i] + "<br>");
                }
            }
            else
            {
                out.println(city + ": " +getTemperature(city) + "°C");
            }
            out.println("</font> </td>\n" +
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

    private static java.util.List<java.lang.String> getAllTemperature() 
    {
        client.TemperaturaService_Service service = new client.TemperaturaService_Service();
        client.TemperaturaService port = service.getTemperaturaServicePort();
        return port.getAllTemperature();
    }

    private static double getTemperature(java.lang.String city) 
    {
        client.TemperaturaService_Service service = new client.TemperaturaService_Service();
        client.TemperaturaService port = service.getTemperaturaServicePort();
        return port.getTemperature(city);
    }

}
