/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package css;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author kylon
 */
@WebService(serviceName = "censStr")
public class censStr {
    private static String[] ban = new String[]{"esame", "difficile", "prolungamento"};
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "muteStr")
    public String muteStr(@WebParam(name = "s") String s) {
        for (String b: ban)
            s = s.replaceAll(b, "***");                                                                          

        return s;
    }
}
