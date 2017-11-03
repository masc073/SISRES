package dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Departamento extends EntidadeNegocio implements Serializable  {
 
//    @NotNull
    @Size(min = 2, max = 40)
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+")
    private String nome;
    
    @Size(min = 2, max = 6)
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+")
    private String sigla;
    
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;

    public Departamento() { }
    
    public Departamento(String nome, String sigla, Responsavel responsavel)
    {
        this.nome = nome;
        this.sigla = sigla;
        this.responsavel = responsavel;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }
}

