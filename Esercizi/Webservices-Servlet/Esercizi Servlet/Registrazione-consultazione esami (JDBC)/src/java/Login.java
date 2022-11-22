import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Gli account degli studenti e dei docenti saranno contenuti rispettivamente
 * in "studenti.txt" e "docenti.txt", dove ogni riga rappresenta un account
 * in formato Username:Password.
 * Il Cookie assegnato sara' del tipo:
 * - 0:username (nel caso dello studente)
 * - 1:username (nel caso del docente)
 */

@WebServlet(name = "Login", urlPatterns =
{
    "/Login"
})
public class Login extends HttpServlet
{
    static String debug = "";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        BufferedReader accountsReader;
        Cookie login;
        String output, pwd, redirect = "";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if ((username.compareTo("") == 0) || password.compareTo("") == 0)
        {
            output = ""
                    + "<p>Devi inserire username e password!</p>"
                    + "<p><a href='.'<pre><-- Torna indietro</pre></p>";
        }
        else if (request.getParameter("type").compareTo("studente") == 0)
        {
            accountsReader = new BufferedReader(new FileReader("studenti.txt"));
            
            // verifica username e password per l'account studente
            if ((pwd = checkAccount(username, accountsReader)) != null)
            {
               if (pwd.compareTo(password) == 0)
               {
                   login = new Cookie("registrazione&consultazione%esami", "0"+username);
                   login.setMaxAge(600);
                   response.addCookie(login);
                   redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"1; URL=index.jsp\">";
                   output = "Accesso in corso...";
               }
               else
               {
                   output = ""
                    + "<p>Password errata per l'account studente \"<b>"+username+"</b>\".</p>"
                    + "<p><a href='.'<pre><-- Torna indietro</pre></p>";
               }
            }
            else
            {
                output = ""
                    + "<p>Account studente non valido!</p>"
                    + "<p><a href='.'<pre><-- Torna indietro</pre></p>";
            }
        }
        else if (request.getParameter("type").compareTo("docente") == 0)
        {
            accountsReader = new BufferedReader(new FileReader("docenti.txt"));
            
            // verifica username e password per l'account docente
            if ((pwd = checkAccount(username, accountsReader)) != null)
            {
               if (pwd.compareTo(password) == 0)
               {
                   login = new Cookie("registrazione&consultazione%esami", "1"+username);
                   login.setMaxAge(600);
                   response.addCookie(login);
                   redirect = "<meta http-equiv=\"REFRESH\" CONTENT=\"1; URL=index.jsp\">";
                   output = "Accesso in corso...";
               }
               else
               {
                   output = ""
                    + "<p>Password errata per l'account docente \"<b>"+username+"</b>\".</p>"
                    + "<p><a href='.'<pre><-- Torna indietro</pre></p>";
               }
            }
            else
            {
                output = ""
                    + "<p>Account docente non valido!</p>"
                    + "<p><a href='.'<pre><-- Torna indietro</pre></p>";
            }
        }
        else
        {
            output = ""
                    + "<p>Errore nel login!</p>"
                    + "<p><a href='.'<pre><-- Torna indietro</pre></p>";      
        }
        
        try
        {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login</title>");  
            out.println(redirect);
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<h1>Registrazione-conslutazione esami</h1>");
            out.println(output);
            //out.println(debug);
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
        return "Identifica l'utente e gli assegna un cookie";
    }// </editor-fold>
    
    // controlla se l'account esiste e ne restituisce la password
    public String checkAccount(String name, BufferedReader reader) throws IOException
    {
        String tmp;
        int i;
        
        while ((tmp = reader.readLine()) != null)
        {
            i = 0;
            
            while (tmp.charAt(i) != ':')
                i++;
            
            if (tmp.substring(0, i).compareTo(name) == 0)
                return tmp.substring(i+1, tmp.length());
        }
        
        return null;
    }
}