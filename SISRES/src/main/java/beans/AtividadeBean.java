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

/**
 * Classe responsável por realizar a comunicação entre o jsf e a camada de
 * serviço das atividades.
 *
 * @author Natália Amâncio
 */
@ManagedBean(name = "atividadeBean")
@ViewScoped
public class AtividadeBean implements Serializable
{

    @EJB
    private AtividadeServico atividadeServico;

    private List<Atividade> atividades = new ArrayList<>();

    private Atividade atividade;

    /**
     * Construtor padrão.
     */
    public AtividadeBean()
    {
        atividade = new Atividade();
    }

    /**
     * Método que realiza a inserção de atividades no banco de dados.
     */
    public void salvar()
    {
        try {
            atividadeServico.salvar(atividade);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade cadastrada com Sucesso!");
        }
        catch (ExcecaoNegocio ex) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        }

        atividade = new Atividade();
        listar();
    }

    /**
     * Lista todas as atividades cadastradas.
     */
    public void listar()
    {
        atividades = atividadeServico.listar();
    }

    /**
     * Realiza a alteração de informações da atividade no banco de dados.
     *
     * @param event Evento vindo da tela de criação/alteração de atividades,
     * especificamente do datatable.
     * @throws ExcecaoNegocio - Exceção lançada por não cumprir as regras de
     * negócio.
     */
    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        atividade = (Atividade) event.getObject();
        listar();

        try {
            atividadeServico.atualizar(atividade);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade alterada com Sucesso!");
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
     * Remove atividade do banco de dados.
     *
     * @param atividade Representa a atividade a ser removida do banco de dados.
     */
    public void remover(Atividade atividade)
    {
        try {
            atividadeServico.remover(atividade);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Atividade removida com Sucesso!");

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
     * Exibe mensagens para o usuário com relação as atividades do processo.
     *
     * @param mensagem Mensagem que será exibida para o usuário
     * @param severity Define o tipo da mensagem.
     */
    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }

    /**
     * Retorna o objeto da AtividadeServico
     *
     * @return AtividadeServico Objeto que permite o acesso aos métodos da
     * classe de AtividadeServico.
     */
    public AtividadeServico getAtividadeServico()
    {
        return atividadeServico;
    }

    /**
     * Seta uma instância para a AtividadeServico
     *
     * @param atividadeServico Objeto que permite o acesso aos métodos da classe
     * de AtividadeServico.
     */
    public void setAtividadeServico(AtividadeServico atividadeServico)
    {
        this.atividadeServico = atividadeServico;
    }

    /**
     * Retorna a lista de atividades
     *
     * @return atividades lista de atividades que está sendo manipulada pelo
     * processo atual.
     */
    public List<Atividade> getAtividades()
    {
        return atividades;
    }

    /**
     * Atribui uma instância para a lista de atividades.
     *
     * @param atividades lista de atividades que está sendo manipulada pelo
     * processo atual.
     */
    public void setAtividades(List<Atividade> atividades)
    {
        this.atividades = atividades;
    }

    /**
     * Retorna objeto de atividade
     *
     * @return atividade
     */
    public Atividade getAtividade()
    {
        return atividade;
    }

    /**
     * Atribui uma instância para a atividade
     *
     * @param atividade
     */
    public void setAtividade(Atividade atividade)
    {
        this.atividade = atividade;
    }

    /**
     * Adiciona as atividades na lista ( em memória ) , para criação ou
     * alteração do fluxo do processo.
     */
    public void adicionarLista()
    {
        atividades.add(atividade);
        atividade = new Atividade();
    }

    /**
     * Remove atividade da lista de atividades, em memória.
     *
     * @param atividade atividade que deve ser removida.
     */
    public void removerLista(Atividade atividade)
    {
        atividades.remove(atividade);
    }
}
