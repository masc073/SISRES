package servico;

import dominio.UnidadeOrganizacional;
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
public class UnidadeOrganizacionalServico extends Servico
{

    public void salvar(UnidadeOrganizacional unidade_organizacional) throws ExcecaoNegocio
    {
        if (chegaExistencia(unidade_organizacional) == false)
        {
            em.persist(unidade_organizacional);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<UnidadeOrganizacional> listar()
    {
        em.flush();
        return em.createQuery("select d from UnidadeOrganizacional d", UnidadeOrganizacional.class).getResultList();
    }

    public boolean chegaExistencia(UnidadeOrganizacional unidade_organizacional)
    {
        TypedQuery<UnidadeOrganizacional> query;

        if (unidade_organizacional.getId() == null) // Inserir
        {
            query = em.createQuery("select d from UnidadeOrganizacional d where d.sigla = ?1", UnidadeOrganizacional.class);
            query.setParameter(1, unidade_organizacional.getSigla());
        } 
        else // Atualizar
        {
            query = em.createQuery("select d from UnidadeOrganizacional d where d.sigla = ?1 and d.id != ?2", UnidadeOrganizacional.class);
            query.setParameter(1, unidade_organizacional.getSigla());
            query.setParameter(2, unidade_organizacional.getId());
        }

        List<UnidadeOrganizacional> responsaveis = query.getResultList();

        if (responsaveis.isEmpty())
        {
            return false;
        } else
        {
            return true;
        }
    }

    public void atualizar(UnidadeOrganizacional unidade_organizacional) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(unidade_organizacional) == false)
        {
            em.merge(unidade_organizacional);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(UnidadeOrganizacional unidade_organizacional)
    {
        UnidadeOrganizacional d = (UnidadeOrganizacional) em.find(UnidadeOrganizacional.class, unidade_organizacional.getId());
        em.remove(d);
    }
}
