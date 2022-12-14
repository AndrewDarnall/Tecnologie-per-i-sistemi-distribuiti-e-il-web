
package bbs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "bibl", targetNamespace = "http://bb/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Bibl {


    /**
     * 
     * @param qty
     * @param title
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "isBookAvailable", targetNamespace = "http://bb/", className = "bbs.IsBookAvailable")
    @ResponseWrapper(localName = "isBookAvailableResponse", targetNamespace = "http://bb/", className = "bbs.IsBookAvailableResponse")
    @Action(input = "http://bb/bibl/isBookAvailableRequest", output = "http://bb/bibl/isBookAvailableResponse")
    public boolean isBookAvailable(
        @WebParam(name = "title", targetNamespace = "")
        String title,
        @WebParam(name = "qty", targetNamespace = "")
        int qty);

}
