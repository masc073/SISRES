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

/** Classe responsável por realizar o login do Google.
 * @author Natália Amâncio
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

    /** Realiza a comunicação com o Google, para realizar o login do usuário.
     * @exception ServletException
     */
    public void loginGoogle() throws ServletException, IOException
    {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();

        Payload payload = verificarIntegridade(idToken);

        if (payload != null) {

            String email = payload.getEmail();

            setUsername(email);

            String nome = (String) payload.get("name");

            ExternalContext ec = fc.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(true);

            Responsavel responsavel_atual = consultarResponsavel(username);

            if (responsavel_atual != null) {

                session.setAttribute("nome", nome);
                session.setAttribute("email", email);
                session.setAttribute("id", responsavel_atual.getId());

                HttpServletRequest request = (HttpServletRequest) ec.getRequest();
                try {
    
                    System.out.println("Email: " + email);

                    request.login(email, payload.getSubject());
//                    System.out.print("Teste: " + request.);
                    
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", responsavel_atual);
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

    /** Realiza o logout do usuário, invalidando o seu token.
     */
    public String logout() throws ServletException, IOException
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        request.logout();
        System.out.println("Invalidando Sessão");
        
//        FacesContext.getCurrentInstance().getExternalContext().redirect("/SISRES/publico/login.xhtml");
        return "sair";
    }

    /** Valida a integridade do Token recebido.
     * @param idToken Token recebido após login do google.
     */
    private Payload verificarIntegridade(String idToken)
    {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
                .setAudience(Collections.singletonList("648373580314-tk04f76nc7ickcn53ma6dln763vji45f.apps.googleusercontent.com"))
                .build();

        Payload payload = null;

        try {
            System.out.println("Tokeeeeeeeeen : " +  idToken);
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

    
    /** Retorna o IdToken
     */
    public String getIdToken()
    {
        return idToken;
    }

    /** Seta valor para o idToken
     */
    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }

    /** Busca responsável por email.
     * @param email Email do usuário.
     */
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

    /** Retorna o nome do usuário
     */
    public String getUsername()
    {
        return username;
    }

    /** Seta o nome do usuário
     */
    public void setUsername(String username)
    {
        this.username = username;
    }
}
