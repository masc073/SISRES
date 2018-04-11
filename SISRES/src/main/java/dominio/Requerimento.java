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

/** Define o requerimento e suas características.
 * @author Natália Amâncio
 */

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

    boolean atrasado;

    /** Construtor Padrão
     */
    public Requerimento()
    {

    }

    /** Retorna o processo de onde foi criado o requerimento
     * @return Processo Processo pelo qual foi criado o requerimento
     */
    public Processo getProcesso()
    {
        return processo;
    }

    /** Atribui um processo ao que o requerimento seja criado.
     * @param processo Processo do requerimento
     */
    public void setProcesso(Processo processo)
    {
        this.processo = processo;
    }

    /** Retorna a data de início formatada dd/MM/yyyy
     * @return String Data do Início no formato dd/MM/yyyy
     */
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

    /** Retorna a data de fim formatada dd/MM/yyyy
     * @return String Data do fim no formato dd/MM/yyyy
     */
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

    /** Retorna a data de abertura do requerimento
     * @return Date Data de abertura do requerimento
     */
    public Date getDataDeInicio()
    {
        return dataDeInicio;
    }

    /** Atribui a data de abertura do requerimento
     * @param dataDeInicio data de abertura do requerimento
     */
    public void setDataDeInicio(Date dataDeInicio)
    {
        this.dataDeInicio = dataDeInicio;
    }

    /** Retorna a data de encerramento do requerimento
     * @return Date Data de encerramento do requerimento
     */
    public Date getDataDeFim()
    {
        return dataDeFim;
    }

    /** Atrubui a data de encerramento do requerimento
     * @param dataDeFim data de encerramento do requerimento
     */
    public void setDataDeFim(Date dataDeFim)
    {
        this.dataDeFim = dataDeFim;
    }

    /** Retorna a atividade atual do requerimento
     * @return Atividade atividade atual do requerimento
     */
    public Atividade getEstadoAtual()
    {
        return estadoAtual;
    }

    /** Atribui a atividade atual do requerimento
     * @param estadoAtual atividade atual do requerimento
     */
    public void setEstadoAtual(Atividade estadoAtual)
    {
        this.estadoAtual = estadoAtual;
    }

    /** Retorna o solicitante do requerimento
     * @return Responsavel solicitante do requerimento
     */
    public Responsavel getSolicitante()
    {
        return solicitante;
    }

    /** Atribui o solicitante do requerimento
     * @param solicitante solicitante do requerimento
     */    
    public void setSolicitante(Responsavel solicitante)
    {
        this.solicitante = solicitante;
    }

    /** Retorna se o processo foi finalizado ou não.
     * @return boolean - Finalizado ( True ) / Não finalizado ( False )
     */
    public boolean isFinalizado()
    {
        return finalizado;
    }

     /** Seta se o processo foi finalizado ou não.
     * @param finalizado - Finalizado ( True ) / Não finalizado ( False )
     */
    public void setFinalizado(boolean finalizado)
    {
        this.finalizado = finalizado;
    }
    
    /** Retorna a matrícula do aluno
     * @return String matrícula do aluno
     */
    public String getMatriculaAluno()
    {
        return matriculaAluno;
    }

    /** Atribui a matrícula do aluno
     * @param matriculaAluno matrícula do aluno
     */
    public void setMatriculaAluno(String matriculaAluno)
    {
        this.matriculaAluno = matriculaAluno;
    }
    
    /** Retorna atividades do requerimento
     * @return List<Atividade> Atividades que define o requerimento
     */
    public List<Atividade> getAtividades()
    {
        return atividades;
    }

    /** Atribui atividades ao requetimento
     * @param atividades Atividades do requetimento
     */
    public void setAtividades(List<Atividade> atividades)
    {
        this.atividades = atividades;
    }

    /** Cria as atividades de acordo com o modelo dela.
     * @param atividadesModelo Modelo da atividade
     */
    public void criarAtividades(List<AtividadeModelo> atividadesModelo)
    {
        Atividade atividade_atual;
        atividades = new ArrayList<>();

        for (AtividadeModelo atividadeModelo_atual : atividadesModelo) {
            atividade_atual = new Atividade();
            atividade_atual.setSituacao(SituacaoAtividade.EmEspera);
            atividade_atual.setAtividademodelo(atividadeModelo_atual);
            atividade_atual.setRequerimento(this);

            if (atividades.isEmpty()) {
                atividade_atual.setSituacao(SituacaoAtividade.Andamento);
                this.estadoAtual = atividade_atual;
            }
            this.atividades.add(atividade_atual);
        }
    }

    /** Retorna se o requerimento está atrasado ou não.
     * @return boolean Atrasado ( True ) / Não atrasado ( False )
     */
    public boolean isAtrasado()
    {
        return atrasado;
    }
    
    /** Atribui se o requerimento está atrasado ou não.
     * @param atrasado Atrasado ( True ) / Não atrasado ( False )
     */
    public void setAtrasado(boolean atrasado)
    {
        this.atrasado = atrasado;
    }

}
