package servico;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Super classe dos serviços.
 *
 * @author Natália Amâncio
 */
public abstract class Servico
{

    @PersistenceContext(unitName = "sisres", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;

     /** Construtor Padrão
     */
    public Servico()
    {
        
    }

}
