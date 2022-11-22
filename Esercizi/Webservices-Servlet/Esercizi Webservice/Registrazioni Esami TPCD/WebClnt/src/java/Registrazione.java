import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import reg_esami.IOException_Exception;
import reg_esami.RegistrazioneEsami_Service;


@WebServlet(name = "Registrazione", urlPatterns =
{
    "/Registrazione"
})
public class Registrazione extends HttpServlet
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WebService/RegistrazioneEsami.wsdl")
    private RegistrazioneEsami_Service service;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String nome, cognome, matricola;
        boolean lode;
        int voto;
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registrazione</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1>Servlet Registrazione Esami TPCD at " + request.getContextPath() + "</h1>");
            
            try
            {
                nome = request.getParameter("nome");
                cognome = request.getParameter("cognome");
                matricola = request.getParameter("matricola");
                
                if ((nome.compareTo("") == 0) || (cognome.compareTo("") == 0) || (matricola.compareTo("") == 0))
                    throw new NullParamsException();
                
                voto = Integer.parseInt(request.getParameter("voto"));
                
                if (request.getParameter("lode") != null)
                    lode = true;
                else
                    lode = false;
                
                if (registrazione(nome, cognome, matricola, voto, lode))
                    out.println("<p>Registrazione avvenuta con successo!</p>");
                else
                    out.println("<p>Errore nella registrazione!</p>");
            }
            catch(NumberFormatException e)
            {
                out.println("<p>Il campo 'voto' richiede un valore numerico!</p>");
            }
            catch(NullParamsException e)
            {
                out.println("<p>Devi riempire tutti i campi!</p>");
            }
            catch (IOException_Exception ex)
            {
                out.println("<p>Errore nella registrazione!</p>");
            }
                
            out.println("<p><a href='./'>Torna indietro</a></p>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        }
        finally
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Servlet che richiama il service registrazione esami TPCD";
    }// </editor-fold>

    private boolean registrazione(String nome, String cognome, String matricola, int arg3, boolean arg4) throws IOException_Exception
    {
        reg_esami.RegistrazioneEsami port = service.getRegistrazioneEsamiPort();
        return port.registrazione(nome, cognome, matricola, arg3, arg4);
    }
}
class NullParamsException extends Exception {}