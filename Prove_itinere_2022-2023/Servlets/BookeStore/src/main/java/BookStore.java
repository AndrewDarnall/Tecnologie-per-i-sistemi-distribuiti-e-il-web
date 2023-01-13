import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

// import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class BookStore extends HttpServlet {

    private Connection aConn = null;
    private static final String DATABASE_URL = "jdbc:mysql://localhost/exam";

    @Override
    public void init() {
        try {
            aConn = DriverManager.getConnection(DATABASE_URL, "gb", "gb");
            System.out.println("Succesfully connected to the db");
        } catch(SQLException e) {
            System.err.println("Caught SQL Exception:\t" + e);
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        Statement aStmt = null;
        ResultSet result = null;
        
        PrintWriter out = null;

        try {
            out = response.getWriter();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");

        out.println("<html><head><title> Book Store </title> ");
        out.println("<style> * {text-align: center;} input {width:100%;} table, tr, th, td {margin:25px;} </style> </head> <body>");
        out.println("<body><div id=\"main\">");
        out.println("<h1>Lost Library of Alexandria</h1>");

        try {
            aStmt = aConn.createStatement();
            result = aStmt.executeQuery("select * from books");

            // result.next();
            out.println("<table>");
            out.println("<tr> <th> ISBN </th> <th> TITLE </th> <th> AUTHOR </th> </tr>");
            while(result.next()) {
                out.println("<tr>");
                out.println("<td> <form id=\"min\" action=\"./showBooks\" method=\"POST\"> <input id=\"min\" type=\"submit\" value=\"" + result.getString("isbn") + "\"> <input id=\"min\" type=\"hidden\" name=\"isbn\" value=\"" + result.getString("isbn") + "\"> <input id=\"min\" type=\"hidden\" name=\"mode\" value=\"update\"> </form> </td>");
                out.println("<td> " + result.getString("title") + " </td>");
                out.println("<td> " + result.getString("author") + " </td>");
                out.println("</tr>");
            }
            out.println("</table>");


        } catch(SQLException e) {
            System.err.println("Query failed");
            e.printStackTrace();
        }

        out.println("<br><br>");
        out.println("<h1> Notice a missing book? Add one! </h1> <br>");
        out.println("<form action=\"./showBooks\" method=\"POST\">");
        out.println("<h3> { ISBN } </h3>");
        out.println("<input type=\"text\" name=\"isbn\">");
        out.println("<h3> { TITLE } </h3>");
        out.println("<input type=\"text\" name=\"title\">");
        out.println("<h3> { AUTHOR } </h3>");
        out.println("<input type=\"text\" name=\"author\">");
        out.println("<h3> { PUBLISHER } </h3>");
        out.println("<input type=\"text\" name=\"publisher\">");
        out.println("<h3> { RANK } </h3>");
        out.println("<input type=\"text\" name=\"rank\">");
        out.println("<h3> { YEAR } </h3>");
        out.println("<input type=\"text\" name=\"year\">");
        out.println("<h3> { PRICE } </h3>");
        out.println("<input type=\"text\" name=\"price\">");
        out.println("<br><input type=\"submit\" value=\"Add Book\">");
        out.println("<input type=\"hidden\" name=\"mode\" value=\"insert\">");
        out.println("</form>");

        out.println("</body></html>");

        try {
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String rank = request.getParameter("rank");
        String year = request.getParameter("year");
        String price = request.getParameter("price");
        String mode = request.getParameter("mode");



        PrintWriter out = null;

        try{
            out = response.getWriter();
        } catch(Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");

        try {

            switch(mode) {
                case "insert": {
                    PreparedStatement aStmt = null;
                    int result;

                    String query = "insert into books(isbn,title,author,publisher,ranking,year,price) values ('" + isbn + "','" + title + "','" + author +  "','" + publisher + "'," + rank + "," + year + "," + price + ")";
                    aStmt = aConn.prepareStatement(query);
                    result = aStmt.executeUpdate();

                    if(result == 0) {
                        System.err.println("Erro inserting record");
                        out.println("<p style=\"color: red; text-align: center;\"> Failed to insert record </p>");
                    } else {
                        out.println("<p style=\"color: green; text-align: center;\"> Succesfully added the Book! </p>");
                    }
                    break;
                }
                case "update" :{

                    Statement aStmt = null;
                    ResultSet result = null;

                    String query = "select * from books where isbn = '" + isbn + "'";

                    aStmt = aConn.createStatement();
                    result = aStmt.executeQuery(query);

                    out.println("<h1> Dettagli del libro:\t" + isbn +  " </h1>");

                    out.println("<table>");
                    out.println("<tr> <th> ISBN </th> <th> TITLE </th> <th> AUTHOR </th> </tr>");
                    while(result.next()) {
                        out.println("<tr>");
                        out.println("<td> " + result.getString("isbn") + " </td>");
                        out.println("<td> " + result.getString("title") + " </td>");
                        out.println("<td> " + result.getString("author") + " </td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");

                    out.println("<br><br>");
                    out.println("<h3> Do you want to delete this book? </h3>");
                    out.println("<form action=\"./showBooks\" method=\"POST\"> <input type=\"submit\" value=\"Remove Book\"> <input type=\"hidden\" name=\"isbn\" value=\"" + isbn + "\"> <input type=\"hidden\" name=\"mode\" value=\"remove\"> </form>");

                    break;
                } 
                case "remove": {

                    PreparedStatement aStmt = null;
                    int result;

                    String query = "delete from books where isbn = '" + isbn + "'";

                    aStmt = aConn.prepareStatement(query);
                    result = aStmt.executeUpdate();

                    if(result == 0) {
                        out.println("<p style=\"color: red;\"> Failed to remove record </p>");
                    } else {
                        out.println("<p style=\"color: green;\"> Succesfully added a record </p>");
                    }

                    break;
                }
            }

            out.println("<a style=\"text-align: center;\" href=\"./showBooks\"> Back Home");

        } catch(SQLException e) {
            e.printStackTrace();
        }

        try{
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        try{
            aConn.close();
        } catch(SQLException e) {
            System.err.println("Caughet SQL exception:\t" + e);
            e.printStackTrace();
        }
    }

}