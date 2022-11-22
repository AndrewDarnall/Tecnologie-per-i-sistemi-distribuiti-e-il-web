
package bbs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbs package. 
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

    private final static QName _IsBookAvailableResponse_QNAME = new QName("http://bb/", "isBookAvailableResponse");
    private final static QName _IsBookAvailable_QNAME = new QName("http://bb/", "isBookAvailable");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IsBookAvailable }
     * 
     */
    public IsBookAvailable createIsBookAvailable() {
        return new IsBookAvailable();
    }

    /**
     * Create an instance of {@link IsBookAvailableResponse }
     * 
     */
    public IsBookAvailableResponse createIsBookAvailableResponse() {
        return new IsBookAvailableResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsBookAvailableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bb/", name = "isBookAvailableResponse")
    public JAXBElement<IsBookAvailableResponse> createIsBookAvailableResponse(IsBookAvailableResponse value) {
        return new JAXBElement<IsBookAvailableResponse>(_IsBookAvailableResponse_QNAME, IsBookAvailableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsBookAvailable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bb/", name = "isBookAvailable")
    public JAXBElement<IsBookAvailable> createIsBookAvailable(IsBookAvailable value) {
        return new JAXBElement<IsBookAvailable>(_IsBookAvailable_QNAME, IsBookAvailable.class, null, value);
    }

}
