
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
        query = em.createQuery("select f from Feriado f where f.nome = ?1 and f.id != ?2", Feriado.class);
        query.setParameter(1, feriado.getNome());        
        query.setParameter(2, feriado.getId());
        List<Feriado> feriados = query.getResultList(); 
        
        if(feriados.isEmpty())
            return false;
        else 
            return true;
    }
    
    public void atualizar(Feriado feriado) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(feriado) == false)
        {
            em.merge(feriado);
            
        } 
        else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
     
    public void remover(Feriado feriado) 
    {
            Feriado f = (Feriado) em.find(Feriado.class, feriado.getId());       
            em.remove(f);
    }

   
}
