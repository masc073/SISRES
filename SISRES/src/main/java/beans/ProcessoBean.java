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
//@SessionScoped
@ViewScoped
public class ProcessoBean implements Serializable
{

    @EJB
    private ProcessoServico ProcessoServico;

    protected List<Processo> processos = new ArrayList<>();

    protected Processo processo;

    protected Atividade atividade;

    private List<Atividade> atividades = new ArrayList<>();

    public ProcessoBean()
    {
        atividade = new Atividade();
        processo = new Processo();
    }

    public void salvar()
    {
        if (!atividades.isEmpty())
        {
            try
            {
                processo.setAtividades(atividades);
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
        }
        else 
        {
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Devem ser adicionadas atividades ao processo!");
        }
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

        for (Atividade atividade_atual : this.atividades)
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
            this.atividades.add(atividade);
        }
        atividade = new Atividade();
    }

    public void removerAtividadeDoProcesso(Atividade atividade)
    {
        for (int i = 0; i < this.atividades.size(); i++)
        {
            if (this.atividades.get(i).getNome().equals(atividade.getNome()))
            {
                this.atividades.remove(i);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade removida com Sucesso!");
                break;
            }
        }
    }

    public void editarAtividadeLista(Atividade atividade)
    {
        for (int i = 0; i < this.atividades.size(); i++)
        {
            if (this.atividades.get(i).getNome().equals(atividade.getNome()))
            {
                this.atividades.get(i).setNome(atividade.getNome());
                this.atividades.get(i).setDepartamento(atividade.getDepartamento());

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

    public List<Atividade> getAtividades()
    {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades)
    {
        this.atividades = atividades;
    }

    public void redireciona_para_editar_atividades(Processo processo_atualizar) throws ExcecaoNegocio
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().clear();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("processo_atualizar", processo_atualizar);
            FacesContext.getCurrentInstance().getExternalContext().redirect("editarprocesso.xhtml");

        } catch (Exception e)
        {
        }

    }
}
