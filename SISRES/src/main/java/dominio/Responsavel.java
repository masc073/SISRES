package dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "RESPONSAVEL_SEQUENCE", sequenceName = "RESPONSAVEL_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Responsavel extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message = "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve possuir apenas letras.")
    private String nome;

    @NotNull(message = "A senha deve ser preenchida.")
//    @Size(min = 2, max = 14, message = "A senha deve conter entre 2 à 14 caracteres.")
    private String senhaDigital;

    @NotNull(message = "O Email deve ser preenchido.")
    @Email(message = "Informar email válido!")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private Departamento departamento;

    private boolean servidor;

    private boolean aprovado;
    
    private boolean administrador;

    @Column
    private boolean lider;

    @Column(name = "numero_numeroAleatorio")
    private int numeroAleatorio;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    private Grupo grupo;

    public Responsavel()
    {

    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Departamento getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(Departamento departamento)
    {
        this.departamento = departamento;
    }

    public boolean isAprovado()
    {
        return aprovado;
    }

    public void setAprovado(boolean aprovado)
    {
        this.aprovado = aprovado;
    }

    public boolean isLider()
    {
        return lider;
    }

    public void setLider(boolean lider)
    {
        this.lider = lider;
    }

    public Responsavel(String nome, String senhaDigital)
    {
        this.nome = nome;
        this.senhaDigital = senhaDigital;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getSenhaDigital()
    {
        return senhaDigital;
    }

    public void setSenhaDigital(String senhaDigital)
    {
        this.senhaDigital = senhaDigital;
    }

    public boolean isServidor()
    {
        return servidor;
    }

    public void setServidor(boolean servidor)
    {
        this.servidor = servidor;
    }

    public int getNumeroAleatorio()
    {
        return numeroAleatorio;
    }

    public void setNumeroAleatorio(int numeroAleatorio)
    {
        this.numeroAleatorio = numeroAleatorio;
    }

    public Grupo getGrupo()
    {
        return grupo;
    }

    public void setGrupo(Grupo grupo)
    {
        this.grupo = grupo;
    }
    
    public boolean isAdministrador()
    {
        return administrador;
    }

    public void setAdministrador(boolean administrador)
    {
        this.administrador = administrador;
    }
}
