<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registrazione-consultazione esami</title>
        <%
            Cookie[] usercookies = request.getCookies();
            boolean ok = false;
            if (usercookies != null)
            {
                for (int i = 0; i < usercookies.length; i++)
                {
                    if (usercookies[i].getName().compareTo("registrazione&consultazione%esami") == 0)
                    {
                        ok = true;
                        if (usercookies[i].getValue() .charAt(0) == '0')
                        {
                            %>
                            <meta http-equiv="REFRESH" CONTENT="0; URL=Studente">
                            <%
                        }
                        else if (usercookies[i].getValue().charAt(0) == '1')
                        {
                            %>
                            <meta http-equiv="REFRESH" CONTENT="0; URL=docente.jsp">
                            <%
                        }
                        break;
                    }
                }
            }
            
            if (!ok)
            {
                %>
                    <meta http-equiv="REFRESH" CONTENT="0; URL=login.html">
                <%
            }
        %>
    </head>
</html>