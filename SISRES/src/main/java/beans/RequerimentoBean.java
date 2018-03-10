package beans;

import dominio.Arquivo;
import dominio.Atividade;
import dominio.Feriado;
import dominio.Processo;
import dominio.Requerimento;
import dominio.Responsavel;
import dominio.SituacaoAtividade;
import excecao.ExcecaoNegocio;
import excecao.MensagemExcecao;
import gherkin.lexer.Ca;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import servico.FeriadoServico;
import servico.RequerimentoServico;

/**
 * Classe responsável por realizar a comunicação do jsf do a camada de serviço
 * do requerimento.
 *
 * @author Natália Amâncio
 */
@ManagedBean(name = "requerimentoBean")
@SessionScoped
public class RequerimentoBean implements Serializable
{

    @EJB
    private RequerimentoServico RequerimentoServico;

    @EJB
    private FeriadoServico FeriadoServico;

    private Atividade atividade_atual;

    private Atividade atividade_anterior;

    private Atividade atividade_proxima;

    private List<Requerimento> Requerimentos = new ArrayList<>();

    private List<Requerimento> requerimentos_finalizados = new ArrayList<>();

    private List<Arquivo> arquivos = new ArrayList<>();

    private Requerimento Requerimento;

    private UploadedFile arquivo;

    private Arquivo arquivo_digital;

    private StreamedContent conteudo_arquivo;

    public RequerimentoBean()
    {
        Requerimento = new Requerimento();
        atividade_atual = new Atividade();
        atividade_anterior = new Atividade();
        arquivos = new ArrayList<>();

    }

    public void salvar()
    {
        Responsavel solicitante;
        solicitante = getUsuarioLogado();
        try {
            Requerimento.criarAtividades(Requerimento.getProcesso().getAtividades());
            Requerimento.setSolicitante(solicitante);
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
        Requerimentos = RequerimentoServico.listar(getUsuarioLogado());
        validaPrazos();
    }

    public void editarRequerimento(String mensagem, Requerimento requerimento, boolean com_messagem)
    {
        try {
            RequerimentoServico.atualizar(requerimento);

            if (com_messagem) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, mensagem);
            }

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
        editarRequerimento("Requerimento alterado com Sucesso!", Requerimento, true);
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

    public Responsavel getUsuarioLogado()
    {
        return (Responsavel) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
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
            Requerimentos = RequerimentoServico.listar(getUsuarioLogado());
            validaPrazos();
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
        preenche_lista_arquivos();
    }

    public void validaPrazos()
    {

        String outro_dia, dia_hoje;
        Date inicio, hoje, dia_atual;
        int qtd_de_dias, dia, contador_dias_uteis = 0;
        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal_auxiliar = new GregorianCalendar();
        List<Feriado> feriados;
        hoje = new Date();
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        feriados = FeriadoServico.listar();

        for (Requerimento requerimento_atual : Requerimentos) {

            inicio = requerimento_atual.getDataDeInicio();
            qtd_de_dias = requerimento_atual.getProcesso().getDuracaoMaximaEmDias();
            contador_dias_uteis = qtd_de_dias;
            cal.setTime(inicio);
            dia_atual = inicio;
            cal_auxiliar.setTime(hoje);

            dia_hoje = dataFormatada.format(hoje);
            outro_dia = dataFormatada.format(cal.getTime());
            
            while (!dia_hoje.equals(outro_dia)) {

                dia = cal.get(Calendar.DAY_OF_WEEK);
                cal.add(Calendar.DATE, 1);
                outro_dia = dataFormatada.format(cal.getTime());

                if (dia != 1 && dia != 7) {
                    --contador_dias_uteis;
                }
            }

            if (contador_dias_uteis == qtd_de_dias || contador_dias_uteis < 0) {
                System.err.println("Ultrapassou data!!!");
                requerimento_atual.setAtrasado(true);
            }
            contador_dias_uteis = 0;
        }

    }

    public void redireciona_para_lista_requerimento(String mensagem) throws IOException
    {

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(mensagem));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        try {

            FacesContext.getCurrentInstance().getExternalContext().redirect("atividade.xhtml?faces-redirect=true");

        }
        catch (Exception e) {
        }

    }

    public void redireciona_para_exibir_requerimento(Requerimento requerimento_exibir)
    {

        Requerimento = requerimento_exibir;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("exibirRequerimento.xhtml");

        }
        catch (Exception e) {
        }

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

    public void redireciona_para_exibir_arquivos(Requerimento requerimento_atual) throws ExcecaoNegocio
    {
        try {
            setRequerimento(requerimento_atual);
            FacesContext.getCurrentInstance().getExternalContext().redirect("exibirArquivosRequerimento.xhtml");

        }
        catch (Exception e) {
        }

    }

    private void adicionar_arquivo()
    {
        Arquivo arquivoDigital = atividade_atual.criarArquivo();
        arquivoDigital.setArquivo(arquivo.getContents());
        arquivoDigital.setExtensao(arquivo.getContentType());
        arquivoDigital.setNome(arquivo.getFileName());
        atividade_atual.setArquivo(arquivoDigital);
        this.arquivo_digital = arquivoDigital;
    }

    private void preenche_lista_arquivos()
    {
        arquivos.clear();
        for (Atividade atividade_atual : Requerimento.getAtividades()) {

            if (atividade_atual.getArquivo() != null) {
                arquivos.add(atividade_atual.getArquivo());

            }
        }
    }

