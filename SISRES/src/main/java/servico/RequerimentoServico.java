package servico;

import dominio.Atividade;
import dominio.Requerimento;
import dominio.Responsavel;
import dominio.SituacaoAtividade;
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
        if (chegaExistencia(Requerimento) == false) {
            em.persist(Requerimento);
        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public List<Requerimento> listar_finalizados(Responsavel usuario_logado)
    {
        em.flush();

//        if (usuario_logado.isServidor()) {
//            return em.createQuery("select f from Requerimento f where f.finalizado = true ", Requerimento.class).getResultList();
//        }
//        else {
        return em.createQuery("select f from Requerimento f where f.finalizado = true", Requerimento.class).getResultList();
//        }
    }

    public List<Requerimento> listar(Responsavel usuarioLogado)
    {
        TypedQuery<Requerimento> query;
        em.flush();
        // PARA USUÁRIO
//        query = em.createQuery("select f from Requerimento f where f.finalizado = false and f.solicitante = ?1 ", Requerimento.class);
//        query.setParameter(1, usuarioLogado);

// PARA SERVIDORES
        query = em.createQuery("select f from Requerimento f where f.finalizado = false and f.estadoAtual.atividademodelo.departamento = ?1 ", Requerimento.class);
        query.setParameter(1, usuarioLogado.getDepartamento());

        return query.getResultList();
    }

    public boolean chegaExistencia(Requerimento Requerimento)
    {
        TypedQuery<Requerimento> query;

        // Quando adicionar a autorização checar o usuário também.
        if (Requerimento.getId() == null) // Inserir
        {
            query = em.createQuery("select f from Requerimento f where f.processo = ?1 and f.matriculaAluno = ?2 and f.finalizado = false", Requerimento.class);
            query.setParameter(1, Requerimento.getProcesso());
            query.setParameter(2, Requerimento.getMatriculaAluno());

        }
        else {
            query = em.createQuery("select f from Requerimento f where f.processo = ?1 and f.matriculaAluno = ?2 and f.id != ?3 and f.finalizado = false", Requerimento.class);
            query.setParameter(1, Requerimento.getProcesso());
            query.setParameter(2, Requerimento.getMatriculaAluno());
            query.setParameter(3, Requerimento.getId());
        }

        List<Requerimento> Requerimentos = query.getResultList();

        if (Requerimentos.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    public void atualizar(Requerimento Requerimento) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(Requerimento) == false) {
            em.merge(Requerimento);

        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    public void remover(Requerimento Requerimento)
    {
        Requerimento f = (Requerimento) em.find(Requerimento.class, Requerimento.getId());
        em.remove(f);
    }

    public Requerimento getRequerimento(Long id)
    {
        TypedQuery<Requerimento> query;
        query = em.createQuery("select f from Requerimento f where f.id = ?1", Requerimento.class);
        query.setParameter(1, id);

        return query.getSingleResult();
    }

}
