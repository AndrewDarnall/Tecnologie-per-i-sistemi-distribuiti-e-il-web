
package server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per reverse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="reverse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="casa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reverse", propOrder = {
    "casa"
})
public class Reverse {

    protected String casa;

    /**
     * Recupera il valore della proprietà casa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCasa() {
        return casa;
    }

    /**
     * Imposta il valore della proprietà casa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCasa(String value) {
        this.casa = value;
    }

}
