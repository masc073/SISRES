package dominio;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Feriado extends EntidadeNegocio
{
    @NotNull
    @Size(min = 2, max = 40, message= "{dominio.Feriado.tamanho}")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "{dominio.Feriado.nome}")     
    private String nome;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date data_do_feriado;

    public Feriado()
    {
        
    }

    public Feriado(String nome, Date data_do_feriado)
    {
        this.nome = nome;
        this.data_do_feriado = data_do_feriado;
    }
    
    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
 
    public Date getData_do_feriado()
    {
        return data_do_feriado;
    }

    public void setData_do_feriado(Date data_do_feriado)
    {
        this.data_do_feriado = data_do_feriado;
    }
}
