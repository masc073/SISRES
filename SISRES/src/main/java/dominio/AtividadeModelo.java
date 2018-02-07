    package dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "ATIVIDADE_MODELO_SEQUENCE", sequenceName = "ATIVIDADE_MODELO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class AtividadeModelo extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 60, message = "O nome deve conter entre 2 à 60 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;

    @NotNull(message = "O departamento deve ser selecionado.")
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_unidadeOrganizacional", referencedColumnName = "id")
    private UnidadeOrganizacional unidade_organizacional;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_processo", referencedColumnName = "id")
    protected Processo processo;

    @OneToMany(mappedBy = "atividademodelo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades;

    private boolean anexarArquivo;

    public AtividadeModelo()
    {

    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public UnidadeOrganizacional getUnidade_organizacional()
    {
        return unidade_organizacional;
    }

    public void setUnidade_organizacional(UnidadeOrganizacional unidade_organizacional)
    {
        this.unidade_organizacional = unidade_organizacional;
    }

    public Processo getProcesso()
    {
        return processo;
    }

    public void setProcesso(Processo processo)
    {
        this.processo = processo;
    }

    public boolean isAnexarArquivo()
    {
        return anexarArquivo;
    }

    public void setAnexarArquivo(boolean anexarArquivo)
    {
        this.anexarArquivo = anexarArquivo;
    }

    public List<Atividade> getAtividades()
    {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades)
    {
        this.atividades = atividades;
    }

}
