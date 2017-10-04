package testesUnitarios;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class Teste {
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;
    protected Logger logger;

    public Teste() {
        logger = Logger.getGlobal();
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("sisres");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        em.setFlushMode(FlushModeType.AUTO);
        et = em.getTransaction();
        et.begin();

    }
    
    @After
    public void tearDown() {
        try {
            if (et != null && et.isActive()) {
                et.commit();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            if (et.isActive())
                et.rollback();
        } finally {
            em.close();
        }
    }
}
