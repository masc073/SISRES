package dominio;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntidadeNegocio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (object != null) {
            if (!(object instanceof EntidadeNegocio)) {
                return false;
            }

            EntidadeNegocio outro = (EntidadeNegocio) object;

            if ((this.id == 0 && outro.id != 0) || (this.id != 0 && this.id != outro.id)) {
                return false;
            }

        }
        return true;
    }

}
