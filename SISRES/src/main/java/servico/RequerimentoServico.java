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
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * Responsável por realizar as salvar, atualizar, listar e remover o requerimento no banco de dados.
 *
 * @author Natália Amâncio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RequerimentoServico extends Servico
{

    /** Salva o requerimento no banco de dados.
     * @param Requerimento Requerimento que será adicionado no banco de dados.
     * @exception ExcecaoNegocio Lançada caso o objeto já exista no banco de dados
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

    /** Retorna a lista de todos os requerimentos finalizados
     * @param usuario_logado
     * @return List<Requerimento>
     */
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

    /** Lista todos os requerimentos abertos para o usuário logado
     * @return List<Requerimento> Lista de requerimentos
     * @param usuarioLogado
     */
    public List<Requerimento> listar(Responsavel usuarioLogado)
    {
        TypedQuery<Requerimento> query;
        em.flush();

        query = em.createQuery("select f from Requerimento f where f.finalizado = false AND f.estadoAtual.atividademodelo.unidade_organizacional.responsavel.email = ?1 ", Requerimento.class);
        query.setParameter(1, usuarioLogado.getEmail());
        return query.getResultList();
    }

    /** Verifica se o requerimento já existe no banco de dados.
     * @param Requerimento 
     * @return boolean True ou False.
     */
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

    /** Atualiza informações do Requerimento no banco de dados.
     * @exception  ExcecaoNegocio
     * @param  Requerimento
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

    /** Remove Requerimento do banco de dados
     * @param Requerimento Requerimento a ser removida
     */
    public void remover(Requerimento Requerimento)
    {
        Requerimento f = (Requerimento) em.find(Requerimento.class, Requerimento.getId());
        em.remove(f);
    }

    /** Retorna requerimento pelo id
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
