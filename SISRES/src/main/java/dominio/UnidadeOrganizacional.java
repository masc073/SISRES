package dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "DEPARTAMENTO_SEQUENCE", sequenceName = "DEPARTAMENTO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class UnidadeOrganizacional extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 60, message = "O nome deve conter entre 2 à 60 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve possuir apenas letras.")
    private String nome;

    @Size(min = 2, max = 6, message = "A sigla deve conter entre 2 à 6 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "A sigla deve possuir apenas letras.")
    private String sigla;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    @OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_mae", referencedColumnName = "id")
    private UnidadeOrganizacional mae;

//    @OneToMany(mappedBy = "unidadeOrganizacional", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<UnidadeOrganizacional> filhas;

    public UnidadeOrganizacional() {  }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getSigla()
    {
        return sigla;
    }

    public void setSigla(String sigla)
    {
        this.sigla = sigla;
    }

    public Responsavel getResponsavel()
    {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel)
    {
        this.responsavel = responsavel;
    }
    
    public UnidadeOrganizacional getMae()
    {
        return mae;
    }

    public void setMae(UnidadeOrganizacional mae)
    {
        this.mae = mae;
    }

//    public List<UnidadeOrganizacional> getFilhas()
//    {
//        return filhas;
//    }
//
//    public void setFilhas(List<UnidadeOrganizacional> filhas)
//    {
//        this.filhas = filhas;
//    }
    
}
