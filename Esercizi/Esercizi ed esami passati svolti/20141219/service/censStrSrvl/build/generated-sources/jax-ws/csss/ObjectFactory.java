
package csss;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the csss package. 
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

    private final static QName _MuteStrResponse_QNAME = new QName("http://css/", "muteStrResponse");
    private final static QName _MuteStr_QNAME = new QName("http://css/", "muteStr");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: csss
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MuteStr }
     * 
     */
    public MuteStr createMuteStr() {
        return new MuteStr();
    }

    /**
     * Create an instance of {@link MuteStrResponse }
     * 
     */
    public MuteStrResponse createMuteStrResponse() {
        return new MuteStrResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MuteStrResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://css/", name = "muteStrResponse")
    public JAXBElement<MuteStrResponse> createMuteStrResponse(MuteStrResponse value) {
        return new JAXBElement<MuteStrResponse>(_MuteStrResponse_QNAME, MuteStrResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MuteStr }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://css/", name = "muteStr")
    public JAXBElement<MuteStr> createMuteStr(MuteStr value) {
        return new JAXBElement<MuteStr>(_MuteStr_QNAME, MuteStr.class, null, value);
    }

}
