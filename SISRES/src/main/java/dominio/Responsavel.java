package dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Responsavel extends EntidadeNegocio implements Serializable {
  
    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message= "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve possuir apenas letras.")
    private String nome;
    
    @NotNull(message = "A senha deve ser preenchida.")
    @Size(min = 2, max = 14, message = "A senha deve conter entre 2 à 14 caracteres.")
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
