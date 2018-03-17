package beans;

import dominio.Responsavel;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import servico.GrupoServico;
import servico.ResponsavelServico;

/**
 * Classe responsável por realizar a comunicaçao da tela de login com a camada
 * de serviço.
 *
 * @author Natália Amâncio
 */
@ManagedBean(name = "loginBean")
public class LoginBean implements Serializable
{

    @EJB
    private ResponsavelServico responsavelServico;

    @NotBlank
    String username;
    @NotBlank
    String senha;

    private static final Logger LOGGER = Logger.getGlobal();

    static {
        LOGGER.setLevel(Level.FINEST);
    }

    /* Realiza o login, chamando o realm.
     */
    public String efetuarLogin()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.login(username, senha); //chama indiretamente o codigo authent user  
//chama indiretamente o codigo authent user  

            String grupo = responsavelServico.buscar_grupo_responsavel(username);

            //seta pessoa na sessão
            Responsavel responsavelLogado = responsavelServico.getResponsavelByEmail(username);

            if (responsavelLogado == null) {
                adicionarMessagem(FacesMessage.SEVERITY_WARN, "Dados inválidos!");
                return "";
            }
            else {

                LOGGER.fine("Logado: " + grupo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", responsavelLogado);
                return "sucesso";
            }

        }
        catch (ServletException ex) {

            ex.getMessage();

            return "falha";
        }
    }

    /**
     * Redireciona página para o cadastro de usuário.
     *
     * @throws IOException
     */
    public void redirecionaParaCadastro() throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect("cadastro.xhtml");
    }

    /**
     * Realiza o logout, encerrando a sessão do usuário.
     *
     * @throws ServletException
     * @return String - retorna para o página de login. Configurado no
     * faces-config.xml
     */
    public String logout() throws ServletException
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            System.out.println("INVALIDEI!");
            session.invalidate();
        }
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        request.logout();
        return "sair";
    }

    /**
     * Exibe mensagens para o usuário em relação do login.
     *
     * @param mensagem Mensagem que será exibida para o usuário
     * @param severity Define o tipo da mensagem.
     */
    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Retorna o email do usuário
     *
     * @return String - Email do login
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Seta um email para o username.
     *
     * @param username Email do usuário.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Retorna senha do usuário
     *
     * @return String - Senha do usuário
     */
    public String getSenha()
    {
        return senha;
    }

    /**
     * Seta valor na senha.
     *
     * @param senha
     */
    public void setSenha(String senha)
    {
        this.senha = senha;
    }
}
