package beans;

import dominio.AtividadeModelo;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import servico.ProcessoServico;

/** Classe responsável por realizar a comunicação do jsf do a camada de serviço do processo.
 * @author Natália Amâncio
 */
@ManagedBean(name = "processoBean")
@ViewScoped
public class ProcessoBean implements Serializable
{

    @EJB
    private ProcessoServico ProcessoServico;

    protected List<Processo> processos = new ArrayList<>();

    protected Processo processo;

    protected AtividadeModelo atividade;
    
    protected String anexarArquivo;

    private List<AtividadeModelo> atividades = new ArrayList<>();

    /** Construtor padrão.
     */
    public ProcessoBean()
    {
        atividade = new AtividadeModelo();
        processo = new Processo();
    }

    /** Seta as atividades do processo e salva no banco de dados.
     */
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

    /**Edita as informações do processo e atualiza no banco de dados.
     * @param event - Evento vindo do datatable
     * @throws ExcecaoNegocio - Exceção lançada por não cumprir as regras de negócio.
     */
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

    /** Remove processo do banco de dados
     * @param processo - Processo a ser removido do banco de dados.
     */
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

    /** Lista todos os processos.
     */
    public void listar()
    {
        processos = ProcessoServico.listar();
    }

    /** Retorna o objeto ProcessoServico
     * @return ProcessoServico - Permite o acesso aos métodos do ProcessoServico.
     */
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

        for (AtividadeModelo atividade_atual : this.atividades)
        {
            if (atividade_atual.getNome().equals(this.atividade.getNome()))
            {
                encontrou = true;
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Esse registro já existe, tente outro!");
                break;
            }
        }

        atividade.setAnexarArquivo(return_anexarAquivo());
        
        if (encontrou == false)
        {
            this.atividades.add(atividade);
        }
        atividade = new AtividadeModelo();
    }

    public void removerAtividadeDoProcesso(AtividadeModelo atividade)
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

    public void editarAtividadeLista(AtividadeModelo atividade)
    {
        
        atividade.setAnexarArquivo(return_anexarAquivo());
        
        for (int i = 0; i < this.atividades.size(); i++)
        {
            if (this.atividades.get(i).getNome().equals(atividade.getNome()))
            {
                this.atividades.get(i).setNome(atividade.getNome());
                this.atividades.get(i).setDepartamento(atividade.getDepartamento());
                this.atividades.get(i).setAnexarArquivo(atividade.isAnexarArquivo());

                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade alterada com Sucesso!");
                break;
            }
        }
    }
    
    public AtividadeModelo getAtividade()
    {
        return atividade;
    }

    public void setAtividade(AtividadeModelo atividade)
    {
        this.atividade = atividade;
    }

    public List<AtividadeModelo> getAtividades()
    {
        return atividades;
    }

    public void setAtividades(List<AtividadeModelo> atividades)
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
    
    public String getAnexarArquivo()
    {
        return anexarArquivo;
    }

    public void setAnexarArquivo(String anexarArquivo)
    {
        this.anexarArquivo = anexarArquivo;
    }
    
    public boolean return_anexarAquivo()
    {
        if (anexarArquivo.equals("sim")) 
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    public String visualizar_anexarAquivo(AtividadeModelo atividade_atual)
    {
        if (atividade_atual.isAnexarArquivo()) 
        {
            return "Sim";
        }
        else 
        {
            return "Não";
        }
    }
}
