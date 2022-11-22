
package cfs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cfs package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ComputeForce_QNAME = new QName("http://cf/", "computeForce");
    private final static QName _ComputeForceResponse_QNAME = new QName("http://cf/", "computeForceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cfs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ComputeForceResponse }
     * 
     */
    public ComputeForceResponse createComputeForceResponse() {
        return new ComputeForceResponse();
    }

    /**
     * Create an instance of {@link ComputeForce_Type }
     * 
     */
    public ComputeForce_Type createComputeForce_Type() {
        return new ComputeForce_Type();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComputeForce_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cf/", name = "computeForce")
    public JAXBElement<ComputeForce_Type> createComputeForce(ComputeForce_Type value) {
        return new JAXBElement<ComputeForce_Type>(_ComputeForce_QNAME, ComputeForce_Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComputeForceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cf/", name = "computeForceResponse")
    public JAXBElement<ComputeForceResponse> createComputeForceResponse(ComputeForceResponse value) {
        return new JAXBElement<ComputeForceResponse>(_ComputeForceResponse_QNAME, ComputeForceResponse.class, null, value);
    }

}
