package servico;

import dominio.Feriado;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

/**
 * Responsável por salvar, listar, atualizar e remover feriados no banco de dados.
 *
 * @author Natália Amâncio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FeriadoServico extends Servico
{

    /** Salva o feriado no banco de dados.
     * @param feriado Feriado que será adicionado no banco de dados.
     * @exception ExcecaoNegocio Lançada caso o objeto já exista no banco de dados
     */
    public void salvar(Feriado feriado) throws ExcecaoNegocio
    {
        if (chegaExistencia(feriado) == false)
        {
            em.persist(feriado);
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
    /** Lista todas os Feriados cadastradas no banco de dados.
     * @return List<Feriado> Lista de feriados
     */
    public List<Feriado> listar()
    {
        em.flush();
        return em.createQuery("select f from Feriado f", Feriado.class).getResultList();
    }

    /** Verifica se o feriado já existe no banco de dados.
     * @param feriado 
     * @return boolean True ou False.
     */
    public boolean chegaExistencia(Feriado feriado)
    {
        TypedQuery<Feriado> query;

        if (feriado.getId() == null) // Inserir
        {
            query = em.createQuery("select f from Feriado f where f.nome = ?1", Feriado.class);
            query.setParameter(1, feriado.getNome());
        } 
        else // Atualizar
        {
            query = em.createQuery("select f from Feriado f where f.nome = ?1 and f.id != ?2", Feriado.class);
            query.setParameter(1, feriado.getNome());
            query.setParameter(2, feriado.getId());
        }

        List<Feriado> feriados = query.getResultList();

        if (feriados.isEmpty())
        {
            return false;
        } else
        {
            return true;
        }
    }
    
    /** Atualiza informações do feriado no banco de dados.
     * @exception  ExcecaoNegocio
     * @param  feriado
     */
    public void atualizar(Feriado feriado) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(feriado) == false)
        {
            em.merge(feriado);

        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }

    /** Remove feriado do banco de dados
     * @param feriado Feriado a ser removida
     */
    public void remover(Feriado feriado)
    {
        Feriado f = (Feriado) em.find(Feriado.class, feriado.getId());
        em.remove(f);
    }

}
