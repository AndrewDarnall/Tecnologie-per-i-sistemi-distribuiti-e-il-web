import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author shin
 */
@WebServlet(name = "Write", urlPatterns =
{
    "/Write"
})
public class Write extends HttpServlet
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/EsercizioWebService_Server/Guestbook.wsdl")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String user, message, redirect = "", output = "";
        
        user = request.getParameter("user");
        message = request.getParameter("message");
        
        output += "<hr /><br>";
        redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"3; URL=index.jsp\">";
        
        if (user.equals("") || message.equals(""))
        {
            output += "<p>Parametri non corretti!</p>"
                    + "<p>Redirect in corso...</p>";
        }
        else
        {
            output += "<p>Messaggio registrato!</p>"
                    + "<p>Redirect in corso...</p>";
            try
            {
                putMessage(user, message);
            }
            catch(Exception e)
            {
                output+=e;
            }
        }
        
        output += "<hr /><br>";
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Operation</title>"); 
            out.println(redirect);
            out.println("</head>");
            out.println("<body>\n<center>");
            out.println("<h1>Writer</h1>");
            out.println(output);
            out.println("</center>\n</body>");
            out.println("</html>");
        }
        finally
        {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        }
        catch (Exception e) {}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        }
        catch (Exception e) {}
    }

    @Override
    public String getServletInfo()
    {
        return "Scrive il messaggio.";
    }// </editor-fold>
    
    public void putMessage(String user, String message) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("guestbook.csv", true));
        
        writer.append(user+","+message+"\n");
        
        writer.close();
    }
}