    public void aprovando_atividade()
    {

        Atividade proxima_atividade;
        int qtd_atividades;
        int posicao;

        Requerimento = RequerimentoServico.getRequerimento(Requerimento.getId());

        qtd_atividades = Requerimento.getAtividades().size();
        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
        Requerimento.getAtividades().get(posicao).setSituacao(SituacaoAtividade.Finalizada);
        Requerimento.getAtividades().get(posicao).setDescricao_sucesso(atividade_atual.getDescricao_sucesso());
        Requerimento.getAtividades().get(posicao).setArquivo(atividade_atual.getArquivo());

        if (posicao < qtd_atividades - 1) {
            ++posicao;
            proxima_atividade = Requerimento.getAtividades().get(posicao);
            Requerimento.getAtividades().get(posicao).setDescricao_erro("");
            Requerimento.getAtividades().get(posicao).setDescricao_sucesso("");
            proxima_atividade.setSituacao(SituacaoAtividade.Andamento);
            Requerimento.setEstadoAtual(proxima_atividade);
            editarRequerimento("Atividade concluída com sucesso!", Requerimento, false);
        }
        else {
            Requerimento.setFinalizado(true);
            Requerimento.setDataDeFim(new Date());
            editarRequerimento("Processo finalizado com sucesso!", Requerimento, false);
        }

    }

    public void aprovarAtividade() throws IOException
    {

        if (atividade_atual.getAtividadeModelo().isAnexarArquivo() == true) {

            if (!arquivo.getContentType().equals("application/pdf")) {
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Deve ser anexado um arquivo PDF! ");
            }
            else {

                adicionar_arquivo();
                aprovando_atividade();
                redireciona_para_lista_requerimento("Atividade concluída com sucesso!");
            }
        }
        else {
            aprovando_atividade();
            redireciona_para_lista_requerimento("Atividade concluída com sucesso!");
        }
    }

    public void reprovarAtividade() throws IOException
    {
        Atividade proxima_atividade;
        int qtd_atividades;
        int posicao;

        Requerimento = RequerimentoServico.getRequerimento(Requerimento.getId());

        qtd_atividades = Requerimento.getAtividades().size();
        posicao = Requerimento.getAtividades().indexOf(atividade_atual);
        Requerimento.getAtividades().get(posicao).setSituacao(SituacaoAtividade.Rejeitada);

        if (posicao > 0) {

            Requerimento.getAtividades().get(posicao).setDescricao_erro(atividade_atual.getDescricao_sucesso());
            --posicao;
            Requerimento.getAtividades().get(posicao).setDescricao_erro("");
            Requerimento.getAtividades().get(posicao).setDescricao_sucesso("");
            Requerimento.getAtividades().get(posicao).setArquivo(null);
            proxima_atividade = Requerimento.getAtividades().get(posicao);
            proxima_atividade.setSituacao(SituacaoAtividade.Andamento);
            Requerimento.setEstadoAtual(proxima_atividade);
            editarRequerimento("Atividade reprovada!", Requerimento, false);
            redireciona_para_lista_requerimento("Atividade reprovada!");
        }
        else {
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "A primeira atividade não pode ser reprovada! ");
        }
    }

    public List<Requerimento> getRequerimentos_finalizados()
    {
        requerimentos_finalizados = RequerimentoServico.listar_finalizados(getUsuarioLogado());

        return requerimentos_finalizados;
    }

    public void setRequerimentos_finalizados(List<Requerimento> requerimentos_finalizados)
    {
        this.requerimentos_finalizados = requerimentos_finalizados;
    }

    public UploadedFile getArquivo()
    {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo)
    {
        this.arquivo = arquivo;
    }

    public Arquivo getArquivo_digital()
    {
        return arquivo_digital;
    }

    public void setArquivo_digital(Arquivo arquivo_digital)
    {
        this.arquivo_digital = arquivo_digital;
    }

    public StreamedContent getConteudo_arquivo()
    {
        return conteudo_arquivo;
    }

    public Atividade getAtividade_anterior()
    {
        setAtividade_anterior();
        return atividade_anterior;
    }

    public void setAtividade_anterior()
    {
        if (Requerimento.getAtividades() != null) {
            if (!Requerimento.getAtividades().isEmpty()) {
                Requerimento = RequerimentoServico.getRequerimento(Requerimento.getId());
                int posicao = Requerimento.getAtividades().indexOf(atividade_atual);
                if (posicao > 0) {
                    this.atividade_anterior = Requerimento.getAtividades().get(posicao - 1);
                }
                else {
                    this.atividade_anterior = atividade_atual;
                }
            }
        }

    }

    public Atividade getAtividade_proxima()
    {
        setAtividade_proxima();
        return atividade_proxima;
    }

    public List<Arquivo> getArquivos()
    {
        if (arquivos.isEmpty()) {
            preenche_lista_arquivos();
        }

        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos)
    {
        this.arquivos = arquivos;
    }

    public void setAtividade_proxima()
    {
        int qtd_atividades;

        if (Requerimento.getAtividades() != null) {
            if (!Requerimento.getAtividades().isEmpty()) {

                qtd_atividades = Requerimento.getAtividades().size();

                int posicao = Requerimento.getAtividades().indexOf(atividade_atual);
                if (posicao < qtd_atividades - 1) {
                    this.atividade_proxima = Requerimento.getAtividades().get(posicao + 1);
                    System.out.println("Posição da próxima: " + posicao + 1);
                }
                else {
                    this.atividade_proxima = atividade_atual;
                    System.out.println("Posição da próxima: " + posicao);
                }
            }
        }
    }
}
