package beans;

import dominio.UnidadeOrganizacional;
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
import servico.UnidadeOrganizacionalServico;

/** Classe responsável pela comunicação do jsf com a camada de serviço com relação a Unidade Organizacional.
 * @author Natália Amâncio
 */
@ManagedBean(name = "unidadeOrganizacionalBean")
@ViewScoped
public class UnidadeOrganizacionalBean implements Serializable
{

    @EJB
    private UnidadeOrganizacionalServico unidadeOrganizacionalServico;
    
    private List<Responsavel> participantes;

    private List<UnidadeOrganizacional> unidadesOrganizacionais = new ArrayList<>();

    private UnidadeOrganizacional unidadeOrganizacional;

    /** Construtor padrão.
     */
    public UnidadeOrganizacionalBean()
    {
        unidadeOrganizacional = new UnidadeOrganizacional();
    }

    /** Realiza a inserção do unidadeOrganizacional no banco de dados.
     */
    public void salvar()
    {
        try
        {
            unidadeOrganizacionalServico.salvar(unidadeOrganizacional);
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

        unidadeOrganizacional = new UnidadeOrganizacional();
        listar();
    }

    /** Lista todos os unidadesOrganizacionais que estão cadastrados no banco de dados.
     */
    public void listar()
    {
        unidadesOrganizacionais = unidadeOrganizacionalServico.listar();
    }

    /** Edita as informações referentes ao depatamento.
     * @param event Evento vindo do datatable que contém o objeto de unidadeOrganizacional que será alterado.
     * @exception ExcecaoNegocio - Exceção lançada por não cumprir as regras de negócio.
     */
    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        unidadeOrganizacional = (UnidadeOrganizacional) event.getObject();
        listar();

        try
        {
            unidadeOrganizacionalServico.atualizar(unidadeOrganizacional);
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

    /** Remove unidadeOrganizacional do banco de dados.
     * @param departamento UnidadeOrganizacional a ser removido.
     */
    public void remover(UnidadeOrganizacional departamento)
    {
        try
        {
            unidadeOrganizacionalServico.remover(departamento);
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
    
   /** Exibe mensagens para o usuário com relação ao unidadeOrganizacional.
     * @param mensagem  Mensagem que será exibida para o usuário
     * @param severity  Define o tipo da mensagem.
      */
    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }

    public UnidadeOrganizacionalServico getUnidadeOrganizacionalServico()
    {
        return unidadeOrganizacionalServico;
    }

    public void setUnidadeOrganizacionalServico(UnidadeOrganizacionalServico unidadeOrganizacionalServico)
    {
        this.unidadeOrganizacionalServico = unidadeOrganizacionalServico;
    }

    public List<UnidadeOrganizacional> getUnidadesOrganizacionais()
    {
        if (unidadesOrganizacionais.isEmpty())
        {
            unidadesOrganizacionais = unidadeOrganizacionalServico.listar();
        }
        return unidadesOrganizacionais;
    }

    public void setUnidadesOrganizacionais(List<UnidadeOrganizacional> unidadesOrganizacionais)
    {
        this.unidadesOrganizacionais = unidadesOrganizacionais;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional()
    {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional)
    {
        this.unidadeOrganizacional = unidadeOrganizacional;
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
