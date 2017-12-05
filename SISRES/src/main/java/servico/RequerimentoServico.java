
package servico;

import dominio.Requerimento;
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
public class RequerimentoServico extends Servico
{
    
    public void salvar(Requerimento Requerimento) throws ExcecaoNegocio
    {
        if (chegaExistencia(Requerimento) == false)
        {
            em.persist(Requerimento);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Requerimento> listar()
    {
        em.flush();
        return em.createQuery("select f from Requerimento f", Requerimento.class).getResultList();
    }

    public boolean chegaExistencia(Requerimento Requerimento)
    {
        TypedQuery<Requerimento> query;
        
        // Quando adicionar a autorização checar o usuário também.
        
        if (Requerimento.getId() == null) // Inserir
        {
            query = em.createQuery("select f from Requerimento f where f.processo = ?1", Requerimento.class);
            query.setParameter(1, Requerimento.getProcesso());

        } 
        else // Atualizar
        {
            query = em.createQuery("select f from Requerimento f where f.processo = ?1 and f.id != ?2", Requerimento.class);
            query.setParameter(1, Requerimento.getProcesso());
            query.setParameter(2, Requerimento.getId());
        }

        List<Requerimento> Requerimentos = query.getResultList();

        if (Requerimentos.isEmpty())
        {
            return false;
        } else
        {
            return true;
        }
    }

    public void atualizar(Requerimento Requerimento) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(Requerimento) == false)
        {
            em.merge(Requerimento);

        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(Requerimento Requerimento)
    {
        Requerimento f = (Requerimento) em.find(Requerimento.class, Requerimento.getId());
        em.remove(f);
    }

}
