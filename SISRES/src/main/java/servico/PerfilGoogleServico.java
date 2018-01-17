package servico;

import dominio.PerfilGoogle;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PerfilGoogleServico extends Servico
{

    public boolean persistePerfilGoogle(PerfilGoogle perfilGoogle)
    {
        em.persist(perfilGoogle);

        return true;
    }
}
