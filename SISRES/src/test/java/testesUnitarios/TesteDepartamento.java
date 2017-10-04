package testesUnitarios;

import dominio.Departamento;
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
public class TesteDepartamento extends Teste {
    
    @Test
    public void t01_atualizarDepartamento()
    {
        Responsavel r = em.find(Responsavel.class, 1);
        
        Departamento d = em.find(Departamento.class, 2);
        d.setResponsavel(r);
        em.merge(d);
        d = em.find(Departamento.class, 2);
        assertEquals(d.getResponsavel().getNome(), r.getNome());
    
    }
    
    @Test
    public void t02_deletarDepartamento()
    {
        Departamento d = em.find(Departamento.class, 7);
        em.remove(d);
        d = em.find(Departamento.class, 7);
        assertNull(d);
    }
    
    public void t03_nomeInvalido()
    {
        
        
    
    }
    
    public void t04_nomeValido()
    {
        
        
    
    }
    
    public void t05_siglaInvalida()
    {
        
        
    
    }
    
    public void t06_siglaValida()
    {
        
        
    
    }
}
