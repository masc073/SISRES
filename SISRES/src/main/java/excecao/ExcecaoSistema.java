package excecao;

import javax.ejb.ApplicationException;

/**
 * Exceções do sistema.
 *
 * @author Natália Amâncio
 */

@ApplicationException(rollback = true)
public class ExcecaoSistema extends RuntimeException
{
    /** Construtor Padrão
     */
    public ExcecaoSistema(Throwable causa)
    {
        super(causa);
    }

    @Override
    public String getMessage()
    {
        MensagemExcecao mensagemExcecao = new MensagemExcecao(this);
        return mensagemExcecao.getMensagem();
    }
}