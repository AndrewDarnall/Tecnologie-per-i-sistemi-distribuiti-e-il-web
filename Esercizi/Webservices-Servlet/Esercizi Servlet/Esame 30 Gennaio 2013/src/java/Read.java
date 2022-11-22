import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import java.sql.*;

/**
 *
 * @author shin
 */
@WebServlet(name = "Read", urlPatterns =
{
    "/Read"
})
public class Read extends HttpServlet
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/EsercizioWebService_Server/Guestbook.wsdl")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String user, n, redirect = "", output = "";
        int num = 0;
        boolean ok = true;
        
        user = request.getParameter("user");
        n = request.getParameter("n");
        
        output += "<hr /><br>";
        
        try
        {
            num = Integer.parseInt(n);
        }
        catch (NumberFormatException e)
        {
            ok = false;
        }
        
        if (user.equals("") || n.equals("") || !ok || num == 0)
        {
            redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"3; URL=index.jsp\">";
            output += "<p>Parametri non corretti!</p>"
                    + "<p>Redirect in corso...</p>";
        }
        else
        {
            try
            {
                output += "<p>Messaggio #<b>"+n+"</b> dell'utente <b>"+user+"</b>: <i>"+getMyLatestPosts(user, num)+"</i></p>";
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
            out.println("<h1>Reader</h1>");
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
        catch(Exception e)  {}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        }
        catch(Exception e)  {}
    }

    @Override
    public String getServletInfo()
    {
        return "Legge l'n-esimo messaggio.";
    }// </editor-fold>
    
    public String getMyLatestPosts(String user, int n) throws ClassNotFoundException, SQLException
    {
        String dir = ".";
        String query = "SELECT MESSAGE FROM guestbook WHERE USER = '"+user+"'";
        String nMessage = "vuoto";
        
        Class.forName("org.relique.jdbc.csv.CsvDriver");
                
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:"+dir);

        Statement stmt = conn.createStatement();

        ResultSet results = stmt.executeQuery(query);
                
        int count = 0;
        
        while (results.next())
        {
            count++;
            nMessage = results.getString("MESSAGE");
            if (count == n)
                break;
        }
        
        if (count != n)
            nMessage = "vuoto";
        
        return nMessage;
    }
}