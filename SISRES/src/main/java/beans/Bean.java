package beans;

import dominio.EntidadeNegocio;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.Servico;

public abstract class Bean<T extends EntidadeNegocio> implements Serializable
{

    protected Servico<T> servico;

    protected List<T> entidades = new ArrayList<>();

    protected T entidade;
    
    @PostConstruct
    public void inicializar() {   
        setServico();
        getEntidadeNegocio();
    }

    public void listarTodos()
    {
        entidades = servico.listarTodos();
    }

    public void getEntidadeNegocio()
    {
        entidade = servico.getEntidade();
    }
    
    
    public void editar(RowEditEvent event)
    {
        entidade = (T) event.getObject();
      
        try
        {
            servico.alterar(entidade);
            listarTodos();
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso!");
        } catch (ExcecaoNegocio ex)
        {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());

        } catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
    }
 
    public void remover(T entidade) throws  ExcecaoNegocio
    {
        try
        {
            servico.remover(entidade);
            listarTodos();
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
        } catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
  
    }

    public void cadastrar()
    {
        try
        {
            servico.salvar(entidade);
            listarTodos(); // Atualiza dados na tela.
            getEntidadeNegocio();
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
        } 
        catch (ExcecaoNegocio ex)
        {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } 
        catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
    }

    public abstract void setServico();

    public List<T> getEntidades()
    {  
        entidades = servico.listarTodos();
        return entidades;
    }

    public void setEntidades(List<T> entidades)
    {
        this.entidades = entidades;
    }

    public T getEntidade()
    {
        return entidade;
    }

    public void setEntidade(T entidade)
    {
        this.entidade = entidade;
    }
    
     protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
   
}
