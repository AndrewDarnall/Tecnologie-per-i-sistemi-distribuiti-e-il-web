
package netsixservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the netsixservice package. 
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

    private final static QName _GetDisponibilita_QNAME = new QName("http://netsixService/", "getDisponibilita");
    private final static QName _GetDisponibilitaResponse_QNAME = new QName("http://netsixService/", "getDisponibilitaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: netsixservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDisponibilitaResponse }
     * 
     */
    public GetDisponibilitaResponse createGetDisponibilitaResponse() {
        return new GetDisponibilitaResponse();
    }

    /**
     * Create an instance of {@link GetDisponibilita }
     * 
     */
    public GetDisponibilita createGetDisponibilita() {
        return new GetDisponibilita();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDisponibilita }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://netsixService/", name = "getDisponibilita")
    public JAXBElement<GetDisponibilita> createGetDisponibilita(GetDisponibilita value) {
        return new JAXBElement<GetDisponibilita>(_GetDisponibilita_QNAME, GetDisponibilita.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDisponibilitaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://netsixService/", name = "getDisponibilitaResponse")
    public JAXBElement<GetDisponibilitaResponse> createGetDisponibilitaResponse(GetDisponibilitaResponse value) {
        return new JAXBElement<GetDisponibilitaResponse>(_GetDisponibilitaResponse_QNAME, GetDisponibilitaResponse.class, null, value);
    }

}
