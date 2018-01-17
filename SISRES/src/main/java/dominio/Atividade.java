package dominio;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "ATIVIDADE_SEQUENCE", sequenceName = "ATIVIDADE_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Atividade extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "A situação deve ser selecionada.")
    @Enumerated(EnumType.STRING)
    private SituacaoAtividade situacao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_requerimento", referencedColumnName = "id")
    protected Requerimento requerimento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "atividademodelo", referencedColumnName = "id")
    private AtividadeModelo atividademodelo;

    private String descricao_sucesso;
    
    private String descricao_erro;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Arquivo arquivo;

    public Atividade()
    {
    }

    public Atividade(SituacaoAtividade situacao, UnidadeOrganizacional departamento)
    {
        this.situacao = situacao;
    }

    public SituacaoAtividade getSituacao()
    {
        return situacao;
    }

    public void setSituacao(SituacaoAtividade situacao)
    {
        this.situacao = situacao;
    }

    public Arquivo getArquivo()
    {
        return arquivo;
    }

    public void setArquivo(Arquivo arquivo)
    {
        this.arquivo = arquivo;
    }

    public Arquivo criarArquivo()
    {
        return new Arquivo();
    }

    public Requerimento getRequerimento()
    {
        return requerimento;
    }

    public void setRequerimento(Requerimento requerimento)
    {
        this.requerimento = requerimento;
    }

    public AtividadeModelo getAtividadeModelo()
    {
        return atividademodelo;
    }

    public void setAtividadeModelo(AtividadeModelo atividadeModelo)
    {
        this.atividademodelo = atividadeModelo;
    }
 public AtividadeModelo getAtividademodelo()
    {
        return atividademodelo;
    }

    public void setAtividademodelo(AtividadeModelo atividademodelo)
    {
        this.atividademodelo = atividademodelo;
    }

    public String getDescricao_sucesso()
    {
        return descricao_sucesso;
    }

    public void setDescricao_sucesso(String descricao_sucesso)
    {
        this.descricao_sucesso = descricao_sucesso;
    }

    public String getDescricao_erro()
    {
        return descricao_erro;
    }

    public void setDescricao_erro(String descricao_erro)
    {
        this.descricao_erro = descricao_erro;
    }

}
