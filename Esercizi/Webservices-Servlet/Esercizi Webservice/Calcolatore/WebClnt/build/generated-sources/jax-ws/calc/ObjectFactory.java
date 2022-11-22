
package calc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the calc package. 
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

    private final static QName _Calcola_QNAME = new QName("http://calc/", "calcola");
    private final static QName _CalcolaResponse_QNAME = new QName("http://calc/", "calcolaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: calc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Calcola }
     * 
     */
    public Calcola createCalcola() {
        return new Calcola();
    }

    /**
     * Create an instance of {@link CalcolaResponse }
     * 
     */
    public CalcolaResponse createCalcolaResponse() {
        return new CalcolaResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calcola }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://calc/", name = "calcola")
    public JAXBElement<Calcola> createCalcola(Calcola value) {
        return new JAXBElement<Calcola>(_Calcola_QNAME, Calcola.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalcolaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://calc/", name = "calcolaResponse")
    public JAXBElement<CalcolaResponse> createCalcolaResponse(CalcolaResponse value) {
        return new JAXBElement<CalcolaResponse>(_CalcolaResponse_QNAME, CalcolaResponse.class, null, value);
    }

}
