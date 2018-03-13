/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import dominio.PerfilGoogle;
import dominio.Responsavel;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import org.primefaces.context.RequestContext;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import servico.GrupoServico;
import servico.ResponsavelServico;

/**
 *
 * @author natal
 */
@ManagedBean(name = "googleSign")
public class GoogleSign implements Serializable
{

    private static final long serialVersionUID = 1L;

    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @ManagedProperty("#{param.idToken}")
    private String idToken;

    private String username;

    @EJB
    private ResponsavelServico responsavelServico;

    @EJB
    private GrupoServico grupoServico;

    public void loginGoogle() throws ServletException
    {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        PerfilGoogle perfilGoogle;

//        if (idToken != null) {
        Payload payload = verificarIntegridade(idToken);

        if (payload != null) {
            perfilGoogle = new PerfilGoogle();
            perfilGoogle.setFamilyName((String) payload.get("family_name"));
            perfilGoogle.setGivenName((String) payload.get("given_name"));
            perfilGoogle.setHostedDomain(payload.getHostedDomain());
            perfilGoogle.setPicture((String) payload.get("picture"));
            perfilGoogle.setSubject(payload.getSubject());
            String email = payload.getEmail();

            setUsername(email);

            String nome = (String) payload.get("name");

            ExternalContext ec = fc.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(true);

            Responsavel responsavel_atual = consultarResponsavel(username);

            if (responsavel_atual != null) {

                session.setAttribute("perfilGoogle", perfilGoogle);
                session.setAttribute("nome", nome);
                session.setAttribute("email", email);
                session.setAttribute("id", responsavel_atual.getId());

                HttpServletRequest request = (HttpServletRequest) ec.getRequest();
                try {

                    System.out.println("Subject: " + perfilGoogle.getSubject());      
                    System.out.println("Email: " + email);

                    request.login(email, perfilGoogle.getSubject());

                    System.out.println("Usuário logado: " + responsavel_atual.getEmail());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", responsavel_atual);
                    String grupo = grupoServico.buscar_grupo_responsavel(username);
                    request.setAttribute("grupo", grupo);
                    System.out.println("Grupo : " + grupo);
//                    request.login(username, "");
//                    System.out.println("beans.GoogleSign.loginGoogle()" + request.);
//                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("grupoAtual", grupo);
                    ec.redirect("template.xhtml");
                }
                catch (IOException ex) {
                    Logger.getLogger(GoogleSign.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                adicionarMessagem(FacesMessage.SEVERITY_INFO, "Usuário não cadastrado no sistema. Favor cadastre-se!");
                logout();
            }
        }
//        }
    }

    public String logout() throws ServletException
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            System.out.println("INVALIDEEEEEEEEI!");
            session.invalidate();
        }

        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        request.logout();
        return "sair";
    }

    private Payload verificarIntegridade(String idToken)
    {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
                .setAudience(Collections.singletonList("648373580314-tk04f76nc7ickcn53ma6dln763vji45f.apps.googleusercontent.com"))
                .build();

        Payload payload = null;

        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (idToken != null) {
                payload = googleIdToken.getPayload();
            }

        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return payload;
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

    public String getIdToken()
    {
        return idToken;
    }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }

    public Responsavel consultarResponsavel(String email)
    {
        Responsavel responsavel;
        Long retorno;

        responsavel = responsavelServico.getResponsavelByEmail(username);

        if (responsavel != null) {
            return responsavel;
        }
        else {
            return null;
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
