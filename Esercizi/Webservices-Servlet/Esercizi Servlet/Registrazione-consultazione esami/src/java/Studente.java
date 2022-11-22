import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Studente", urlPatterns =
{
    "/Studente"
})
public class Studente extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String output = "", redirect = "", matricola = "";
        
        // verifico validita' del Cookie
        Cookie[] usercookies = request.getCookies();
        boolean ok = false;
        
        if (usercookies != null)
        {
            for (int i = 0; i < usercookies.length; i++)
            {
                if (usercookies[i].getName().compareTo("registrazione&consultazione%esami") == 0 && (matricola = usercookies[i].getValue()).charAt(0) == '0')
                {
                    ok = true;
                    matricola = matricola.substring(1, matricola.length());
                    break;
                }
            }
        }
        
        if (!ok)
        {
                redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"0; URL=login.html\">";
        }
        else
        {
            BufferedReader reader = new BufferedReader(new FileReader("profili_studenti/"+matricola+".txt"));
            String tmp;
            
            output = "<h2>Elenco materie registrate:</h2>\n"
                    + "<hr>\n";
            
            while ((tmp = reader.readLine()) != null)
                output += tmp;
            
            output += "\n<hr>\n";
        }
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println(redirect);
            out.println("<title>Portale Studenti</title>");
            out.println("</head>");
            out.println("<body><center>");
            out.println(output);
            out.println("</center></body>");
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
        return "Controlla la validita' del Cookie e restituisce le materie registrate dello studente.";
    }// </editor-fold>
}
