package testesUnitarios;

import dominio.Responsavel;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Natália
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteResponsavel extends Teste {
    
    public TesteResponsavel() {
    }
    
    @Test
    public void t01_atualizarResponsavel()
    {
      
        Responsavel r = em.find(Responsavel.class, 2);
        r.setNome("Maria Silva");
        em.merge(r);
        r = em.find(Responsavel.class, 2);
        assertEquals(r.getNome(), "Maria Silva");
    
    }
    
    @Test
    public void t02_deletarResponsavel()
    {
        Responsavel r = em.find(Responsavel.class, 3);
        em.remove(r);
        r = em.find(Responsavel.class, 3);
        assertNull(r);
    }
    
    public void t03_nomeInvalido()
    {
        
        
    
    }
    
    public void t04_nomeValido()
    {
        
        
    
    }
    
    public void t05_senhaInvalida()
    {
        
        
    
    }
    
    public void t06_senhaValida()
    {
        
        
    
    }
}
