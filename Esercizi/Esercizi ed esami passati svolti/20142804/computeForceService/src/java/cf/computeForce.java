/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cf;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author kylon
 */
@WebService(serviceName = "computeForce")
public class computeForce {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "computeForce")
    public double computeForce(@WebParam(name = "f") float f, @WebParam(name = "m") float m, @WebParam(name = "a") float a) {
        if (f == 0.0)
            return m*a;
        else if (m == 0.0)
            return f/a;
        else
            return f/m;
            
    }
}
