
package bbs;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "bibl", targetNamespace = "http://bb/", wsdlLocation = "http://localhost:8080/biblServ/bibl?wsdl")
public class Bibl_Service
    extends Service
{

    private final static URL BIBL_WSDL_LOCATION;
    private final static WebServiceException BIBL_EXCEPTION;
    private final static QName BIBL_QNAME = new QName("http://bb/", "bibl");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/biblServ/bibl?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BIBL_WSDL_LOCATION = url;
        BIBL_EXCEPTION = e;
    }

    public Bibl_Service() {
        super(__getWsdlLocation(), BIBL_QNAME);
    }

    public Bibl_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), BIBL_QNAME, features);
    }

    public Bibl_Service(URL wsdlLocation) {
        super(wsdlLocation, BIBL_QNAME);
    }

    public Bibl_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BIBL_QNAME, features);
    }

    public Bibl_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Bibl_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Bibl
     */
    @WebEndpoint(name = "biblPort")
    public Bibl getBiblPort() {
        return super.getPort(new QName("http://bb/", "biblPort"), Bibl.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Bibl
     */
    @WebEndpoint(name = "biblPort")
    public Bibl getBiblPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://bb/", "biblPort"), Bibl.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BIBL_EXCEPTION!= null) {
            throw BIBL_EXCEPTION;
        }
        return BIBL_WSDL_LOCATION;
    }

}
