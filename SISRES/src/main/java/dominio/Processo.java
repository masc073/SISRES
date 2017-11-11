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

@Entity
public class Processo extends EntidadeNegocio implements Serializable {

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message= "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;

    @NotNull(message = "O responsável deve ser preenchido.")
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeCriacao;

    @NotNull(message = "O processo deve conter atividades.")
    @OneToMany(mappedBy = "processo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Atividade> atividades;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeInicio;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDeFim;
    
    @NotNull
    private int duracaoMaximaEmDias;
    
    public Processo()
    {
        System.out.println("NOVO PROCESSO!");
        atividades = new ArrayList<>();
    }
    
    public Processo(String nome, Responsavel criador, Date dataDeCriacao, ArrayList<Atividade> atividades, 
            Date dataDeInicio, Date dataDeFim, int duracaoMaximaEmDias)
    {
        System.out.println("Estou no construtor do processo!");
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
        
        for(Atividade atv : atividades)
        {
            System.out.println("Atividade: " +  atv.getNome());
            
        }
        System.out.println("\blaaaaaaaaa");
        return atividades;
    }

    public void setAtividades(ArrayList<Atividade> atividades) {
        System.out.println("Passei no set lista do processo");
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

    public void setDuracaoMaximaEmDias(int duracaoMaximaEmDias) {
        this.duracaoMaximaEmDias = duracaoMaximaEmDias;
    }
}
