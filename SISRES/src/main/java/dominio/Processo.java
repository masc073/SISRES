package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Natália
 */

@Entity
public class Processo extends EntidadeNegocio implements Serializable {
   
    @NotNull
    @Size(min = 2, max = 40)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "")
    private String nome;
    
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    @NotNull
    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Atividade> atividades;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeInicio;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeFim;
    
    @NotNull(message = "{preenchimento_nao_informado}")
    private int duracaoMaximaEmDias;

    public Processo()
    {
        atividades = new ArrayList<>();
    }
    
    public Processo(String nome, Responsavel criador, Date dataDeCriacao, ArrayList<Atividade> atividades, 
            Date dataDeInicio, Date dataDeFim, int duracaoMaximaEmDias)
    {
       this.nome = nome;
       this.responsavel = criador;
       this.dataDeCriacao = dataDeCriacao;
       this.atividades = atividades;
       this.dataDeInicio = dataDeInicio;
       this.dataDeFim = dataDeFim;
       this.duracaoMaximaEmDias = duracaoMaximaEmDias;
    }
    
    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<Atividade> atividades) {
        this.atividades = atividades;
    }
    
    public Date getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(Date dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public Date getDataDeFim() {
        return dataDeFim;
    }

    public void setDataDeFim(Date dataDeFim) {
        this.dataDeFim = dataDeFim;
    }

    public int getDuracaoMaximaEmDias() {
        return duracaoMaximaEmDias;
    }

    public void setDuracaoMaximaEmDias(int duracaoMaxima) {
        this.duracaoMaximaEmDias = duracaoMaximaEmDias;
    }
}
