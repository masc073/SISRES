package domains;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
/**
 *
 * @author Natália
 */
@Entity
@SequenceGenerator(name = "RESPONSAVEL_SEQUENCE", sequenceName = "RESPONSAVEL_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Responsavel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPONSAVEL_SEQUENCE")
    private int id;
    
    private String nome;
    
    private String senhaDigital;

    public Responsavel() { }
    
    public Responsavel(String nome, String senhaDigital)
    {
        this.nome = nome;
        this.senhaDigital = senhaDigital;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenhaDigital() {
        return senhaDigital;
    }

    public void setSenhaDigital(String senhaDigital) {
        this.senhaDigital = senhaDigital;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Responsavel)
            {
                Responsavel outro = (Responsavel) o;
                if (this.id == outro.id)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }  
}
