package dominio;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "REQUERIMENTO_SEQUENCE", sequenceName = "REQUERIMENTO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Requerimento extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "Um processo deve ser selecionado.")
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_processo", referencedColumnName = "id")
    private Processo processo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeInicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeFim;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_atividade", referencedColumnName = "id")
    private Atividade estadoAtual;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_solicitante", referencedColumnName = "id")
    private Responsavel solicitante;

    @OneToMany(mappedBy = "requerimento", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades;

    @Column
    private boolean finalizado;

    @NotNull
    @Column
    private String matriculaAluno;

    public Requerimento()
    {

    }

    public Processo getProcesso()
    {
        return processo;
    }

    public void setProcesso(Processo processo)
    {
        this.processo = processo;
    }

    public String getDataDeInicioFormatada() throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (dataDeInicio != null) {
            return fmt.format(dataDeInicio);
        }
        else {
            return "Data de início não informada";
        }
    }

    public String getDataDeFimFormatada() throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (dataDeFim != null) {
            return fmt.format(dataDeFim);
        }
        else {
            return "Data de fim não informada";
        }
    }

    public Date getDataDeInicio()
    {
        return dataDeInicio;
    }

    public void setDataDeInicio(Date dataDeInicio)
    {
        this.dataDeInicio = dataDeInicio;
    }

    public Date getDataDeFim()
    {
        return dataDeFim;
    }

    public void setDataDeFim(Date dataDeFim)
    {
        this.dataDeFim = dataDeFim;
    }

    public Atividade getEstadoAtual()
    {
        return estadoAtual;
    }

    public void setEstadoAtual(Atividade estadoAtual)
    {
        this.estadoAtual = estadoAtual;
    }

    public Responsavel getSolicitante()
    {
        return solicitante;
    }

    public void setSolicitante(Responsavel solicitante)
    {
        this.solicitante = solicitante;
    }

    public boolean isFinalizado()
    {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado)
    {
        this.finalizado = finalizado;
    }

    public String getMatriculaAluno()
    {
        return matriculaAluno;
    }

    public void setMatriculaAluno(String matriculaAluno)
    {
        this.matriculaAluno = matriculaAluno;
    }

    public List<Atividade> getAtividades()
    {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades)
    {
        this.atividades = atividades;
    }

    public void criarAtividades(List<AtividadeModelo> atividadesModelo)
    {
        Atividade atividade_atual;
        atividades = new ArrayList<>();

        for (AtividadeModelo atividadeModelo_atual : atividadesModelo) {
            atividade_atual = new Atividade();
            atividade_atual.setSituacao(SituacaoAtividade.EmEspera);
            atividade_atual.setAtividadeModelo(atividadeModelo_atual);
            atividade_atual.setRequerimento(this);

            if (atividades.isEmpty()) {
                atividade_atual.setSituacao(SituacaoAtividade.Andamento);
                this.estadoAtual = atividade_atual;
            }
            this.atividades.add(atividade_atual);
        }
    }
}
