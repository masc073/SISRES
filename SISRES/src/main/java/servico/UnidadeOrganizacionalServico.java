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

/**
 * Responsável por realizar as operações da Unidade Organizacional no banco de dados.
 *
 * @author Natália Amâncio
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UnidadeOrganizacionalServico extends Servico
{

    /** Salva a Unidade Organizacional no banco de dados.
     * @param unidade_organizacional Unidade Organizacional que será adicionada no banco de dados.
     * @exception ExcecaoNegocio Lançada caso o objeto já exista no banco de dados
     */
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

    /** Lista todas as Unidade Organizacionais cadastradas no banco de dados.
     * @return List<UnidadeOrganizacional> Lista das Unidade Organizacionais
     */
    public List<UnidadeOrganizacional> listar()
    {
        em.flush();
        return em.createQuery("select d from UnidadeOrganizacional d", UnidadeOrganizacional.class).getResultList();
    }

    /** Verifica se o Unidade organizacionl já existe no banco de dados.
     * @param unidade_organizacional 
     * @return boolean True ou False.
     */
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
    
    /** Atualiza informações do Unidade Organizacional no banco de dados.
     * @exception  ExcecaoNegocio
     * @param  unidade_organizacional
     */
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

    /** Remove Unidade Organizacional do banco de dados
     * @param unidade_organizacional Unidade Organizacional a ser removidO
     */
    public void remover(UnidadeOrganizacional unidade_organizacional)
    {
        UnidadeOrganizacional d = (UnidadeOrganizacional) em.find(UnidadeOrganizacional.class, unidade_organizacional.getId());
        em.remove(d);
    }
}
