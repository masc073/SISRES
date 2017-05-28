package domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Natália
 */

@Entity
@SequenceGenerator(name = "PROCESSO_SEQUENCE", sequenceName = "PROCESSO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Processo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROCESSO_SEQUENCE")
    private int id;
    
    private String nome;
    
    private String instituicao;
     
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_criador", referencedColumnName = "id")
    private Responsavel criador;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_atividadeInicial", referencedColumnName = "id")
    private Atividade atividadeInicial;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_atividadeFinal", referencedColumnName = "id")
    private Atividade atividadeFinal;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    private ArrayList<Atividade> atividades;

    public Processo()
    {
        atividades = new ArrayList<>();
    }
    
    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
    
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

    public Responsavel getCriador() {
        return criador;
    }

    public void setCriador(Responsavel criador) {
        this.criador = criador;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<Atividade> atividades) {
        this.atividades = atividades;
    }

    public Atividade getAtividadeInicial() {
        return atividadeInicial;
    }

    public void setAtividadeInicial(Atividade atividadeInicial) {
        this.atividadeInicial = atividadeInicial;
    }

    public Atividade getAtividadeFinal() {
        return atividadeFinal;
    }

    public void setAtividadeFinal(Atividade atividadeFinal) {
        this.atividadeFinal = atividadeFinal;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Processo)
            {
                Processo outro = (Processo) o;
                if (this.id == outro.id)
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
