package servico;

import dominio.Departamento;
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
public class DepartamentoServico extends Servico
{
    public void salvar(Departamento departamento) throws ExcecaoNegocio
    {
        if (chegaExistencia(departamento) == false)
        {
            em.persist(departamento);
        } 
        else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
   public List<Departamento> listar()
    {
        em.flush();
        return em.createQuery("select d from Departamento d", Departamento.class).getResultList();
    }

    public boolean chegaExistencia(Departamento departamento) 
    {
        TypedQuery<Departamento> query;
        query = em.createQuery("select d from Departamento d where d.sigla = ?1", Departamento.class);
        query.setParameter(1, departamento.getSigla());
        List<Departamento> responsaveis = query.getResultList(); 

        if(responsaveis.isEmpty())
            return false;
        else
            return true;
    }
    
    public void atualizar(Departamento departamento) throws ExcecaoNegocio
    {
        em.flush();

        if (chegaExistencia(departamento) == false)
        {
            em.merge(departamento);
        } 
        else
        {
            throw new ExcecaoNegocio(ExcecaoNegocio.OBJETO_EXISTENTE);
        }
    }
    
    public void remover(Departamento departamento) 
    {
            Departamento d = (Departamento) em.find(Departamento.class, departamento.getId());       
            em.remove(d);
    }
}
