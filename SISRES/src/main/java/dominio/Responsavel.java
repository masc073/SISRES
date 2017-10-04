package dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 *
 * @author Natália
 */
@Entity
public class Responsavel extends EntidadeNegocio implements Serializable{
  
    @NotNull
    @Size(min = 2, max = 40)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "")
    private String nome;
    
    @NotNull
    @Size(min = 2, max = 14)
    private String senhaDigital;

    public Responsavel() { }
    
    public Responsavel(String nome, String senhaDigital)
    {
        this.nome = nome;
        this.senhaDigital = senhaDigital;
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
}
