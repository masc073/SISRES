package beans;

import dominio.Departamento;
import dominio.Responsavel;
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
import servico.DepartamentoServico;

/** Classe responsável pela comunicação do jsf com a camada de serviço com relação ao departamento.
 * @author Natália Amâncio
 */
@ManagedBean(name = "departamentoBean")
@ViewScoped
public class DepartamentoBean implements Serializable
{

    @EJB
    private DepartamentoServico departamentoServico;
    
    private List<Responsavel> participantes;

    private List<Departamento> departamentos = new ArrayList<>();

    private Departamento departamento;

    /** Construtor padrão.
     */
    public DepartamentoBean()
    {
        departamento = new Departamento();
    }

    /** Realiza a inserção do departamento no banco de dados.
     */
    public void salvar()
    {
        try
        {
            departamentoServico.salvar(departamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Departamento cadastrado com Sucesso!");
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

        departamento = new Departamento();
        listar();
    }

    /** Lista todos os departamentos que estão cadastrados no banco de dados.
     */
    public void listar()
    {
        departamentos = departamentoServico.listar();
    }

    /** Edita as informações referentes ao depatamento.
     * @param event Evento vindo do datatable que contém o objeto de departamento que será alterado.
     * @exception ExcecaoNegocio - Exceção lançada por não cumprir as regras de negócio.
     */
    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        departamento = (Departamento) event.getObject();
        listar();

        try
        {
            departamentoServico.atualizar(departamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Departamento alterado com Sucesso!");
            listar();
        } 
        catch (ExcecaoNegocio ex)
        {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } 
        catch (EJBException ex)
        {
            if (ex.getCause() instanceof ConstraintViolationException)
            {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
    }

    /** Remove departamento do banco de dados.
     * @param departamento Departamento a ser removido.
     */
    public void remover(Departamento departamento)
    {
        try
        {
            departamentoServico.remover(departamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Departamento removido com Sucesso!");

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
    
   /** Exibe mensagens para o usuário com relação ao departamento.
     * @param mensagem  Mensagem que será exibida para o usuário
     * @param severity  Define o tipo da mensagem.
      */
    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }

    public DepartamentoServico getDepartamentoServico()
    {
        return departamentoServico;
    }

    public void setDepartamentoServico(DepartamentoServico departamentoServico)
    {
        this.departamentoServico = departamentoServico;
    }

    public List<Departamento> getDepartamentos()
    {
        if (departamentos.isEmpty())
        {
            departamentos = departamentoServico.listar();
        }
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos)
    {
        this.departamentos = departamentos;
    }

    public Departamento getDepartamento()
    {
        return departamento;
    }

    public void setDepartamento(Departamento departamento)
    {
        this.departamento = departamento;
    }
    
    public List<Responsavel> getParticipantes()
    {
        return participantes;
    }

    public void setParticipantes(List<Responsavel> participantes)
    {
        this.participantes = participantes;
    }

//    public Responsavel getResponsavel_lider()
//    {
//        return responsavel_lider;
//    }
//
//    public void setResponsavel_lider(Responsavel responsavel_lider)
//    {
//        this.responsavel_lider = responsavel_lider;
//    }
}
