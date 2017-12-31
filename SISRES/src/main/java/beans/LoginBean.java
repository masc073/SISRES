/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dominio.Responsavel;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import servico.GrupoServico;
import servico.ResponsavelServico;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable
{

    @EJB
    private ResponsavelServico responsavelServico;

    @NotBlank
    String username;
    @NotBlank
    String senha;

    @EJB
    private GrupoServico grupoServico;

    private static final Logger LOGGER = Logger.getGlobal();

    static {
        LOGGER.setLevel(Level.FINEST);
    }

    public String efetuarLogin()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        try {
            request.login(username, senha); //chama indiretamente o codigo authent user  
//chama indiretamente o codigo authent user  

            String grupo = grupoServico.buscar_grupo_responsavel(username);

            //seta pessoa na sessão
            Responsavel responsavelLogado = responsavelServico.getResponsavelByEmail(username);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", responsavelLogado);

            LOGGER.fine("Logado: " + grupo);

            return "sucesso";
        }
        catch (ServletException ex) {
            ex.printStackTrace();
            return "falha";
        }
    }

    public String logout() throws ServletException
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        request.logout();
        return "sair";
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }
}
