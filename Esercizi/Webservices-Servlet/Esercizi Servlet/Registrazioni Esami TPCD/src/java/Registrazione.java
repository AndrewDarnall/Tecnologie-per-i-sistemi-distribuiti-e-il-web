import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Registrazione", urlPatterns =
{
    "/Registrazione"
})
public class Registrazione extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        BufferedWriter writer = new BufferedWriter(new FileWriter("db.txt", true));
        String nome, cognome, matricola, lode;
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
                    lode = "Si";
                else
                    lode = "No";
                
                writer.append("Nome:"+request.getParameter("nome")+"; Cognome:"+request.getParameter("cognome")+"; Matricola:"+request.getParameter("matricola")+"; Voto:|"+voto+"|; Lode: "+lode+".\n");
                
                out.println("<p>Registrazione avvenuta con successo!</p>");
            }
            catch(IOException e)
            {
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
                
            out.println("<p><a href='./'>Torna indietro</a></p>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } finally
        {
            writer.close();
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
        return "Servlet registrazione esami TPCD";
    }// </editor-fold>
}

class NullParamsException extends Exception {}