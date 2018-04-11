package excecao;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import util.LeitorPropriedades;

/**
 * Define as mensagens de exceções que são exibidas para o usuário
 *
 * @author Natália Amâncio
 */

public class MensagemExcecao
{
    protected Throwable excecao;
    protected static LeitorPropriedades leitor = new LeitorPropriedades(new String[]
    {
        "Exception.properties", "Mensagens.properties"
    });

    /** Construtor Padrão
     */
    public MensagemExcecao(Throwable excecao)
    {
        this.excecao = excecao;
    }

    public String getMensagem()
    {
        StringBuilder mensagem = new StringBuilder();

        if (excecao instanceof ConstraintViolationException)
        {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) excecao).getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations)
            {
                if (mensagem.length() != 0)
                {
                    mensagem.append("; ");
                }

                mensagem.append(violation.getPropertyPath());
                mensagem.append(" ");
                mensagem.append(violation.getMessage());
            }

            mensagem
                    = new StringBuilder(String.format(
                            leitor.get(excecao.getClass().getName()),
                            mensagem.toString()));
        } else if (excecao instanceof ExcecaoNegocio)
        {
            mensagem.append(leitor.get(((ExcecaoNegocio) excecao).getChave()));
        } else if (excecao != null && leitor.get(excecao.getClass().getName()) != null)
        {
            mensagem.append(leitor.get(excecao.getClass().getName()));
        } else
        {
            mensagem.append(leitor.get("java.lang.Exception"));
        }

        return mensagem.toString();
    }
}