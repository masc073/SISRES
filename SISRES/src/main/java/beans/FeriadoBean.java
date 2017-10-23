package beans;

import dominio.Feriado;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.FeriadoServico;

@ManagedBean(name = "feriadoBean")
@ViewScoped
public class FeriadoBean implements Serializable
{
    @EJB
    private FeriadoServico feriadoServico;

    private List<Feriado> feriados;

    private Feriado feriado;
    
    public FeriadoBean()
    {
        feriado = new Feriado();
    }
    
    public void salvar()
    {
        try
        {
            feriadoServico.salvar(feriado);
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

        feriado = new Feriado();  
        listar();
    }
    
     public void editar(RowEditEvent event)
     {
        feriado = (Feriado) event.getObject();
        listar(); 
       
        try
        {
           feriadoServico.atualizar(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Alterado com sucesso!");
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
    
    public void listar()
    {
        feriados = feriadoServico.listar();
    }
    
    public FeriadoServico getFeriadoServico()
    {
        return feriadoServico;
    }

    public void setFeriadoServico(FeriadoServico feriadoServico)
    {
        this.feriadoServico = feriadoServico;
    }

    public List<Feriado> getFeriados()
    {
        return feriados;
    }

    public void setFeriados(List<Feriado> feriados)
    {
        this.feriados = feriados;
    }

    public Feriado getFeriado()
    {
        return feriado;
    }

    public void setFeriado(Feriado feriado)
    {
        this.feriado = feriado;
    }
    
    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}