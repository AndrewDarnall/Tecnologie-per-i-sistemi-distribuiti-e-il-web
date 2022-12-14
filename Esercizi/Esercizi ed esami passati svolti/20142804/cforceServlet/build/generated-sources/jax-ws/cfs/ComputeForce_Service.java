
package cfs;

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
@WebServiceClient(name = "computeForce", targetNamespace = "http://cf/", wsdlLocation = "http://localhost:8080/computeForceService/computeForce?wsdl")
public class ComputeForce_Service
    extends Service
{

    private final static URL COMPUTEFORCE_WSDL_LOCATION;
    private final static WebServiceException COMPUTEFORCE_EXCEPTION;
    private final static QName COMPUTEFORCE_QNAME = new QName("http://cf/", "computeForce");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/computeForceService/computeForce?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        COMPUTEFORCE_WSDL_LOCATION = url;
        COMPUTEFORCE_EXCEPTION = e;
    }

    public ComputeForce_Service() {
        super(__getWsdlLocation(), COMPUTEFORCE_QNAME);
    }

    public ComputeForce_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), COMPUTEFORCE_QNAME, features);
    }

    public ComputeForce_Service(URL wsdlLocation) {
        super(wsdlLocation, COMPUTEFORCE_QNAME);
    }

    public ComputeForce_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, COMPUTEFORCE_QNAME, features);
    }

    public ComputeForce_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ComputeForce_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ComputeForce
     */
    @WebEndpoint(name = "computeForcePort")
    public ComputeForce getComputeForcePort() {
        return super.getPort(new QName("http://cf/", "computeForcePort"), ComputeForce.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ComputeForce
     */
    @WebEndpoint(name = "computeForcePort")
    public ComputeForce getComputeForcePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://cf/", "computeForcePort"), ComputeForce.class, features);
    }

    private static URL __getWsdlLocation() {
        if (COMPUTEFORCE_EXCEPTION!= null) {
            throw COMPUTEFORCE_EXCEPTION;
        }
        return COMPUTEFORCE_WSDL_LOCATION;
    }

}
