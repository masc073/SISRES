package Run;

import dominio.Atividade;
import dominio.Processo;
import dominio.Responsavel;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Run {
    
    public static void main(String[] args)
    {
        
        Responsavel responsavel = new Responsavel();
        responsavel.setNome("Natália Amâncio");
        responsavel.setSenhaDigital("123456");
        
        Atividade a = new Atividade();
        a.setNome("blablabla");
        
        ArrayList<Atividade> atividades = new ArrayList<Atividade>();
        atividades.add(a);
        
        Processo p = new Processo();
        p.setResponsavel(responsavel); 
        p.setAtividades(atividades);
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        
        try
        {
            emf = Persistence.createEntityManagerFactory("sisres");
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(p);
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