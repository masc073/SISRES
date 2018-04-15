package servico;

import dominio.Responsavel;
import dominio.Titulos;
import excecao.ExcecaoNegocio;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.ejb.TransactionManagement;
import static javax.ejb.TransactionManagementType.CONTAINER;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Responsável por realizar as operações do responsável no banco de dados.
 *
 * @author Natália Amâncio
 */
@Stateless
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class ResponsavelServico extends Servico
{
    /** Salva o reponsável no banco de dados.
     * @param responsavel reponsável que será adicionado no banco de dados.
     * @exception ExcecaoNegocio Lançada caso o objeto já exista no banco de dados
     */
    public void salvar(Responsavel responsavel) throws ExcecaoNegocio
    {
        if (chegaExistencia(responsavel) == false) {
            em.persist(responsavel);
        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
    /** Lista todos os responsáveis cadastrados no banco de dados.
     * @return List<Responsavel> Lista de Responsavéis
     */
    public List<Responsavel> listar()
    {
        em.flush();
        return em.createQuery("select r from Responsavel r", Responsavel.class).getResultList();
    }
    
    /** Retorna Responsável pelo email
     * @param email
     * @return Responsavel
     */
    public Responsavel getResponsavelByEmail(String email)
    {
        Responsavel responsavel;
        TypedQuery<Responsavel> query;

        try {
            query = em.createQuery("select r from Responsavel r where r.email = ?1", Responsavel.class);
            query.setParameter(1, email);
            responsavel = query.getSingleResult();
        }
        catch (NoResultException e) {
            responsavel = null;
        }

        return responsavel;
    }
  
    /** Verifica se o responsavel já existe no banco de dados.
     * @param responsavel 
     * @return boolean True ou False.
     */
    public boolean chegaExistencia(Responsavel responsavel)
    {
        TypedQuery<Responsavel> query;

        if (responsavel.getId() == null) {
            query = em.createQuery("select r from Responsavel r where r.email = ?1", Responsavel.class
            );
            query.setParameter(1, responsavel.getEmail());

        }
        else {
            query = em.createQuery("select r from Responsavel r where r.email = ?1 and r.id != ?2", Responsavel.class
            );
            query.setParameter(1, responsavel.getEmail());
            query.setParameter(2, responsavel.getId());
        }

        List<Responsavel> responsaveis = query.getResultList();

        return !responsaveis.isEmpty();
    }
    
    /** Remove responsavel do banco de dados
     * @param responsavel Responsavel a ser removidO
     */
    public void remover(Responsavel responsavel)
    {
        Responsavel r = (Responsavel) em.find(Responsavel.class,
                 responsavel.getId());
        em.remove(r);
    }
    
    /** Atualiza informações do responsavel no banco de dados.
     * @exception  ExcecaoNegocio
     * @param  responsavel
     */
    public void atualizar(Responsavel responsavel) throws ExcecaoNegocio
    {
        em.flush();
        if (chegaExistencia(responsavel) == false) {
            em.merge(responsavel);
        }
        else {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
    /** Lista os responsáveis que não foram aprovados como líder.
     * @return List<Responsavel> Lista de Responsavéis
     */
    public List<Responsavel> listar_nao_aprovados_lider()
    {
        em.flush();

        return em.createQuery("select r from Responsavel r where r.aprovado = false and r.lider = true ", Responsavel.class
        ).getResultList();
    }

    /** Lista os responsáveis que não foram aprovados.
     * @param usuario_logado
     * @return List<Responsavel> Lista de Responsavéis
     */
    public List<Responsavel> listar_nao_aprovados(Responsavel usuario_logado)
    {
        em.flush();
        TypedQuery<Responsavel> query;

        query = em.createQuery("select r from Responsavel r where r.aprovado = false", Responsavel.class );
       
        List<Responsavel> responsaveis = query.getResultList();

        return responsaveis;

    }
    
    public List<Responsavel> listar_servidores()
    {
        em.flush();
        TypedQuery<Responsavel> query;

        query = em.createQuery("select r from Responsavel r where r.titulo != ?1 AND r.titulo != ?2  ", Responsavel.class );
        query.setParameter(1, Titulos.Administrador);        
        query.setParameter(2, Titulos.Aluno);

        List<Responsavel> responsaveis = query.getResultList();

        return responsaveis;

    }
    
}
