
package cfs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per computeForceResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="computeForceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "computeForceResponse", propOrder = {
    "_return"
})
public class ComputeForceResponse {

    @XmlElement(name = "return")
    protected double _return;

    /**
     * Recupera il valore della proprietà return.
     * 
     */
    public double getReturn() {
        return _return;
    }

    /**
     * Imposta il valore della proprietà return.
     * 
     */
    public void setReturn(double value) {
        this._return = value;
    }

}
