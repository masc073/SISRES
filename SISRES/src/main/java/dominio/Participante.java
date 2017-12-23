
package dominio;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "PARTICIPANTE_SEQUENCE", sequenceName = "PARTICIPANTE_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Participante extends EntidadeNegocio
{
    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message= "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;
    
    @NotNull(message = "A senha deve ser preenchida.")
    @Size(min = 2, max = 14, message = "A senha deve conter entre 2 à 14 caracteres.")
    private String senha;
    
    @NotNull(message = "O Email deve ser preenchido.")
    @Email(message = "Informar email válido!")
    private String email;
    
    private Departamento departamento;
    
    private boolean aprovado;
    
    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
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
    
}
