package beans;

import dominio.Responsavel;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import servico.ResponsavelServico;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "responsavelBean")
@SessionScoped
public class ResponsavelBean implements Serializable
{
    @EJB
    private ResponsavelServico responsavelServico;

    private List<Responsavel> responsaveis;

    private Responsavel responsavel;

    private String senha_confirmacao;

    public ResponsavelBean()
    {
        responsavel = new Responsavel();
    }

    public void salvar()
    {
        if (responsavel.getSenhaDigital().equals(senha_confirmacao))
        {
            try
            {
                responsavelServico.salvar(responsavel);
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

        } else
        {
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Senhas diferentes!");
        }

        responsavel = new Responsavel();  
        listar();
    }
 
    
    public void editar(Responsavel responsavel)
    {
        listar(); 
       
        try
        {
            responsavelServico.atualizar(responsavel);
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
   
    public void remover(Responsavel responsavel)
    {
        try
        {
            responsavelServico.remover(responsavel);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Removido com sucesso!");
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


    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void listar()
    {
        responsaveis = responsavelServico.listar();
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

    public String getSenha_confirmacao()
    {
        return senha_confirmacao;
    }

    public void setSenha_confirmacao(String senha_confirmacao)
    {
        this.senha_confirmacao = senha_confirmacao;
    }
}
