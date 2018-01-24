package beans;

import criptografia.Encripta;
import dominio.PerfilGoogle;
import dominio.Responsavel;
import dominio.Titulos;
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
import servico.PerfilGoogleServico;
import servico.ResponsavelServico;

/** Classe responsável por realizar a comunicação do jsf do a camada de serviço do responsável.
 * @author Natália Amâncio
 */
@ManagedBean(name = "responsavelBean")
@ViewScoped
public class ResponsavelBean implements Serializable
{

    @EJB
    private ResponsavelServico responsavelServico;
    
    @EJB
    private PerfilGoogleServico googleServico;
    
    private PerfilGoogle perfilGoogle;

    private List<Responsavel> responsaveis = new ArrayList<>();

    private List<Responsavel> lideres_nao_aprovados = new ArrayList<>();
    
    private List<Responsavel> participantes_do_deparamento = new ArrayList<>();
    
    private Titulos[] titulos;

    private Responsavel responsavel;

    String senhaConfirmacao;

    Encripta encripta;

    public ResponsavelBean()
    {
        responsavel = new Responsavel();
        encripta = new Encripta();
        perfilGoogle = new PerfilGoogle();
    }

    public Responsavel getUsuarioLogado()
    {
      return (Responsavel) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");      
    }
    
    public void salvar()
    {
        perfilGoogle.setUsuario(responsavel);
        googleServico.persistePerfilGoogle(perfilGoogle);

            try {
                responsavelServico.salvar(responsavel);
                adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável cadastrado com Sucesso!");
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
//        }
//        else {
//            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Senhas diferentes!");
//        }

        responsavel = new Responsavel();
        listar();
    }

    public void listar()
    {
        responsaveis = responsavelServico.listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        responsavel = (Responsavel) event.getObject();
        listar();

        try {
            responsavelServico.atualizar(responsavel);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável alterado com Sucesso!");
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

    public void remover(Responsavel responsavel)
    {
        try {
            responsavelServico.remover(responsavel);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável removido com Sucesso!");

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

    public ResponsavelServico getResponsavelServico()
    {
        return responsavelServico;
    }

    public void setResponsavelServico(ResponsavelServico responsavelServico)
    {
        this.responsavelServico = responsavelServico;
    }

    public List<Responsavel> getResponsaveis()
    {
        if (responsaveis.isEmpty()) {
            responsaveis = responsavelServico.listar();
        }
        return responsaveis;
    }

    public void setResponsaveis(List<Responsavel> responsaveis)
    {
        this.responsaveis = responsaveis;
    }

    public Responsavel getResponsavel()
    {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel)
    {
        this.responsavel = responsavel;
    }

    public String getSenhaConfirmacao()
    {
        return senhaConfirmacao;
    }

    public void setSenhaConfirmacao(String senhaConfirmacao)
    {
        this.senhaConfirmacao = senhaConfirmacao;
    }

    public List<Responsavel> getLideres_nao_aprovados()
    {
        lideres_nao_aprovados = responsavelServico.listar_nao_aprovados_lider();
        return lideres_nao_aprovados;
    }

    public void setLideres_nao_aprovados(List<Responsavel> responsaveis_nao_aprovados)
    {
        this.lideres_nao_aprovados = responsaveis_nao_aprovados;
    }

    public void Aprovar(Responsavel responsavel_atual)
    {
        responsavel_atual.setAprovado(true);
        try {
            responsavelServico.atualizar(responsavel_atual);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Responsável aprovado com Sucesso!");
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
    
    public List<Responsavel> getParticipantes_do_deparamento()
    {
        Responsavel usuarioAtual;
        
        usuarioAtual = getUsuarioLogado();
        
        participantes_do_deparamento = responsavelServico.listar_nao_aprovados(usuarioAtual);
        return participantes_do_deparamento;
    }

    public void setParticipantes_do_deparamento(List<Responsavel> participantes_do_deparamento)
    {
        this.participantes_do_deparamento = participantes_do_deparamento;
    }
    
    public Titulos[] getTitulos()
    {
        titulos = Titulos.values();
        return titulos;
    }
    
    public PerfilGoogle getPerfilGoogle()
    {
        return perfilGoogle;
    }

    public void setPerfilGoogle(PerfilGoogle perfilGoogle)
    {
        this.perfilGoogle = perfilGoogle;
    }
    
    public PerfilGoogleServico getGoogleServico()
    {
        return googleServico;
    }

    public void setGoogleServico(PerfilGoogleServico googleServico)
    {
        this.googleServico = googleServico;
    }
}
