package servico;

import dominio.EntidadeNegocio;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

public class Servico<T extends EntidadeNegocio>
{
    @PersistenceContext(unitName = "sisres", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;
  
    public void Servico()
    {
    
    }
    
}