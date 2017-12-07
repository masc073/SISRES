package beans;

import dominio.Atividade;
import dominio.SituacaoAtividade;
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
import servico.AtividadeServico;

@ManagedBean(name = "atividadeBean")
@ViewScoped
public class AtividadeBean implements Serializable
{

    @EJB
    private AtividadeServico atividadeServico;

    private List<Atividade> atividades = new ArrayList<>();

    private Atividade atividade;

    public AtividadeBean()
    {
        System.out.println("CONSTRUTOR DE ATIVIDADES");
        atividade = new Atividade();
    }

    public void salvar()
    {
        try
        {
            atividadeServico.salvar(atividade);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade cadastrada com Sucesso!");
        } catch (ExcecaoNegocio ex)
        {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        }

        atividade = new Atividade();
        listar();
    }

    public void listar()
    {
        atividades = atividadeServico.listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        atividade = (Atividade) event.getObject();
        listar();

        try
        {
            atividadeServico.atualizar(atividade);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade alterada com Sucesso!");
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

    public void remover(Atividade atividade)
    {
        try
        {
            atividadeServico.remover(atividade);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade removida com Sucesso!");

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

    public AtividadeServico getAtividadeServico()
    {
        return atividadeServico;
    }

    public void setAtividadeServico(AtividadeServico atividadeServico)
    {
        this.atividadeServico = atividadeServico;
    }

    public List<Atividade> getAtividades()
    {
//        if (atividades.isEmpty())
//        {
//            atividades = atividadeServico.listar();
//        }
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades)
    {
        System.out.println("Estou no set de atividades");
        this.atividades = atividades;
    }

    public Atividade getAtividade()
    {
        return atividade;
    }

    public void setAtividade(Atividade atividade)
    {
        this.atividade = atividade;
    }
    
//    public SituacaoAtividade[] getSituacaoAtividade() {
//        return SituacaoAtividade.values();
//    }
    
    public void adicionarLista()
    {
        System.out.println("Lista de atividades : " + atividades.size());
        atividades.add(atividade);
        atividade = new Atividade();
    }
    
    public void removerLista(Atividade atividade)
    {
         atividades.remove(atividade);
    }
    
    public void editarAtividadeLista(Atividade atividade)
    {
        for (Atividade atividade_atual : atividades)
        {
            if(atividade_atual.equals(atividade))
            {
              atividade_atual.setDepartamento(atividade.getDepartamento());
              atividade_atual.setNome(atividade.getNome());
            }
        }
    }
}
