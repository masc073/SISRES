package servico;

import dominio.Processo;
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
public class ProcessoServico extends Servico
{

    public void salvar(Processo processo) throws ExcecaoNegocio
    {
        if (chegaExistencia(processo) == false)
        {
            em.persist(processo);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Processo> listar()
    {
        em.flush();
        return em.createQuery("select p from Processo p", Processo.class).getResultList();
    }
    
    

    public boolean chegaExistencia(Processo processo)
    {
        TypedQuery<Processo> query;

        if (processo.getId() == null) // Inserir
        {
            query = em.createQuery("select p from Processo p where p.nome = ?1", Processo.class);
            query.setParameter(1, processo.getNome());
        } else // Atualizar
        {
            query = em.createQuery("select p from Processo p where p.nome = ?1 and p.id != ?2", Processo.class);
            query.setParameter(1, processo.getNome());
            query.setParameter(2, processo.getId());
        }

        List<Processo> Processos = query.getResultList();

        if (Processos.isEmpty())
        {
            return false;
        } else
        {
            return true;
        }
    }

    public void atualizar(Processo processo) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(processo) == false)
        {
            em.merge(processo);

        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(Processo processo)
    {
        Processo f = (Processo) em.find(Processo.class, processo.getId());
        em.remove(f);
    }
}
