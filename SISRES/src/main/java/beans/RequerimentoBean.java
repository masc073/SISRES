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

    /**
     * Realiza a abertura de um novo requerimento.
     */
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

    /**
     * Lista todos os requerimentos por usuário.
     */
    public void listar()
    {
        Requerimentos = RequerimentoServico.listar(getUsuarioLogado());
        validaPrazos();
    }

    /**
     * Permite atualização das informações do requerimento.
     */
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

    /**
     * Método chamado ao selecionar um requerimento para edição na tela.
     *
     * @param event
     * @exception ExcecaoNegocio
     */
    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        Requerimento = (Requerimento) event.getObject();
        listar();
        editarRequerimento("Requerimento alterado com Sucesso!", Requerimento, true);
        listar();
    }

    /**
     * Cancela requerimento.
     *
     * @param requerimento
     */
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

    /**
     * Exibe mensagens para o usuário em relação ao requerimento.
     *
     * @param mensagem Mensagem que será exibida para o usuário
     * @param severity Define o tipo da mensagem.
     */
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

    /**
     * Realiza a validação do prazo do requerimento, levando em consideração dos
     * feriados cadastrados no SIRES Estes não serão considerados dias úteis.
     */
    public void validaPrazos()
    {
        List<Feriado> feriados;
        Date inicio, hoje;
        boolean feriado = false;
        int dia, qtd_dias_cont = 0;
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        GregorianCalendar cal = new GregorianCalendar();
        String inicio_formatado, hoje_formatado, feriado_formatado;

        feriados = FeriadoServico.listar();
        hoje = new Date();
        cal.setTime(hoje);
        cal.add(Calendar.DATE, 1);

        for (Requerimento requerimento_atual : Requerimentos) {
            inicio = requerimento_atual.getDataDeInicio();

            hoje_formatado = dataFormatada.format(hoje);
            inicio_formatado = dataFormatada.format(inicio);
           
            if (!inicio_formatado.equals(hoje_formatado) || qtd_dias_cont != 0) {

                hoje = cal.getTime();
                hoje_formatado = dataFormatada.format(hoje);
                cal.setTime(inicio);

                while (!inicio_formatado.equals(hoje_formatado)) {
                    for (Feriado feriado_atual : feriados) {
                        
                        feriado_formatado = dataFormatada.format(feriado_atual.getData_do_feriado());
                        if (feriado_formatado.equals(inicio_formatado)) {
                            feriado = true;
                            break;
                        }
                    }

                    dia = cal.get(Calendar.DAY_OF_WEEK);
                    cal.add(Calendar.DATE, 1);
                    inicio_formatado = dataFormatada.format(cal.getTime());

                    if ((dia != 1 && dia != 7) && feriado == false) {
                        ++qtd_dias_cont;
                    }

                    feriado = false;
                }

                if (qtd_dias_cont > requerimento_atual.getProcesso().getDuracaoMaximaEmDias()) {
                    requerimento_atual.setAtrasado(true);
                }
                qtd_dias_cont = 0;
                hoje = new Date();
            }
        }
    }

    /**
     * Redireciona para a tela de listagem de requerimentos.
     *
     * @param mensagem
     * @exception IOException
     */
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

    /**
     * Redirecicona para a tela de exibição do requerimento
     *
     * @param requerimento_exibir Requerimento que deve ser exibido
     */
    public void redireciona_para_exibir_requerimento(Requerimento requerimento_exibir)
    {

        Requerimento = requerimento_exibir;
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("exibirRequerimento.xhtml");

        }
        catch (Exception e) {
        }

    }

    /**
     * Redireciona para a tela de edição das atividades
     *
     */
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

    /**
     * Redireciona para a tela de exibição dos arquivos do requerimento atual
     *
     * @param requerimento_atual
     * @exception ExcecaoNegocio
     */
    public void redireciona_para_exibir_arquivos(Requerimento requerimento_atual) throws ExcecaoNegocio
    {
        try {
            setRequerimento(requerimento_atual);
            FacesContext.getCurrentInstance().getExternalContext().redirect("exibirArquivosRequerimento.xhtml");

        }
        catch (Exception e) {
        }

    }

    /**
     * Vincula o arquivo na atividade atual do requerimento.
     */
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

    /**
     * Realiza a aprovação das atividades do requerimento.
     */
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

    /**
     * Gerencia a aprovação das atividades e a validação dos arquivos.
     */
    public void aprovarAtividade() throws IOException
    {

        if (atividade_atual.getAtividademodelo().isAnexarArquivo() == true) {

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

    /**
     * Realiza a reprovação da atividade do requerimento.
     *
     * @exception IOException
     */
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

    /**
     * Retorna a lista de todos os requerimentows que foram encerrados para
     * aquele usuário.
     *
     */
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

    /**
     * Seta o valor da atividade que foi realizada anteriormente da atual.
     */
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

    /**
     * Seta o valor da atividade que será realizada posteriormente a atual.
     */
    public void setAtividade_proxima()
    {
        int qtd_atividades;

        if (Requerimento.getAtividades() != null) {
            if (!Requerimento.getAtividades().isEmpty()) {

                qtd_atividades = Requerimento.getAtividades().size();

                int posicao = Requerimento.getAtividades().indexOf(atividade_atual);
                if (posicao < qtd_atividades - 1) {
                    this.atividade_proxima = Requerimento.getAtividades().get(posicao + 1);
                }
                else {
                    this.atividade_proxima = atividade_atual;
                }
            }
        }
    }
}
