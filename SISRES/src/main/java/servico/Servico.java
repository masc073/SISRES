package servico;

import dominio.EntidadeNegocio;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

public abstract class Servico<T extends EntidadeNegocio>
{

    @PersistenceContext(unitName = "sisres", type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;


    public void salvar(T entidade) throws ExcecaoNegocio
    {
        try
        {
            if (checarExistencia(entidade))
            {
                throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
            } 
            else
            {
                em.persist(entidade);
            }
        } 
        catch (NonUniqueResultException e)
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }

    }

    public void alterar(T entidade) throws ExcecaoNegocio
    {
       try
        {
            if (!checarExistencia(entidade))
            {
                throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
            } 
            else
            {
                em.merge(entidade);
            }
        } 
        catch (NonUniqueResultException e)
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(T entidade) throws ExcecaoNegocio
    {
        if (checarAssociacao(entidade) == false)
        {
            entidade = em.find(getClasse(), entidade.getId());
            em.remove(entidade);

        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
        }
    }

    public List<T> listarTodos()
    {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select e ");
        jpql.append(" from ");
        jpql.append(getClasse().getSimpleName());
        jpql.append(" as e ");
        TypedQuery<T> query = em.createQuery(jpql.toString(), getClasse());
        return query.getResultList();
    }

    public abstract Class<T> getClasse();

    public abstract T getEntidade();

    public abstract Boolean checarExistencia(T entidade);

    public abstract Boolean checarAssociacao(T entidade);
}
