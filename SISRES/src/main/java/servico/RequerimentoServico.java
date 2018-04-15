package servico;

import dominio.Atividade;
import dominio.Requerimento;
import dominio.Responsavel;
import dominio.SituacaoAtividade;
import dominio.Titulos;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * Responsável por realizar as salvar, atualizar, listar e remover o
 * requerimento no banco de dados.
 *
 * @author Natália Amâncio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RequerimentoServico extends Servico
{

    /**
     * Salva o requerimento no banco de dados.
     *
     * @param Requerimento Requerimento que será adicionado no banco de dados.
     * @exception ExcecaoNegocio Lançada caso o objeto já exista no banco de
     * dados
     */
    public void salvar(Requerimento Requerimento) throws ExcecaoNegocio
    {
        if (chegaExistencia(Requerimento) == false) {
            em.persist(Requerimento);
        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    /**
     * Retorna a lista de todos os requerimentos finalizados
     *
     * @param usuario_logado
     * @return List<Requerimento>
     */
    public List<Requerimento> listar_finalizados(Responsavel usuario_logado)
    {
        TypedQuery<Requerimento> query;
        em.flush();

        if (usuario_logado.getTitulo() == Titulos.Aluno) {

            query = em.createQuery("select f from Requerimento f where f.finalizado = true AND f.solicitante.email = ?1", Requerimento.class);
            query.setParameter(1, usuario_logado.getEmail());
        }
        else {
//            query = em.createQuery("select f from Requerimento f where f.finalizado = true AND f.atividades.atividademodelo.unidade_organizacional.responsavel.email = ?1", Requerimento.class);
            query = em.createQuery("select f from Requerimento f where f.finalizado = true AND f.id IN ( SELECT atv.requerimento.id FROM Atividade atv WHERE atv.atividademodelo.unidade_organizacional.responsavel.email = ?1 ) ", Requerimento.class);
            query.setParameter(1, usuario_logado.getEmail());
        }

        return query.getResultList();

    }

    /**
     * Lista todos os requerimentos abertos para o usuário logado
     *
     * @return List<Requerimento> Lista de requerimentos
     * @param usuarioLogado
     */
    public List<Requerimento> fila_requerimentos(Responsavel usuarioLogado)
    {
        TypedQuery<Requerimento> query;
        em.flush();
        query = em.createQuery(" SELECT req FROM Requerimento req INNER JOIN req.atividades  atv INNER JOIN atv.atividademodelo atm INNER JOIN atm.unidade_organizacional un INNER JOIN un.responsavel res WHERE req.finalizado = false AND atv.situacao = ?2 AND res.email = ?1 ", Requerimento.class);

        query.setParameter(1, usuarioLogado.getEmail());
        query.setParameter(2, SituacaoAtividade.Andamento);
        return query.getResultList();
    }

    public List<Requerimento> listar(Responsavel usuarioLogado)
    {
        TypedQuery<Requerimento> query;
        em.flush();
        query = em.createQuery(" SELECT r FROM Requerimento r WHERE r.solicitante.email = ?1 AND r.finalizado = false", Requerimento.class);

        query.setParameter(1, usuarioLogado.getEmail());
        return query.getResultList();
    }

    /**
     * Verifica se o requerimento já existe no banco de dados.
     *
     * @param Requerimento
     * @return boolean True ou False.
     */
    public boolean chegaExistencia(Requerimento Requerimento)
    {
        TypedQuery<Requerimento> query;

        // Quando adicionar a autorização checar o usuário também.
        if (Requerimento.getId() == null) // Inserir
        {
            query = em.createQuery("select f from Requerimento f where f.processo = ?1 and f.solicitante.matricula = ?2 and f.finalizado = false", Requerimento.class);
            query.setParameter(1, Requerimento.getProcesso());
            query.setParameter(2, Requerimento.getSolicitante().getMatricula());

        }
        else {
            query = em.createQuery("select f from Requerimento f where f.processo = ?1 and f.solicitante.matricula = ?2 and f.id != ?3 and f.finalizado = false", Requerimento.class);
            query.setParameter(1, Requerimento.getProcesso());
            query.setParameter(2, Requerimento.getSolicitante().getMatricula());
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

    /**
     * Atualiza informações do Requerimento no banco de dados.
     *
     * @exception ExcecaoNegocio
     * @param Requerimento
     */
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

    /**
     * Remove Requerimento do banco de dados
     *
     * @param Requerimento Requerimento a ser removida
     */
    public void remover(Requerimento Requerimento)
    {
        Requerimento f = (Requerimento) em.find(Requerimento.class, Requerimento.getId());
        em.remove(f);
    }

    /**
     * Retorna requerimento pelo id
     *
     * @param id
     * @return Requerimento
     */
    public Requerimento getRequerimento(Long id)
    {
        TypedQuery<Requerimento> query;
        query = em.createQuery("select f from Requerimento f where f.id = ?1", Requerimento.class);
        query.setParameter(1, id);

        return query.getSingleResult();
    }

}
