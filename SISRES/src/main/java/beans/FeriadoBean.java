package beans;

import dominio.Feriado;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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

    protected List<Feriado> feriados = new ArrayList<>();

    protected Feriado feriado;

    public FeriadoBean()
    {
        feriado = new Feriado();
    }

    public void salvar()
    {
        try
        {
            feriadoServico.salvar(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Feriado cadastrado com Sucesso!");
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

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        feriado = (Feriado) event.getObject();
        listar();

        try
        {
            feriadoServico.atualizar(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Feriado alterado com Sucesso!");
            listar();
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
        listar();
    }
 
    public void remover(Feriado feriado) 
    {
        try
        {
            feriadoServico.remover(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Feriado removido com Sucesso!");
            
        } 
        catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
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
        if (feriados.isEmpty())
            feriados = feriadoServico.listar();
        
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
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }
}
