package servico;

import dominio.Participante;
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
public class ParticipanteServico extends Servico
{
    
    public void salvar(Participante participante) throws ExcecaoNegocio
    {
        if (chegaExistencia(participante) == false)
        {
            em.persist(participante);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Participante> listar()
    {
        em.flush();
        return em.createQuery("select f from Participante f", Participante.class).getResultList();
    }

    public boolean chegaExistencia(Participante participante)
    {
        TypedQuery<Participante> query;

        if (participante.getId() == null) // Inserir
        {
            query = em.createQuery("select f from Participante f where f.nome = ?1", Participante.class);
            query.setParameter(1, participante.getNome());
        } 
        else // Atualizar
        {
            query = em.createQuery("select f from Participante f where f.nome = ?1 and f.id != ?2", Participante.class);
            query.setParameter(1, participante.getNome());
            query.setParameter(2, participante.getId());
        }

        List<Participante> Participantes = query.getResultList();

        if (Participantes.isEmpty())
        {
            return false;
        } else
        {
            return true;
        }
    }

    public void atualizar(Participante Participante) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(Participante) == false)
        {
            em.merge(Participante);

        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(Participante participante)
    {
        Participante f = (Participante) em.find(Participante.class, participante.getId());
        em.remove(f);
    }
}
