package dominio;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
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

    @Column
    private boolean finalizado;

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
        if(dataDeInicio != null)
            return fmt.format(dataDeInicio);
        else
            return "Data de início não informada"; 
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

}
