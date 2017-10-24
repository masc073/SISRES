package servico;

import dominio.EntidadeNegocio;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ResponsavelServico extends Servico {

    @Override
    public Class getClasse()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntidadeNegocio getEntidade()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean checarExistencia(EntidadeNegocio entidade)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean checarAssociacao(EntidadeNegocio entidade)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    
//    public void salvar(Responsavel responsavel) throws ExcecaoNegocio
//    {
//        if (chegaExistencia(responsavel) == false)
//        {
//            em.persist(responsavel);
//        } 
//        else
//        {
//            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
//        }
//    }
//    
//   public List<Responsavel> listar()
//    {
//        em.flush();
//        return em.createQuery("select r from Responsavel r", Responsavel.class).getResultList();
//    }
//
//    public boolean chegaExistencia(Responsavel responsavel) 
//    {
//        TypedQuery<Responsavel> query;
//        query = em.createQuery("select r from Responsavel r where r.nome = ?1", Responsavel.class);
//        query.setParameter(1, responsavel.getNome());
//        List<Responsavel> responsaveis = query.getResultList();
//
//        return !responsaveis.isEmpty();
//    }
//    
//    public void remover(Responsavel responsavel) 
//    {
//            Responsavel r = (Responsavel) em.find(Responsavel.class, responsavel.getId());       
//            em.remove(r);
//      
//    }
//    
//    public void atualizar(Responsavel responsavel) throws ExcecaoNegocio
//    {
//        em.flush();
//
//        if (chegaExistencia(responsavel) == true)
//        {
//            em.merge(responsavel);
//            
//        } else
//        {
//            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
//        }
//    }
}
