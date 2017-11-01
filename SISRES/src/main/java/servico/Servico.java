package servico;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public abstract class Servico
{

    @PersistenceContext(unitName = "sisres", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;

    public Servico()
    {

    }

}
