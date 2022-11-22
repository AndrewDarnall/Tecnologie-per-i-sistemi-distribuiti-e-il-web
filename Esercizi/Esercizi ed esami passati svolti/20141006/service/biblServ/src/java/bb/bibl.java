/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bb;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author kylon
 */
@WebService(serviceName = "bibl")
public class bibl {
    private String[] books = new String[] {"lal,3", "aha,4", "opp,10", "rrr,5"};

    /**
     * Web service operation
     */
    @WebMethod(operationName = "isBookAvailable")
    public boolean isBookAvailable(@WebParam(name = "title") String title, @WebParam(name = "qty") int qty) {
        for (String b: books) {
            String[] t1 = b.split(",");
            
            if (title.equals(t1[0]) && qty <= Integer.parseInt(t1[1]))
                return true;
        }

        return false;
    }
}
