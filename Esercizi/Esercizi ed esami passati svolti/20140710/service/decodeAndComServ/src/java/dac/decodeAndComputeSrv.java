/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dac;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author kylon
 */
@WebService(serviceName = "decodeAndComputeSrv")
public class decodeAndComputeSrv {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "decodeAndCompute")
    public Integer decodeAndCompute(@WebParam(name = "s") String s) {
        Integer n = Character.getNumericValue(s.charAt(1));

        switch (s.charAt(0)) {
            case '+': {
                for (int i=2; i<5; ++i)
                    n += Character.getNumericValue(s.charAt(i));
            }
                break;
            case '-': {
                for (int i=2; i<5; ++i)
                    n -= Character.getNumericValue(s.charAt(i));
            }
                break;
            default:
                break;
        }

        return n;
    }
}
