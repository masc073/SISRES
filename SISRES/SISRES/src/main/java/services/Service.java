package services;

import domains.Responsavel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Service {
    
    public static void main(String[] args)
    {
        Responsavel responsavel = new Responsavel();
        responsavel.setNome("Natália Amâncio");
        responsavel.setSenhaDigital("123456");
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        
        try
        {
            emf = Persistence.createEntityManagerFactory("sisres");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(responsavel);
            et.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(et != null)
                et.rollback();
        }
    }
}
