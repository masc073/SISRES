package passos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Natália
 */
public class ConfiguracaoTeste {
    
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;

    public ConfiguracaoTeste() 
    {
        emf = Persistence.createEntityManagerFactory("sisres");
        em = emf.createEntityManager();
    }
}
