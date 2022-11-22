
package calc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per calcola complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="calcola">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operazione" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="operando1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="operando2" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calcola", propOrder = {
    "operazione",
    "operando1",
    "operando2"
})
public class Calcola {

    @XmlSchemaType(name = "unsignedShort")
    protected int operazione;
    protected double operando1;
    protected double operando2;

    /**
     * Recupera il valore della propriet\u00e0 operazione.
     * 
     */
    public int getOperazione() {
        return operazione;
    }

    /**
     * Imposta il valore della propriet\u00e0 operazione.
     * 
     */
    public void setOperazione(int value) {
        this.operazione = value;
    }

    /**
     * Recupera il valore della propriet\u00e0 operando1.
     * 
     */
    public double getOperando1() {
        return operando1;
    }

    /**
     * Imposta il valore della propriet\u00e0 operando1.
     * 
     */
    public void setOperando1(double value) {
        this.operando1 = value;
    }

    /**
     * Recupera il valore della propriet\u00e0 operando2.
     * 
     */
    public double getOperando2() {
        return operando2;
    }

    /**
     * Imposta il valore della propriet\u00e0 operando2.
     * 
     */
    public void setOperando2(double value) {
        this.operando2 = value;
    }

}
