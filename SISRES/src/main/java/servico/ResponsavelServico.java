package servico;

import dominio.Responsavel;
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
public class ResponsavelServico extends Servico {
    
    public void salvar(Responsavel responsavel) throws ExcecaoNegocio
    {
        if (chegaExistencia(responsavel) == false)
        {
            em.persist(responsavel);
        } 
        else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
   public List<Responsavel> listar()
    {
        em.flush();
        return em.createQuery("select r from Responsavel r", Responsavel.class).getResultList();
    }

    public boolean chegaExistencia(Responsavel responsavel) 
    {
        TypedQuery<Responsavel> query;
        query = em.createQuery("select r from Responsavel r where r.nome = ?1", Responsavel.class);
        query.setParameter(1, responsavel.getNome());
        List<Responsavel> responsaveis = query.getResultList();

        return !responsaveis.isEmpty();
    }
    
    public void remover(Responsavel responsavel) 
    {
//            Responsavel r = (Buffet) em.find(Buffet.class, buffet.getId());       
            em.remove(responsavel);
      
    }
    
    public void atualizar(Responsavel responsavel) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(responsavel) == true)
        {
            em.merge(responsavel);
            
        } else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
        }
    }
}
