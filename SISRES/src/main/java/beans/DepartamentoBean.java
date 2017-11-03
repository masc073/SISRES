package beans;

import dominio.Departamento;
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
import servico.DepartamentoServico;

@ManagedBean(name = "departamentoBean")
@ViewScoped
public class DepartamentoBean implements Serializable
{

    @EJB
    private DepartamentoServico departamentoServico;

    private List<Departamento> departamentos = new ArrayList<>();

    private Departamento departamento;

    public DepartamentoBean()
    {
        departamento = new Departamento();
    }

    public void salvar()
    {
        try
        {
            departamentoServico.salvar(departamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Departamento cadastrado com Sucesso!");
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

        departamento = new Departamento();
        listar();
    }

    public void listar()
    {
        departamentos = departamentoServico.listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        departamento = (Departamento) event.getObject();
        listar();

        try
        {
            departamentoServico.atualizar(departamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Departamento alterado com Sucesso!");
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

    public void remover(Departamento departamento)
    {
        try
        {
            departamentoServico.remover(departamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Departamento removido com Sucesso!");

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

    public DepartamentoServico getDepartamentoServico()
    {
        return departamentoServico;
    }

    public void setDepartamentoServico(DepartamentoServico departamentoServico)
    {
        this.departamentoServico = departamentoServico;
    }

    public List<Departamento> getDepartamentos()
    {
        if (departamentos.isEmpty())
        {
            departamentos = departamentoServico.listar();
        }
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos)
    {
        this.departamentos = departamentos;
    }

    public Departamento getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(Departamento departamento)
    {
        this.departamento = departamento;
    }
}
