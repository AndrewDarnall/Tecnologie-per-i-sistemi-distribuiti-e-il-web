
package grss;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the grss package. 
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

    private final static QName _MakeRandStrResponse_QNAME = new QName("http://rst/", "makeRandStrResponse");
    private final static QName _MakeRandStr_QNAME = new QName("http://rst/", "makeRandStr");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: grss
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MakeRandStrResponse }
     * 
     */
    public MakeRandStrResponse createMakeRandStrResponse() {
        return new MakeRandStrResponse();
    }

    /**
     * Create an instance of {@link MakeRandStr }
     * 
     */
    public MakeRandStr createMakeRandStr() {
        return new MakeRandStr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeRandStrResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rst/", name = "makeRandStrResponse")
    public JAXBElement<MakeRandStrResponse> createMakeRandStrResponse(MakeRandStrResponse value) {
        return new JAXBElement<MakeRandStrResponse>(_MakeRandStrResponse_QNAME, MakeRandStrResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeRandStr }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rst/", name = "makeRandStr")
    public JAXBElement<MakeRandStr> createMakeRandStr(MakeRandStr value) {
        return new JAXBElement<MakeRandStr>(_MakeRandStr_QNAME, MakeRandStr.class, null, value);
    }

}
