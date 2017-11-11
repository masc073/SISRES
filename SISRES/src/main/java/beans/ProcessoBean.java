package beans;

import dominio.Atividade;
import dominio.Processo;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import servico.AtividadeServico;
import servico.ProcessoServico;

@ManagedBean(name = "processoBean")
@SessionScoped
//@ViewScoped
public class ProcessoBean implements Serializable
{

    @EJB
    private ProcessoServico ProcessoServico;
    
    @EJB
    private AtividadeServico atividadeServico;

    protected List<Processo> processos = new ArrayList<>();

    protected Processo processo;

    protected Atividade atividade;
    
    private List<Atividade> atividades = new ArrayList<>();

    public ProcessoBean()
    {
        System.out.println("Criei um novo processo no construtor");
        atividade = new Atividade();
        processo = new Processo();

    }

    public void salvar()
    {
        try
        {
            ProcessoServico.salvar(processo);
            
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Processo cadastrado com Sucesso!");
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
        System.out.println("Criei um novo processo ao salvar");
        processo = new Processo();
        listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        processo = (Processo) event.getObject();
        listar();

        try
        {
            ProcessoServico.atualizar(processo);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Processo alterado com Sucesso!");
            listar();
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
        listar();
    }

    public void remover(Processo processo)
    {
        try
        {
            ProcessoServico.remover(processo);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Processo removido com Sucesso!");

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

    public void listar()
    {
        processos = ProcessoServico.listar();
    }

    public ProcessoServico getProcessoServico()
    {
        return ProcessoServico;
    }

    public void setProcessoServico(ProcessoServico ProcessoServico)
    {
        this.ProcessoServico = ProcessoServico;
    }

    public List<Processo> getProcessos()
    {
        if (processos.isEmpty())
        {
            processos = ProcessoServico.listar();
        }

        return processos;
    }

    public void setProcessos(List<Processo> Processos)
    {
        this.processos = Processos;
    }

    public Processo getProcesso()
    {
        return processo;
    }

    public void setProcesso(Processo Processo)
    {
        this.processo = Processo;
    }

    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }

    public void adicionarAtividadeNoProcesso()
    {
        boolean encontrou = false;

        for (Atividade atividade_atual : processo.getAtividades())
        {
            if (atividade_atual.getNome().equals(this.atividade.getNome()))
            {
                encontrou = true;
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Esse registro já existe, tente outro!");
                break;
            }
        }

        if (encontrou == false)
        {
            processo.getAtividades().add(atividade);
        }
        atividade = new Atividade();
    }

    public void removerAtividadeDoProcesso(Atividade atividade)
    {
        for (int i = 0; i < processo.getAtividades().size(); i++)
        {
            if (processo.getAtividades().get(i).getNome().equals(atividade.getNome()))
            {
                processo.getAtividades().remove(i);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade removida com Sucesso!");
                break;
            }
        }
    }
//    
//    public void adicionarAtividadeNoProcesso()
//    {
//        processo.getAtividades().add(atividade);
//        atividade = new Atividade();
//    }
//    
//    public void removerAtividadeDoProcesso(Atividade atividade)
//    {
//         processo.getAtividades().remove(atividade);
//    }

    public void editarAtividadeLista(Atividade atividade)
    {
        for (int i = 0; i < processo.getAtividades().size(); i++)
        {
            if (processo.getAtividades().get(i).getNome().equals(atividade.getNome()))
            {
                processo.getAtividades().get(i).setNome(atividade.getNome());
                processo.getAtividades().get(i).setDepartamento(atividade.getDepartamento());

                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade alterada com Sucesso!");
                break;
            }
        }
    }

    public Atividade getAtividade()
    {
        return atividade;
    }

    public void setAtividade(Atividade atividade)
    {
        this.atividade = atividade;
    }

    public void onSelect(SelectEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

}
