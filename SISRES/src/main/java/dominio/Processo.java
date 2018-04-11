package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Entidade que define o processo do sisres.
 * @author Natália Amâncio
 */

@Entity
@SequenceGenerator(name = "PROCESSO_SEQUENCE", sequenceName = "PROCESSO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Processo extends EntidadeNegocio implements Serializable {

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message= "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;

    @NotNull(message = "O responsável deve ser preenchido.")
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    @NotNull(message = "O processo deve conter atividades.")
    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AtividadeModelo> atividades;
    
    @NotNull
    private int duracaoMaximaEmDias;
    
    /** Construtor Padrão
     */
    public Processo()
    {
        atividades = new ArrayList<>();
    }
    
    /** Retorna a data da criação do processo
     * @return Date Data de criação do processo
     */
    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

     /** Atribui a data da criação do processo
     * @param dataDeCriacao Data de criação do processo
     */
    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
    
    /** Retorna nome do Processo
     * @return String Nome do processo
     */
    public String getNome() {
        return nome;
    }

     /** Atribui um nome ao processo
     * @param nome Nome do processo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** Retorna o responsável pelo processo
     * @return Responsavel Responsável pelo processo
     */
    public Responsavel getResponsavel() {
        return responsavel;
    }

    /** Atribui o responsável pelo processo
     * @param responsavel Responsável pelo processo
     */
    public void setResponsavel(Responsavel responsavel) {
        
        this.responsavel = responsavel;
    }

    /** Retorna atividades do processo
     * @return List<AtividadeModelo> Atividades que define o processo
     */
    public List<AtividadeModelo> getAtividades() {
      
        return atividades;
    }

    /** Adicina atividades ao processo
     * @param atividade  Nova atividade do processo
     */
    public void addAtividade(AtividadeModelo atividade)
    {
        atividade.setProcesso(this);
        atividades.add(atividade);
    }
    
    /** Atribui atividades ao processo
     * @param atividades Atividades do processo
     */
    public void setAtividades(List<AtividadeModelo> atividades) 
    {
        for (AtividadeModelo atividade_atual : atividades)
        {
            addAtividade(atividade_atual);
        }
    }

    /** Retorna a duração máxima do processo em dias úteis.
     * @return int Duração máxima do processo em dias úteis.
     */
    public int getDuracaoMaximaEmDias() {
        return duracaoMaximaEmDias;
    }
    
    /** Atribui valor ao campo duração máxima em dias úteis.
     * @param int duração de dias
     */
    public void setDuracaoMaximaEmDias(int duracaoMaximaEmDias) {
        this.duracaoMaximaEmDias = duracaoMaximaEmDias;
    }
}
