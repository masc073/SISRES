package services;

import domains.Atividade;
import domains.Processo;
import domains.Responsavel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        
        Atividade a = new Atividade();
        a.setDataDeFim(new Date(2017));
        
        ArrayList<Atividade> atividades = new ArrayList<Atividade>();
        atividades.add(a);
        
        Processo p = new Processo();
        p.setCriador(responsavel);
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
