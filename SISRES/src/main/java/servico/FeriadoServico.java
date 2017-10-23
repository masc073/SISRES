
package servico;

import dominio.Feriado;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FeriadoServico extends Servico
{
    public void salvar(Feriado feriado) throws ExcecaoNegocio
    {
        if (chegaExistencia(feriado) == false)
        {
            em.persist(feriado);
        } 
        else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
   public List<Feriado> listar()
    {
        em.flush();
        return em.createQuery("select f from Feriado f", Feriado.class).getResultList();
    }

    public boolean chegaExistencia(Feriado feriado) 
    {
        TypedQuery<Feriado> query;
        query = em.createQuery("select f from Feriado f where f.data_do_feriado = ?1", Feriado.class);
        query.setParameter(1, feriado.getData_do_feriado());
        List<Feriado> responsaveis = query.getResultList(); 

        return !responsaveis.isEmpty();
    }
    
    public void atualizar(Feriado feriado) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(feriado) == true)
        {
            em.merge(feriado);
            
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
        }
    }
}
