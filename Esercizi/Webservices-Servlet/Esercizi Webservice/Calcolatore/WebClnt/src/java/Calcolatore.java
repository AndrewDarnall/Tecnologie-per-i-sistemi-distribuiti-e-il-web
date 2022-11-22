import calc.Calcolatore_Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet(name = "Calcolatore", urlPatterns =
{
    "/Calcolatore"
})

public class Calcolatore extends HttpServlet
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebService/Calcolatore.wsdl")
    private Calcolatore_Service service;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebService/Calcolatore.wsdl")
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
            
            z = calcola(operazione, x, y);
            
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
            out.println("<title>Servlet Calcolatore con Webservice</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1>Servlet Calcolatore che richiama un Webservice</h1>");
            out.println(risultato);
            out.println("<a href='/WebClnt'>Torna indietro</a>");
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
        return "Esempio utilizzo Servlet Java che richiama un Webservice";
    }

    private double calcola(int operazione, double operando1, double operando2)
    {
        calc.Calcolatore port = service.getCalcolatorePort();
        return port.calcola(operazione, operando1, operando2);
    }
}