package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Realiza a validação do email, analisando se é institucional, gmail ou outro.
 * @author Natália Amâncio
 */
public class ValidadorEmail implements ConstraintValidator<ValidaEmail, String>
{

    @Override
    public void initialize(ValidaEmail constraintAnnotation)
    {
        
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context)
    {
        boolean valido = false;

        if (email != null) 
        {
            if (email.endsWith("@gmail.com") || email.endsWith("recife.ifpe.edu.br"))
            {
                valido = true;
            }
        }
        else valido = true;
        
        return valido;
    }
    
}
