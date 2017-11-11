package dominio;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;

@Entity
public class Atividade extends EntidadeNegocio implements Serializable {

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 60, message= "O nome deve conter entre 2 à 60 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;
    
//    @NotNull(message = "A situação deve ser selecionada.")
    @Enumerated(EnumType.STRING)
    private SituacaoAtividade situacao;
    
    @NotNull(message = "O departamento deve ser selecionado.")
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_processo", referencedColumnName = "id")
    protected Processo processo;

    // Anexos?
   
    public Atividade() { }
    
    public Atividade(String nome, SituacaoAtividade situacao, Departamento departamento, Atividade proximaAtividade)
    {
        this.nome = nome;
        this.situacao = situacao;
        this.departamento = departamento;
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
    
    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}

