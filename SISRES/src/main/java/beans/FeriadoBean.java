package beans;

import dominio.Feriado;
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
import servico.FeriadoServico;

/**
 * Classe responsável por realizar a comunicação entre o jsf e a camada de
 * serviço com relação ao feriado.
 *
 * @author Natália Amâncio
 */
@ManagedBean(name = "feriadoBean")
@ViewScoped
public class FeriadoBean implements Serializable
{

    @EJB
    private FeriadoServico feriadoServico;

    /**
     * Lista dos feriados cadastrados no banco.
     */
    protected List<Feriado> feriados = new ArrayList<>();

    /**
     * Feriado que está sendo manipulado no momento.
     */
    protected Feriado feriado;

    /**
     * Construtor padrão.
     */
    public FeriadoBean()
    {
        feriado = new Feriado();
    }

    /**
     * Salva feriado no banco de dados.
     */
    public void salvar()
    {
        try {
            feriadoServico.salvar(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Feriado cadastrado com Sucesso!");
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

        feriado = new Feriado();
        listar();
    }

    /**
     * Edita as informações do feriado e atualiza no banco de dados.
     *
     * @param event Evento vindo do datatable com o objeto feriado para ser
     * atualizado.
     * @throws ExcecaoNegocio - Exceção lançada por não cumprir as regras de
     * negócio.
     */
    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        feriado = (Feriado) event.getObject();
        listar();

        try {
            feriadoServico.atualizar(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Feriado alterado com Sucesso!");
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
        listar();
    }

    /**
     * Remover feriado
     *
     * @param feriado feriado a ser removido.
     */
    public void remover(Feriado feriado)
    {
        try {
            feriadoServico.remover(feriado);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Feriado removido com Sucesso!");

        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
    }

    /**
     * Lista todos os feriados cadastrados no banco de dados.
     */
    public void listar()
    {
        feriados = feriadoServico.listar();
    }

    /**
     * Retorna objeto FeriadoServico
     *
     * @return feriadoServico - Objeto que permite acessar todos os métodos da
     * classe FeriadoServico.
     */
    public FeriadoServico getFeriadoServico()
    {
        return feriadoServico;
    }

    /**
     * Seta instância de feriadoServido para objeto da classe.
     *
     * @param feriadoServico
     */
    public void setFeriadoServico(FeriadoServico feriadoServico)
    {
        this.feriadoServico = feriadoServico;
    }

    /**
     * Retorna lista de feriados
     *
     * @return feriados
     */
    public List<Feriado> getFeriados()
    {
        if (feriados.isEmpty()) {
            feriados = feriadoServico.listar();
        }

        return feriados;
    }

    /**
     * Atribui uma lista de feriados.
     *
     * @param feriados lista de feriados
     */
    public void setFeriados(List<Feriado> feriados)
    {
        this.feriados = feriados;
    }

    /**
     * Retorna objeto Feriado
     *
     * @return Feriado
     */
    public Feriado getFeriado()
    {
        return feriado;
    }

    /**
     * Seta instância de feriado
     *
     * @param feriado
     */
    public void setFeriado(Feriado feriado)
    {
        this.feriado = feriado;
    }

    /**
     * Exibe mensagens para o usuário com relação ao feriado
     *
     * @param mensagem Mensagem que será exibida para o usuário
     * @param severity Define o tipo da mensagem.
     */
    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }
}
