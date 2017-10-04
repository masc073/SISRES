package testesUnitarios;

import dominio.Processo;
import dominio.Responsavel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Natália
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteProcesso extends Teste {
    
    @Test
    public void t01_atualizarResponsavelDoProcesso()
    {
      
        Responsavel r = em.find(Responsavel.class, 4);
        
        Processo p = em.find(Processo.class, 1);
        p.setResponsavel(r);
        em.merge(p);
        p = em.find(Processo.class, 1);
        assertEquals(p.getResponsavel().getNome(), r.getNome());
    
    }
    
    @Test
    public void t02_deletarResponsavel()
    {
        Processo p = em.find(Processo.class, 1);
        em.remove(p);
        p = em.find(Processo.class, 1);
        assertNull(p);
    }
      
    
}
