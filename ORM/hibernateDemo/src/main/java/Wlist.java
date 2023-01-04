import java.lang.annotation.Inherited; 
import jakarta.persistence.*;

@Entity
public class Wlist {
 
    @Id
    @GeneratedValue
    private Long id;
    private String titolo;
    private String regista;

    public Wlist() {}

    public Wlist(String titolo, String regista) {
        this.titolo = titolo;
        this.regista = regista;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getRegista() {
        return regista;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
}
// Class for object-relational mapping