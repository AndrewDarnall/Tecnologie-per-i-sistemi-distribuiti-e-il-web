import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Media", urlPatterns =
{
    "/Media"
})
public class Media extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        BufferedReader reader = new BufferedReader(new FileReader("db.txt"));
        String media, tmp;
        float count = 0, sum = 0, voto;
        
        while ((tmp = reader.readLine()) != null)
        {
            count++;
            voto = myParser(tmp);
            
            if (voto == -1)
            {
                sum = -1;
                break;
            }
            
            sum += voto;
        }
        
        if (sum == -1)
            media = "Errore nel calcolo della media";
        else
            media = "Media: "+sum/count;
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Media</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1>Servlet Registrazione Esami TPCD at " + request.getContextPath() + "</h1>");
            out.println(media);
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
        return "Media dei voti registrati su db.txt";
    }// </editor-fold>
    
    public int myParser(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '|')
            {
                int x = i+1;
                
                while (str.charAt(x) != '|')
                    x++;
                
                return Integer.parseInt(str.substring(i+1, x));
            }
        }
        return -1;
    }
}
