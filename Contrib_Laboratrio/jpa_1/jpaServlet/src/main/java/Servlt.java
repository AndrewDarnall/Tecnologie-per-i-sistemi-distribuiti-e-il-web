import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// @WebServlet("/myServlet")
public class Servlt extends HttpServlet {

    private List<Students> people = null;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        Students students = new Students();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("def");
        EntityManager em = emf.createEntityManager();
        /*
        // Only if I want to insert / commit changes to the database
        em.getTransaction().begin();
        em.persist(students);
        em.getTransaction().commit();
        */

        out.println("<html><head><title>Demo JPA page</title>");
        out.println("<style> * {padding: 15px; margin: 5px;} .blank{ border: 0px; border-radius: 0px; text-align: center;} table{border: 1px solid black; border-radius: 25px;} tr{border: 1px solid black; border-radius: 25px;} td{border: 1px solid black; border-radius: 25px;}</style></head><body>");
        // Notice the difference in the query - where we are submitting Students s instead of a hand made query due to
        // the Object Relational Mapping !
        List<Students> people = em.createQuery("select s from Students s", Students.class).getResultList();
        out.println("<h1> Query results</h1>");
        out.println("<table>");
        out.write("<tr class\"blank\">");
        out.write("<td class=\"blank\">NAME</td>\t<td class=\"blank\">AGE</td>\t<td class=\"blank\">DEGREECOURSE</td></tr><br>");
        for(Students s: people) {
                out.write("<tr>");
                    out.write("<td>" + s.getName() +"</td>\t");
                    out.write("<td>" + s.getAge() +"</td>\t");
                    out.write("<td>" + s.getDegreeCourse() + "</td>\t");
                out.write("</tr><br>");
        }
        out.println("</table>");
        out.println("</body></html>");

        out.close();

    }   

}

/**
 * 
 * Probably will need the Singleton design pattern to access the DataBase in Write mode
 */