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

    private List<Requerimento> requerimentos_finalizados = new ArrayList<>();

    private Requerimento Requerimento;
    
    private String mensagem_erro;

    public RequerimentoBean()
    {
        Requerimento = new Requerimento();
        atividade_atual = new Atividade();
    }

    public void salvar()
    {
        try {
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
        Requerimento = new Requerimento();
    }

    public void listar()
    {
        Requerimentos = RequerimentoServico.listar();
    }

    public void editarRequerimento(String mensagem, Requerimento requerimento)
    {
        try {
            RequerimentoServico.atualizar(requerimento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, mensagem);
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
        Requerimento = new Requerimento();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        Requerimento = (Requerimento) event.getObject();
        listar();
        editarRequerimento("Requerimento alterado com Sucesso!", Requerimento);
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
        int qtd_atividades;
        int posicao;

        Requerimento = RequerimentoServico.getRequerimento(Requerimento.getId());

        qtd_atividades = Requerimento.getAtividades().size();
        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
        Requerimento.getAtividades().get(posicao).setSituacao(SituacaoAtividade.Finalizada);
        Requerimento.getAtividades().get(posicao).setDescricao_sucesso(atividade_atual.getDescricao_sucesso());

        if (posicao < qtd_atividades - 1) {
            ++posicao;
            proxima_atividade = Requerimento.getAtividades().get(posicao);
            proxima_atividade.setSituacao(SituacaoAtividade.Andamento);
            Requerimento.setEstadoAtual(proxima_atividade);
            editarRequerimento("Atividade concluída com sucesso!", Requerimento);
        }
        else {
            Requerimento.setFinalizado(true);
            Requerimento.setDataDeFim(new Date());
            editarRequerimento("Processo finalizado com sucesso!", Requerimento);
        }

    }

    public void reprovarAtividade()
    {
        Atividade proxima_atividade;
        int qtd_atividades;
        int posicao;

        Requerimento = RequerimentoServico.getRequerimento(Requerimento.getId());

        qtd_atividades = Requerimento.getAtividades().size();
        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
        Requerimento.getAtividades().get(posicao).setSituacao(SituacaoAtividade.Rejeitada);
        
        if (posicao > 0 ) {
            
            Requerimento.getAtividades().get(posicao).setDescricao_erro(atividade_atual.getDescricao_sucesso());
            --posicao;
            proxima_atividade = Requerimento.getAtividades().get(posicao);
            proxima_atividade.setSituacao(SituacaoAtividade.Andamento);
            Requerimento.setEstadoAtual(proxima_atividade);
            editarRequerimento("Atividade reprovada!", Requerimento);
        }
        else {
           adicionarMensagem(FacesMessage.SEVERITY_INFO, "A primeira atividade não pode ser reprovada! ");
        }
    }

    public List<Requerimento> getRequerimentos_finalizados()
    {
        if(requerimentos_finalizados.isEmpty())
           requerimentos_finalizados = RequerimentoServico.listar_finalizados();
        return requerimentos_finalizados;
    }

    public void setRequerimentos_finalizados(List<Requerimento> requerimentos_finalizados)
    {
        this.requerimentos_finalizados = requerimentos_finalizados;
    }
    
    public String getMensagem_erro()
    {
//        int posicao;
//        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
//        ++posicao;
//        mensagem_erro = Requerimento.getAtividades().get(posicao).getDescricao();
        
        return mensagem_erro;
    }

    public void setMensagem_erro(String mensagem_erro)
    {
        this.mensagem_erro = mensagem_erro;
    }

}
