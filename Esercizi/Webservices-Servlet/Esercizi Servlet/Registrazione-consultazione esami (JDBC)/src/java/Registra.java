import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Registra", urlPatterns =
{
    "/Registra"
})
public class Registra extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String output = "", redirect = "";
        
        // verifico validita' del Cookie
        Cookie[] usercookies = request.getCookies();
        boolean ok = false;
        
        if (usercookies != null)
        {
            for (int i = 0; i < usercookies.length; i++)
            {
                if (usercookies[i].getName().compareTo("registrazione&consultazione%esami") == 0 && usercookies[i].getValue().charAt(0) == '1')
                {
                    ok = true;
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
        
            // verifico validita' dei parametri
            String matricola, nome, cognome, materia;
            int voto = -1;
            
            matricola = request.getParameter("matricola");
            nome = request.getParameter("nome");
            cognome = request.getParameter("cognome");
            materia = request.getParameter("materia");
            
            try
            {
                voto = Integer.parseInt(request.getParameter("voto"));
            }
            catch (NumberFormatException e) {}
            
            if (matricola.compareTo("") == 0 || nome.compareTo("") == 0 || cognome.compareTo("") == 0 || materia.compareTo("") == 0 || voto < 18 || voto > 30)
            {
                output = "Errore! Dati inseriti non corretti!";
                redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"2; URL=docente.jsp\">";
            }
            else
            {
                String l = "no";
                
                if (voto == 30 && request.getParameter("lode") != null)
                    l = "si";
                    
                BufferedWriter writer = new BufferedWriter(new FileWriter("db_materie.csv", true));
                
                writer.append(matricola+","+nome+","+cognome+","+materia+","+voto+","+l+"\n");
                redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"1; URL=docente.jsp\">";
                output = "Materia registrata con successo!";
                writer.close();
            }
        }
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registrazione...</title>");     
            out.println(redirect);
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
        return "Verifica il cookie e registra la materia";
    }// </editor-fold>
}
