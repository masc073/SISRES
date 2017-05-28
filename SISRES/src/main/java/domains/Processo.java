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
     
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_criador", referencedColumnName = "id")
    private Responsavel criador;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    @OneToMany(mappedBy = "processo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Atividade> atividades;

    public Processo()
    {
        atividades = new ArrayList<>();
    }
    
    public Processo(String nome, String instituicao, Responsavel criador, Date dataDeCriacao, ArrayList<Atividade> atividades)
    {
       this.nome = nome;
       this.instituicao = instituicao;
       this.criador = criador;
       this.dataDeCriacao = dataDeCriacao;
       this.atividades = atividades;
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
