import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

import jakarta.persistence.*;


public class FakeFlix extends HttpServlet {

    private EntityManager em = null;
    private List<Wlist> wlist = null;
    private List<Flist> flist = null;

    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        this.em = emf.createEntityManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = null;
        String suggMovie = null;
        String action = null;

        if(this.em == null) {
            System.err.println("EntitiyManager was NOT initialized!");
            // System.exit(-2);
        }

        try {
            out = response.getWriter();
        } catch(Exception e) {
            System.out.println("Caught exception:\t" + e);
            e.printStackTrace();
        }

        response.setContentType("text/html");
        action = request.getParameter("action");

        out.println("<html><head><title> FakeFlix </title>");
        out.println(" <style> * { text-align: center; } input { width: 100%; }  </style> </head>");
        out.println("<body> <div id=\"main\"> <h1> Welcome to FakeFlix </h1>");

        try {
            // Based on what you select, the query returns different things, the string or the record
            Query q = em.createNativeQuery("select titolo from Wlist order by rand() limit 1");
            suggMovie = (String) q.getSingleResult();
            if(!suggMovie.equals("")) {
                out.println("<h3> Film Suggerito </h3>");
                out.println("<p>" + suggMovie + "</p>");
            } else {
                // Purely for debugging
                out.println("<p style=\"color: red;\"> No movies found in the DataBase </p>");
            }
        } catch(Exception e) {
            System.out.println("Caught exception:\t" + e);
            e.printStackTrace();
        }

        // Form

        out.println("<form action=\"./showHome\" method=\"POST\"> ");
        out.println("<h3> { Film } </h3> <input type=\"text\" name=\"Titolo\">");
        out.println("<h3> { Regista } </h3> <input type=\"text\" name=\"Regista\">");
        out.println("<br> <br> <input type=\"submit\" value=\"Cerca Film\">");
        out.println(" </form>");

        // Show Wish List feature

        action = request.getParameter("action");

        if(action == null) {
            action = "";
        }


        if(action.equals("")) {

            out.println("<br> <br> <form action=\"./showHome\" method=\"GET\">");
            out.println("<input type=\"submit\" value=\"Show Wish List\">");
            out.println("<input type=\"hidden\" name=\"action\" value=\"showWishList\">");
            out.println("</form>");

        } else if (action.equals("showWishList")){

            out.println("<h3> Wish List Movies </h3> <br>");

            // Make sure you include the ORM mapped class
            wlist = em.createQuery("select m from Wlist m", Wlist.class).getResultList();

            out.println("<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>");

            for(Wlist w: wlist) {
                out.println("<tr> <td>" + w.getTitolo() + "</td> <td>" + w.getRegista() + "</td> </tr>");
            }

            out.println("</table>");

            out.println("<br> <br> <form action=\"./showHome\" method=\"GET\">");
            out.println("<input type=\"submit\" value=\"Hide Wish List\">");
            out.println("</form>");

        }

        out.println("<h5> End of form page </h5>");

        out.println("</div></body></html>");

        try {
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        // Implement CRUD methods in POST handler
        String titolo = "";
        String regista = "";
        String action = null;

        titolo = request.getParameter("Titolo");
        regista = request.getParameter("Regista");

        String query = "select * from Flist where titolo = '" + titolo +"' and regista = '" + regista + "'";

        Query q1 = em.createNativeQuery(query);
        Query q2 = em.createNativeQuery(query);

        // wlist = q1.getResultList();
        // flist = q2.getResultList();


        // Alternative method ~ Better for casting
        flist = em.createQuery("select m from Flist m where m.titolo = :titolo and m.regista = :regista", Flist.class).setParameter("titolo", titolo).setParameter("regista", regista).getResultList();
        wlist = em.createQuery("select m from Wlist m where m.titolo = :titolo and m.regista = :regista", Wlist.class).setParameter("titolo", titolo).setParameter("regista", regista).getResultList();


        PrintWriter out = null;

        try {
            out = response.getWriter();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");

        out.println("<html><head><title> Movie List Page </title>");
        out.println("<style>  * { text-align: center; } input { width: 100%; } </style> </head>");
        out.println("<body><div id=\"main\"> <h1> Movie Lists </h1>");

        if(flist.isEmpty()) {
            out.println("<p style=\"color: red;\"> The Movie is not in your flist </p>");
        } else {
            out.println("<h3> Flist Movies </h3>");

            out.println("<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>");

            // Show Flist 
            for(Flist f: flist) {
                out.println("<tr> <td> " + f.getTitolo() + " </td> <td> " + f.getRegista() + " </td> </tr>");
            }

            out.println("</table>");

        }

        action = request.getParameter("action");

        if(action == null) {
            action = "";
        }

        switch(action) {

            case "addMovie": {

                // Ha perfettamente senso usare transazzioni, devo ammettere che Hibernate e' notevolmente superiore a JDBC

                out.println("<h5> Adding movie to WishList </h5>");
                Wlist item = new Wlist(titolo, regista);
                EntityTransaction transaction = this.em.getTransaction();
                transaction.begin();
                this.em.persist(item);
                transaction.commit();
                out.println("<p style=\"color: green;\"> Movie Succesfully added </p>");
                
                break;
            }

            default: {
                if(wlist.isEmpty()) {
                    out.println("<p style=\"color: red;\"> The Movie is not in your wish list </p>");
                    out.println("<br> <br> <h3> Want to add the movie to the wish list? </h3>");
                    out.println("<form action=\"./showHome\" method=\"POST\"> <input type=\"submit\" value=\"Si\"> <input type=\"hidden\" name=\"Titolo\" value=" + titolo +"> <input type=\"hidden\" name=\"Regista\" value=" + regista + "> <input type=\"hidden\" name=\"action\" value=\"addMovie\">");
                    out.println("</form>");
                    out.println("<br> <form action=\"./showHome\" method=\"GET\"> <input type=\"submit\" value=\"No\">");
                    out.println("</form>");
                } else {

                    // Show Wlist
                    out.println("<h3> Wish List </h3>");
                    out.println("<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>");

                    for(Wlist w: wlist) {
                        out.println("<tr> <td> " + w.getTitolo() + " </td> <td> " + w.getRegista() + " </td> </tr>");
                    }

                    out.println("</table>");

                }
                break;
            }

        }

        out.println("<br><br><form action=\"./showHome\" method=\"GET\"> <input type=\"submit\" value=\"Home\"> </form>");

        out.println("</div></body></html>");

        try{
            out.close();
        } catch(Exception e) {
            e.printStackTrace();;
        }


    }

    public void destroy() {
        this.em.close();
    }

}
/**
 * 
 * Considerations: All and all using Hibernate is cleaner and better compared to JDBC
 * The 'boring' part is the the proper configuration of the directory structure and more
 * 
 * NOTA BENE:
 * 
 * Se il DB contiene gia records allora bisogna assicurarsi che le tabelle abbiano autoincrement
 * nella chiave primaria altrimenti diventa particolarmente ostico aggiungere records ...
 * 
 * Problema riscontrato --> Non ammette spazzi nelle stringhe immesse, salva solo la prima frase
 * 
 */