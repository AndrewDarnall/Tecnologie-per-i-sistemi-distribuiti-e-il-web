import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.stream.events.ProcessingInstruction;

public class Former extends HttpServlet {

    private static Map<String, Integer> map = new HashMap<String, Integer>();
    private static String[] languages = {"C","C++","Java","JavaScript","Go","Python","Haskell","Erlang","C#","None"};

    private static int submissions = 0;

    //si poteva implementare anche la doGet()

    private static void initMap() {

        for (int i = 0; i < 10; i++) {
            map.put(languages[i],0);
        }

    }

    private static void addParam(String par) {

        int v = map.get(par);
        v += 1;
        map.put(par,v);

    }

    private static void increaseSubmissions() {

        submissions += 1;

    }

    private static int getSubmissions() {
        return submissions;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter postOut = response.getWriter();

        initMap();

        String parameter = request.getParameter("answer");

        addParam(parameter);

        increaseSubmissions();

        postOut.println("<html>");

        postOut.println("<head> <title> Response to Form </title> </head>");
        postOut.println("<body>");
        postOut.println("<div id=\"main\"> <h1> Total submissions: " + getSubmissions() +  "</h1>");
        postOut.println("The latest voted language is: " + parameter + " </div>");
        postOut.println("<br><br><button onclick=\"history.back()\">back to form </button>");
        postOut.println("</body>");
        postOut.println("</html>");

    }

}