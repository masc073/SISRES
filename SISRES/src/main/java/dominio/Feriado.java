package dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Define os feriados da Instituição, que não devem se considerados como dias úteis.
 * @author Natália Amâncio
 */

@Entity
@SequenceGenerator(name = "FERIADO_SEQUENCE", sequenceName = "FERIADO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Feriado extends EntidadeNegocio
{
    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message= "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve possuir apenas letras.")     
    private String nome;
    
    @NotNull(message = "A data deve ser preenchida.")
    @Temporal(TemporalType.DATE)
    private Date data_do_feriado;

    /** Construtor Padrão
    */
    public Feriado()
    {
        
    }

    /** Retorna nome do feriado
     * @return String Nome do Feriado
     */
    public String getNome()
    {
        return nome;
    }

    /** Atribui um nome ao feriado
     * @param nome Nome do feriado
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }
 
     /** Retorna a data do feriado
     * @return Date Data do Feriado
     */
    public Date getData_do_feriado()
    {
        return data_do_feriado;
    }

     /** Atribui a data do feriado
     * @param data_do_feriado Data do Feriado
     */
    public void setData_do_feriado(Date data_do_feriado)
    {
        this.data_do_feriado = data_do_feriado;
    }
    
    /** Retorna a data do feriado formatada dd/MM/yyyy
     * @return String Data do Feriado no formato dd/MM/yyyy
     */
    public String getFeriadoFormatado() throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        if (data_do_feriado != null) {
            return fmt.format(data_do_feriado);
        }
        else {
            return "Feriado não informado";
        }
    }
}
