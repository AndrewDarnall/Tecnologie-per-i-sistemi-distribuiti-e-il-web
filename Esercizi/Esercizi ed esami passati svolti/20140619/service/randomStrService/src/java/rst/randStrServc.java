/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rst;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author kylon
 */
@WebService(serviceName = "randStrServc")
public class randStrServc {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "makeRandStr")
    public String makeRandStr(@WebParam(name = "x") int x) {
        int len = (int)(Math.random()*x*100);
        String ret = "";
        
        for (int i=0; i<len; ++i)
            ret += Math.random() > 0.5 ? "1":"0";
        
        return ret;
    }
}
