package beans;

import dominio.UnidadeOrganizacional;
import dominio.Responsavel;
import dominio.TiposUnidadesOrganizacionais;
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

    private TiposUnidadesOrganizacionais[] tipos; 
    
    /** Construtor padrão.
     */
    public UnidadeOrganizacionalBean()
    {
        unidadeOrganizacional = new UnidadeOrganizacional();
    }

    /** Realiza a inserção da Unidade Organizacional no banco de dados.
     */
    public void salvar()
    {
        try
        {
            unidadeOrganizacionalServico.salvar(unidadeOrganizacional);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Unidade Organizacional cadastrada com Sucesso!");
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

    /** Lista todas as Unidades Organizacionais que estão cadastradas no banco de dados.
     */
    public void listar()
    {
        unidadesOrganizacionais = unidadeOrganizacionalServico.listar();
    }

    /** Edita as informações referentes a Unidade Orgenizacional.
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
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Unidade Organizacional alterada com Sucesso!");
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

    /** Remove Unidade Organizacional do banco de dados.
     * @param unidade_organizacional UnidadeOrganizacional a ser removido.
     */
    public void remover(UnidadeOrganizacional unidade_organizacional)
    {
        try
        {
            unidadeOrganizacionalServico.remover(unidade_organizacional);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Unidade Organizacional removida com Sucesso!");

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
    
   /** Exibe mensagens para o usuário com relação a Unidade Organizacional.
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
    
    public TiposUnidadesOrganizacionais[] getTipos()
    {
        tipos = TiposUnidadesOrganizacionais.values();
        return tipos;
    }
}