package domains;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Natália
 */

@Entity
@SequenceGenerator(name = "ATIVIDADE_SEQUENCE", sequenceName = "ATIVIDADE_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Atividade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATIVIDADE_SEQUENCE")
    private int id;
    
    @Column
    private String nome;
    
    private SituacaoAtividade situacao;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private Departamento departamento;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_proximaAtividade", referencedColumnName = "id")
    private Atividade proximaAtividade;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_processo", referencedColumnName = "id")
    private Processo processo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeInicio;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeFim;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date duracaoMaxima;
    
    // Anexos?
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Atividade getProximaAtividade() {
        return proximaAtividade;
    }

    public void setProximaAtividade(Atividade proximaAtividade) {
        this.proximaAtividade = proximaAtividade;
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

    public Date getDuracaoMaxima() {
        return duracaoMaxima;
    }

    public void setDuracaoMaxima(Date duracaoMaxima) {
        this.duracaoMaxima = duracaoMaxima;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Atividade)
            {
                Atividade outra = (Atividade) o;
                if (this.id == outra.id)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }
}
