package beans;

import dominio.Requerimento;
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
import servico.RequerimentoServico;

@ManagedBean(name = "requerimentoBean")
@ViewScoped
public class RequerimentoBean implements Serializable
{
     @EJB
    private RequerimentoServico RequerimentoServico;

    private List<Requerimento> Requerimentos = new ArrayList<>();

    private Requerimento Requerimento;

    public RequerimentoBean()
    {
        Requerimento = new Requerimento();
    }

    public void salvar()
    {
        try
        {
            RequerimentoServico.salvar(Requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Requerimento cadastrado com Sucesso!");
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

        Requerimento = new Requerimento();
        listar();
    }

    public void listar()
    {
        Requerimentos = RequerimentoServico.listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        Requerimento = (Requerimento) event.getObject();
        listar();

        try
        {
            RequerimentoServico.atualizar(Requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Requerimento alterado com Sucesso!");
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

    public void remover(Requerimento requerimento)
    {
        try
        {
            RequerimentoServico.remover(requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Requerimento removido com Sucesso!");

        } catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
    }

    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }

    public RequerimentoServico getRequerimentoServico()
    {
        return RequerimentoServico;
    }

    public void setRequerimentoServico(RequerimentoServico RequerimentoServico)
    {
        this.RequerimentoServico = RequerimentoServico;
    }

    public List<Requerimento> getRequerimentos()
    {
        if (Requerimentos.isEmpty())
        {
            Requerimentos = RequerimentoServico.listar();
        }
        return Requerimentos;
    }

    public void setRequerimentos(List<Requerimento> Requerimentos)
    {
        this.Requerimentos = Requerimentos;
    }

    public Requerimento getRequerimento()
    {
        return Requerimento;
    }

    public void setRequerimento(Requerimento Requerimento)
    {
        this.Requerimento = Requerimento;
    }
}
