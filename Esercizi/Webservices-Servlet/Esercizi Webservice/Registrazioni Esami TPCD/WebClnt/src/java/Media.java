import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import reg_esami.FileNotFoundException_Exception;
import reg_esami.IOException_Exception;
import reg_esami.RegistrazioneEsami_Service;

@WebServlet(name = "Media", urlPatterns =
{
    "/Media"
})
public class Media extends HttpServlet
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebService/RegistrazioneEsami.wsdl")
    private RegistrazioneEsami_Service service;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IOException_Exception, FileNotFoundException_Exception
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Media</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1>Servlet Registrazione Esami TPCD at " + request.getContextPath() + "</h1>");
            out.println(media());
            out.println("<p><a href='./'>Torna indietro</a></p>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } finally
        {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        }
        catch (Exception ex)    {}
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            processRequest(request, response);
        }
        catch (Exception ex)    {}
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Media dei voti registrati su db.txt";
    }// </editor-fold>

    private String media() throws IOException_Exception, FileNotFoundException_Exception
    {
        reg_esami.RegistrazioneEsami port = service.getRegistrazioneEsamiPort();
        return port.media();
    }
}
