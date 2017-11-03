package dominio;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntidadeNegocio implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public int hashCode()
    {
        int result = 5;
        int hash = this.id == null ? 0 : this.id.hashCode();
        return (37 * result) + hash;
    }

    @Override
    public boolean equals(Object object)
    {

        if (object != null)
        {
            if (!(object instanceof EntidadeNegocio))
            {
                return false;
            }

            EntidadeNegocio outro = (EntidadeNegocio) object;

            if ((this.id == 0 && outro.id != 0) || (this.id != 0 && this.id != outro.id))
            {
                return false;
            }

        }
        return true;
    }

}
