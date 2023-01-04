import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

// JDBC - DataBase dependent version {just to be quicker}
import java.sql.*;
import java.util.Properties;

public class FakeFlix extends HttpServlet {

    private Connection aConn = null;
    // Accertatevi che le credenziali siano corrette per l'utente e che abbiate i diritti corretti per il db
    private static final String DATABASE_URL = "jdbc:mysql://localhost/myDB";
    private Properties info = null;

    public void init() {

        // Per pura semplicita' ~ Idealmente va tenuto altrove
        info = new Properties();
        info.put("user","gb");
        info.put("password","gb");

        // Inizializzo la connessione al db
        try {
            aConn = DriverManager.getConnection(DATABASE_URL,info);
            System.out.println("Connected to db: " + aConn.toString());
        } catch (SQLException sqlException) {
            System.out.println("Caught connection error: " + sqlException);
            sqlException.printStackTrace();
            System.exit(-3);
        }

    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        PrintWriter out = null;
        Statement aStmt = null;
        ResultSet result = null;
        String randFilm = "";
        String query = "";

        // Prova ad effettuare la query
        try {
            aStmt = this.aConn.createStatement();
            query = "select * from wlist order by rand() limit 1";
            result = aStmt.executeQuery(query);
            result.next();
            randFilm = result.getString("titolo");
        } catch(Exception e) {
            System.err.println("Caught exception: " + e);
            e.printStackTrace();
        }

        try {
            out = response.getWriter();
            response.setContentType("text/html");

            String action = request.getParameter("action");

            if(action == null) {
                action = "";
            }

            out.println("<html><head><title> FakeFlix </title>");
            out.println("<style> input {width: 100%; text-align: center;} </style>");
            out.println("</head> <body>");
            out.println("<div id=\"main\"> <h1 style=\"text-align: center;\"> Welcome to FakeFlix </h1>");
            out.println("<h1 style=\"text-align: center;\"> Film Suggerito:</h1><p style=\"text-align: center;\">");
            if(!randFilm.equals("")) {    
                out.println(randFilm + "</p></div>");
            } else {
                out.println("No Film Available in DB </p></div>");
            }

            // Parte del form
            out.println("<form action=\"./showHome\" method=\"POST\"> <h3 style=\"text-align: center;\"> { Film } </h3> <input type=\"text\" name=\"Titolo\"> <br> <h3 style=\"text-align: center;\"> { Regista } </h3> <input type=\"text\" name=\"Regista\"> <input type=\"hidden\" name=\"action\" value=\"searchMovie\"> <br> <br> <input type=\"submit\" value=\"Cerca Film\">");
            out.println("</form>");
                    
            if(!action.equals("showWishList")) {
                out.println("<br> <form action=\"./showHome\" method=\"GET\"> <input type=\"hidden\" name=\"action\" value=\"showWishList\"> <input type=\"submit\" value=\"Show Wish List\"> </form>");
                out.println("</body></html>");
            } else {

                Statement aSt = null;
                ResultSet res = null;
                String sql = "select * from wlist";

                aSt = this.aConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                res = aSt.executeQuery(sql);

                if(res.next()) {

                    res.beforeFirst();

                    out.println("<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>");

                    while(res.next()) {
                        out.println("<tr> <td> " + res.getString("titolo") + " </td> <td> " + res.getString("regista") + " </td> </tr>");
                    }

                    out.println("</table>");

                } else {

                    out.println("<p style=\"color: red;\"> No Movies in the Wish List at this time! </p>");

                }

                out.println("<br><br> <form action=\"./showHome\" method=\"GET\"> <input type=\"submit\" value=\"Hide Wish List\"></form>");                

            }

            try{
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }


        } catch(Exception e) {
            System.out.println("Caught a print writer exception: " + e);
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String titolo = "";
        String regista = "";
        String query1 = "";
        String query2 = "";
        String action = "";
        Statement aStmt = null;
        Statement aStmt2 = null;
        ResultSet result = null;
        ResultSet result2 = null;
        boolean inFlist;
        boolean inWlist;

        // Handler del form ~ Lievemente piu' elegante di PHP
        PrintWriter out = null;
        response.setContentType("text/html");

        try{
            out = response.getWriter();
        } catch (Exception e) {
            System.err.println("Caught PrintWriter exception: " + e);
            e.printStackTrace();
        }

        titolo = request.getParameter("Titolo");
        regista = request.getParameter("Regista");
        action = request.getParameter("action");

        // Manca la verifica dell' input
        query1 = "select * from flist where titolo = '" + titolo + "' and regista = '" + regista + "'";
        query2 = "select * from wlist where titolo = '" + titolo + "' and regista = '" + regista + "'";

        try {

            // I parametri per il result set sono vitali per il metodo beforeFirst()
            aStmt = this.aConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result = aStmt.executeQuery(query2);

            aStmt2 = this.aConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            result2 = aStmt2.executeQuery(query1);
        

            out.println("<html>");
            out.println("<head> <title> ResultPage </title> ");
            out.println("<style> * { text-align: center;} input { width: 100% } </style> </head>");
            out.println("<body> <div id=\"main\"> <h1> Movie List </h1>");

            // out.println("<p> Titolo: " + titolo + " - Regista: " + regista + "</p>");
            
            if(action.equals("searchMovie")) {
                if(result.next() == false) {
                    out.println("<p style=\"text-align: center; color: red;\"> Movie NOT in Flist </p>");

                    out.println("<br><br> <h3> Vuoi aggiungere il film alla wishlist? </h3> <form action=\"./showHome\" method=\"POST\"> <input type=\"submit\" value=\"Si\"> <input type=\"hidden\" name=\"action\" value=\"addToWishList\"> <input type=\"hidden\" name=\"Titolo\" value=\"" + titolo +"\"> <input type=\"hidden\" name=\"Regista\" value=\"" + regista + "\"> </form>");
                    out.println("<br> <form action=\"./showHome\" method=\"GET\"> <input type=\"submit\" value=\"No\"> </form>");

                } else {

                    // Per ritornare al primo record ~ Meccanismo di controllo empty result set o no
                    result.beforeFirst();

                    out.println("<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>");

                    while(result.next()) {
                        out.println("<tr> <td> " + result.getString("titolo") + " </td> <td> " + result.getString("regista") + " </td> </tr>");
                    }
            
                    out.println("</table>");
                }

                result.close();
            } else if(action.equals("addToWishList")) {

                query1 = "insert into wlist (titolo, regista) values ('" + titolo + "','" + regista +"')";
                PreparedStatement stmt = this.aConn.prepareStatement(query1);
                stmt.executeUpdate();

                stmt.close();

                out.println("<h3> The Movie was succesfully added to the wishlist! </h3>");

            }

        } catch (SQLException sqlException) {
            System.err.println("Caught SQL exception: " + sqlException);
            sqlException.printStackTrace();
        }

        // Attenzione al path action con jetty
        out.println("<form action=\"./showHome\" method=\"GET\"> <input type=\"submit\" value=\"Home\"> </form>");
        out.println("</div></body></html>");

    }

    public void destroy() {
        try {
            aConn.close();
        } catch( SQLException sqlException) {
            System.out.println("Caught a SQL exception: " + sqlException);
            sqlException.printStackTrace();
        }
    }

}
/**
 * Note, the manual deployment in Apache Tomcat works falwlessly, even with Docker
 * however using the Maven project ... problems happen, possibly due to the nature
 * of the directory structure --> CONFIRMED! ~ This could be a problem
 * 
 * Jetty works fine just remember!! --> localhost:8080/test/showHome and NOT localhost:8080/FakeNetflix/showHome
 * Jetty is much simpler!
 * 
 * Da notare come le servlets gestiscono eventi in maniera notevolmente piu' elegante -> ottimo per
 * la programmazione ad eventi
 * 
 * 
 * NOTA BENE --> Le operazioni sono dipendenti dalla tabella e dal tipo di DB creato
 * 
 */