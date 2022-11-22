
package netsixservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getDisponibilita complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getDisponibilita">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serieInput" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="episodio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDisponibilita", propOrder = {
    "serieInput",
    "episodio"
})
public class GetDisponibilita {

    protected String serieInput;
    protected int episodio;

    /**
     * Recupera il valore della proprietà serieInput.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerieInput() {
        return serieInput;
    }

    /**
     * Imposta il valore della proprietà serieInput.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerieInput(String value) {
        this.serieInput = value;
    }

    /**
     * Recupera il valore della proprietà episodio.
     * 
     */
    public int getEpisodio() {
        return episodio;
    }

    /**
     * Imposta il valore della proprietà episodio.
     * 
     */
    public void setEpisodio(int value) {
        this.episodio = value;
    }

}
