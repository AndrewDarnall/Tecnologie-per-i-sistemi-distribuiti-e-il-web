import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Calcolatore", urlPatterns =
{
    "/Calcolatore"
})

public class Calcolatore extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        double x, y, z;
        char operazione;
        String o1, o2, risultato;
        
        o1 = request.getParameter("operando1");
        o2 = request.getParameter("operando2");
        
        try
        {
            x = Double.parseDouble(o1);
            y = Double.parseDouble(o2);
            
            operazione = request.getParameter("operazione").charAt(0);
            
            switch (operazione)
            {
                case '+':
                    z = x + y;
                    break;
                case '-':
                    z = x - y;
                    break;
                case '*':
                    z = x * y;
                    break;
                case '/':
                    z = x / y;
                    break;
                case '%':
                    z = x % y;
                    break;
                case '^':
                    z = Math.pow(x, y);
                    break;
                default:
                        z = 0;
            }        
            
            risultato ="<p>Risultato: "+z+"</p>";
        }
        catch(NumberFormatException e)
        {
            risultato = "<p>Formato degli operandi non corretto!</p>";
        }
            
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Calcolatore</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1>Servlet Calcolatore</h1>");
            out.println(risultato);
            out.println("<a href='.'>Torna indietro</a>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
        finally
        {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "Esempio utilizzo Servlet Java";
    }
}