
package dacs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dacs package. 
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

    private final static QName _DecodeAndComputeResponse_QNAME = new QName("http://dac/", "decodeAndComputeResponse");
    private final static QName _DecodeAndCompute_QNAME = new QName("http://dac/", "decodeAndCompute");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dacs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DecodeAndCompute }
     * 
     */
    public DecodeAndCompute createDecodeAndCompute() {
        return new DecodeAndCompute();
    }

    /**
     * Create an instance of {@link DecodeAndComputeResponse }
     * 
     */
    public DecodeAndComputeResponse createDecodeAndComputeResponse() {
        return new DecodeAndComputeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DecodeAndComputeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dac/", name = "decodeAndComputeResponse")
    public JAXBElement<DecodeAndComputeResponse> createDecodeAndComputeResponse(DecodeAndComputeResponse value) {
        return new JAXBElement<DecodeAndComputeResponse>(_DecodeAndComputeResponse_QNAME, DecodeAndComputeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DecodeAndCompute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dac/", name = "decodeAndCompute")
    public JAXBElement<DecodeAndCompute> createDecodeAndCompute(DecodeAndCompute value) {
        return new JAXBElement<DecodeAndCompute>(_DecodeAndCompute_QNAME, DecodeAndCompute.class, null, value);
    }

}
