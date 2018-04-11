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

/**
 * Responsável por realizar as operações da atividade no banco de dados.
 *
 * @author Natália Amâncio
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AtividadeServico extends Servico
{
    /** Salva a atividade no banco de dados.
     * @param atividade Atividade que será adicionada no banco de dados.
     * @exception ExcecaoNegocio Lançada caso o objeto já exista no banco de dados
     */
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

    /** Lista todas as atividades cadastradas no banco de dados.
     * @return List<Atividade> Lista de atividades
     */
    public List<Atividade> listar()
    {
        em.flush();
        return em.createQuery("select a from Atividade a", Atividade.class).getResultList();
    }

    /** Verifica se a atividade já existe no banco de dados.
     * @param atividade 
     * @return boolean True ou False.
     */
    public boolean chegaExistencia(Atividade atividade)
    {
        TypedQuery<Atividade> query;

        if (atividade.getId() == null) // Inserir atividade já com um processo.
        {
            query = em.createQuery("select a from Atividade a where a.atividademodelo.id = ?1 and a.requerimento.id = ?2 and a.situacao = 'Finalizada' ", Atividade.class);
            query.setParameter(1, atividade.getAtividademodelo().getId());
            query.setParameter(2, atividade.getRequerimento().getId());

        } 
        else // Atualizar
        {
            query = em.createQuery("select a from Atividade a where a.atividademodelo.id = ?1 and a.requerimento.id = ?2 and a.situacao != 'Finalizada'", Atividade.class);
            query.setParameter(1, atividade.getAtividademodelo().getId());
            query.setParameter(2, atividade.getRequerimento().getId());
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

    /** Atualiza informações do da atividade no banco de dados.
     * @exception  ExcecaoNegocio
     * @param  atividade
     */
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

    /** Remove atividade do banco de dados
     * @param atividade Atividade a ser removida
     */
    public void remover(Atividade atividade)
    {
        Atividade d = (Atividade) em.find(Atividade.class, atividade.getId());
        em.remove(d);
    }
}
