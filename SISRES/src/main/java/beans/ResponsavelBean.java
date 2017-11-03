package beans;

import dominio.Responsavel;
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
import servico.ResponsavelServico;

@ManagedBean(name = "responsavelBean")
@ViewScoped
public class ResponsavelBean implements Serializable
{

    @EJB
    private ResponsavelServico responsavelServico;

    private List<Responsavel> responsaveis = new ArrayList<>();

    private Responsavel responsavel;

    String senhaConfirmacao;

    public void salvar()
    {
        if (responsavel.getSenhaDigital().equals(senhaConfirmacao))
        {
            try
            {
                responsavelServico.salvar(responsavel);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável cadastrado com Sucesso!");
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
        else
        {
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Senhas diferentes!");
        }

        responsavel = new Responsavel();
        listar();
    }

    public void listar()
    {
        responsaveis = responsavelServico.listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        responsavel = (Responsavel) event.getObject();
        listar();

        try
        {
            responsavelServico.atualizar(responsavel);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável alterado com Sucesso!");
            listar();
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

    public void remover(Responsavel responsavel)
    {
        try
        {
            responsavelServico.remover(responsavel);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável removido com Sucesso!");

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

    public ResponsavelBean()
    {
        responsavel = new Responsavel();
    }

    public ResponsavelServico getResponsavelServico()
    {
        return responsavelServico;
    }

    public void setResponsavelServico(ResponsavelServico responsavelServico)
    {
        this.responsavelServico = responsavelServico;
    }

    public List<Responsavel> getResponsaveis()
    {
        if (responsaveis.isEmpty())
        {
            responsaveis = responsavelServico.listar();
        }
        return responsaveis;
    }

    public void setResponsaveis(List<Responsavel> responsaveis)
    {
        this.responsaveis = responsaveis;
    }

    public Responsavel getResponsavel()
    {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel)
    {
        this.responsavel = responsavel;
    }

    public String getSenhaConfirmacao()
    {
        return senhaConfirmacao;
    }

    public void setSenhaConfirmacao(String senhaConfirmacao)
    {
        this.senhaConfirmacao = senhaConfirmacao;
    }

}
