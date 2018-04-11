package excecao;

import javax.ejb.ApplicationException;

/**
 * Exceções de acordo com a regra de negócio do SISRES.
 *
 * @author Natália Amâncio
 */

@ApplicationException(rollback = true)
public class ExcecaoNegocio extends Exception
{
    private String chave;
    public static final String OBJETO_INEXISTENTE = "excecao.ExcecaoNegocio.objetoInexistente";
    public static final String OBJETO_EXISTENTE = "excecao.ExcecaoNegocio.objetoExistente";    
    public static final String OBJETO_ASSOCIADO = "excecao.ExcecaoNegocio.objetoAssociado";

    /** Construtor Padrão
     */
    public ExcecaoNegocio(String chave)
    {
        this.chave = chave;
    }

    public String getChave()
    {
        return chave;
    }

    @Override
    public String getMessage()
    {
        MensagemExcecao mensagemExcecao = new MensagemExcecao(this);
        return mensagemExcecao.getMensagem();
    }
}