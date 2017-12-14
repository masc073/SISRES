package servico;

import dominio.Atividade;
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
public class AtividadeServico extends Servico
{
    
    public void salvar(Atividade atividade) throws ExcecaoNegocio
    {
        if (chegaExistencia(atividade) == false)
        {
            em.persist(atividade);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Atividade> listar()
    {
        em.flush();
        return em.createQuery("select a from Atividade a", Atividade.class).getResultList();
    }

    public boolean chegaExistencia(Atividade atividade)
    {
        TypedQuery<Atividade> query;

        if (atividade.getId() == null) // Inserir atividade já com um processo.
        {
            query = em.createQuery("select a from Atividade a where a.nome = ?1 and a.id_processo = ?2", Atividade.class);
//            query.setParameter(1, atividade.getNome());
//            query.setParameter(2, atividade.getProcesso().getId());

        } 
        else // Atualizar
        {
            query = em.createQuery("select d from Atividade d where a.nome = ?1 and a.id_processo = ?2 and d.id != ?3", Atividade.class);
//            query.setParameter(1, atividade.getNome());
//            query.setParameter(2, atividade.getProcesso().getId());
            query.setParameter(3, atividade.getId());
        }

        List<Atividade> responsaveis = query.getResultList();

        if (responsaveis.isEmpty())
        {
            return false;
        } else
        {
            return true;
        }
    }

    public void atualizar(Atividade atividade) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(atividade) == false)
        {
            em.merge(atividade);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(Atividade atividade)
    {
        Atividade d = (Atividade) em.find(Atividade.class, atividade.getId());
        em.remove(d);
    }
}
