package beans;

import dominio.Arquivo;
import dominio.Atividade;
import dominio.Processo;
import dominio.Requerimento;
import dominio.SituacaoAtividade;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import servico.RequerimentoServico;

@ManagedBean(name = "requerimentoBean")
@SessionScoped
public class RequerimentoBean implements Serializable
{

    @EJB
    private RequerimentoServico RequerimentoServico;

    private Atividade atividade_atual;
    
    private List<Requerimento> Requerimentos = new ArrayList<>();

    private Requerimento Requerimento;

    public RequerimentoBean()
    {
        Requerimento = new Requerimento();
        atividade_atual = new Atividade();
    }

    public void salvar()
    {
        try 
        {
            Requerimento.criarAtividades(Requerimento.getProcesso().getAtividades());
            atividade_atual = Requerimento.getAtividades().get(0);
            Requerimento.setDataDeInicio(new Date());
            RequerimentoServico.salvar(Requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Requerimento aberto com Sucesso!");
        }
        catch (ExcecaoNegocio ex) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }

        listar();
    }

    public void listar()
    {
        Requerimentos = RequerimentoServico.listar();
    }

    public void editarRequerimento(Requerimento requerimento)
    {
        try 
        {
            RequerimentoServico.atualizar(requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Requerimento alterado com Sucesso!");
            listar();
        }
        catch (ExcecaoNegocio ex) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }

    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        Requerimento = (Requerimento) event.getObject();
        listar();
        editarRequerimento(Requerimento);
        listar();
    }

    public void remover(Requerimento requerimento)
    {
        try {
            RequerimentoServico.remover(requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Requerimento encerrado com Sucesso!");

        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
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
        if (Requerimentos.isEmpty()) {
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

    public Atividade getAtividade_atual()
    {
        return atividade_atual;
    }

    public void setAtividade_atual(Atividade atividade_atual)
    {
        this.atividade_atual = atividade_atual;
    }

    public void setRequerimento(Requerimento Requerimento)
    {
        setAtividade_atual(Requerimento.getEstadoAtual());
        this.Requerimento = Requerimento;
    }

    public void redireciona_para_exibir_requerimento(Requerimento requerimento_exibir) throws ExcecaoNegocio
    {

        Requerimento = requerimento_exibir;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("exibirRequerimento.xhtml");

        }
        catch (Exception e) {
        }

    }

    public float geraPercentual()
    {

        int posicao = Requerimento.getProcesso().getAtividades().indexOf(Requerimento.getEstadoAtual());
        int totalDeAtividades = Requerimento.getProcesso().getAtividades().size();

        float percentual = (posicao * 100) / totalDeAtividades;

        return percentual;
    }

    public void redireciona_para_editar_atividades(Requerimento requerimento_atualizar) throws ExcecaoNegocio
    {
       
        setRequerimento(requerimento_atualizar);
        setAtividade_atual(requerimento_atualizar.getEstadoAtual());

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("editaratividade.xhtml");

        }
        catch (Exception e) {

        }
    }

    public void upload(FileUploadEvent event)
    {
        setFile(event.getFile());
    }

    private void setFile(UploadedFile file)
    {
        Arquivo arquivoDigital = atividade_atual.criarArquivo();
        arquivoDigital.setArquivo(file.getContents());
        arquivoDigital.setExtensao(file.getContentType());
        arquivoDigital.setNome(file.getFileName());
        atividade_atual.setArquivo(arquivoDigital);
    }

    public void aprovarAtividade()
    {
        Atividade proxima_atividade;
        int posicao;
        
        Requerimento = RequerimentoServico.getRequerimento(Requerimento.getId());
        
        
        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
        Requerimento.getAtividades().get(posicao).setSituacao(SituacaoAtividade.Finalizada);
        Requerimento.getAtividades().get(posicao).setDescricao(atividade_atual.getDescricao());
        ++posicao;
        
        proxima_atividade = Requerimento.getAtividades().get(posicao);
        proxima_atividade.setSituacao(SituacaoAtividade.Andamento);
        Requerimento.setEstadoAtual(proxima_atividade);
        
        editarRequerimento(Requerimento);
    }
    
     public void reprovarAtividade()
    {
        Atividade proxima_atividade;
        int posicao;
        
        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
        atividade_atual.setSituacao(SituacaoAtividade.Rejeitada);
        --posicao;
        
        proxima_atividade = Requerimento.getAtividades().get(posicao);
        proxima_atividade.setSituacao(SituacaoAtividade.Andamento);
        Requerimento.setEstadoAtual(proxima_atividade);
        editarRequerimento(Requerimento);
    }

}
