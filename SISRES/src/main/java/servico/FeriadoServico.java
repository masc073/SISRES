
package servico;


import dominio.Feriado;
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
public class FeriadoServico extends Servico<Feriado>
{

    @Override
    public Class<Feriado> getClasse()
    {
        return Feriado.class;
    }

    @Override
    public Feriado getEntidade()
    {
       return new Feriado();
    }

    @Override
    public Boolean checarExistencia(Feriado entidade)
    {
        TypedQuery<Feriado> query;
        query = em.createQuery("select f from Feriado f where f.data_do_feriado = ?1", Feriado.class);
        query.setParameter(1, entidade.getData_do_feriado());
        List<Feriado> responsaveis = query.getResultList(); 

        return !responsaveis.isEmpty();
    }

    @Override
    public Boolean checarAssociacao(Feriado entidade)
    {
        return false;
    }




//    public void salvar(Feriado feriado) throws ExcecaoNegocio
//    {
//        if (chegaExistencia(feriado) == false)
//        {
//            em.persist(feriado);
//        } 
//        else
//        {
//            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
//        }
//    }
//    
//   public List<Feriado> listar()
//    {
//        em.flush();
//        return em.createQuery("select f from Feriado f", Feriado.class).getResultList();
//    }
//
//    public boolean chegaExistencia(Feriado feriado) 
//    {
//        TypedQuery<Feriado> query;
//        query = em.createQuery("select f from Feriado f where f.data_do_feriado = ?1", Feriado.class);
//        query.setParameter(1, feriado.getData_do_feriado());
//        List<Feriado> responsaveis = query.getResultList(); 
//
//        return !responsaveis.isEmpty();
//    }
//    
//    public void atualizar(Feriado feriado) throws ExcecaoNegocio
//    {
//        em.flush();
//
//        if (chegaExistencia(feriado) == true)
//        {
//            em.merge(feriado);
//            
//        } else
//        {
//            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_INEXISTENTE);
//        }
//    }
//    
//     
//    public void remover(Feriado feriado) 
//    {
//            Feriado f = (Feriado) em.find(Feriado.class, feriado.getId());       
//            em.remove(f);
//      
//    }

   
}
