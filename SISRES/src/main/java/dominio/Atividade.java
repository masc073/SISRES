package dominio;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 *
 * @author Natália
 */

@Entity
public class Atividade extends EntidadeNegocio implements Serializable {

    @NotNull
    @Size(min = 2, max = 60)
  //s  @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "{Atividade.nome}")
    private String nome;
    
    @NotNull(message = "{preenchimento_nao_informado}")
    @Enumerated(EnumType.STRING)
    private SituacaoAtividade situacao;
    
    @NotNull(message = "{preenchimento_nao_informado}")
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private Departamento departamento;
    
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_processo", referencedColumnName = "id")
    private Processo processo;

    // Anexos?
   
    public Atividade() { }
    
    public Atividade(String nome, SituacaoAtividade situacao, Departamento departamento, Atividade proximaAtividade,
            Processo processo)
    {
        this.nome = nome;
        this.situacao = situacao;
        this.departamento = departamento;
        this.processo = processo;
    }
   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public SituacaoAtividade getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoAtividade situacao) {
        this.situacao = situacao;
    }
    
     public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

}
