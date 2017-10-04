package testesUnitarios;

import dominio.Atividade;
import dominio.SituacaoAtividade;
import java.util.Set;
import java.util.logging.Level;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * @author Natália
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteAtividade extends Teste {
    
    @Test
    public void t01_atualizarAtividade()
    {
        Atividade a = em.find(Atividade.class, 1);
        a.setSituacao(SituacaoAtividade.Andamento);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Atividade>> constraintViolations = validator.validate(a);

        if (logger.isLoggable(Level.INFO)) {
            constraintViolations.stream().forEach((violation) -> {
                logger.log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            });
        }
        
        em.merge(a);
        a = em.find(Atividade.class, 1);
        assertEquals(a.getSituacao(), SituacaoAtividade.Andamento);
    }
    
    @Test
    public void t02_deletarAtividade()
    {
        Atividade a = em.find(Atividade.class, 2);
        em.remove(a);
        a = em.find(Atividade.class, 2);
        assertNull(a);
    }
    
   // @Test
    public void t03_nomeInvalido()
    {
        Atividade a = new Atividade();
        a.setNome("1234");
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Set<ConstraintViolation<Atividade>> constraintViolations = validator.validate(a);
        assertEquals(1, constraintViolations.size());
        
        ConstraintViolation<Atividade> violation = constraintViolations.iterator().next();
        assertEquals("Deve possuir entre 2 à 30 caracteres, sendo  uma única letra maiúscula, seguida por letras minúsculas.", violation.getMessage());
    }  
    
 
    
    
    
}
