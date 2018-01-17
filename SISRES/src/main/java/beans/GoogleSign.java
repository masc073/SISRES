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
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Collections;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import org.primefaces.context.RequestContext;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @EJB
    private LoginBean loginBean;

    public void loginGoogle() throws ServletException, IOException
    {

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        PerfilGoogle perfilGoogle;

        Payload payload = verificarIntegridade(idToken);

        if (payload != null) {
            System.out.println(payload.getHostedDomain());
        }

        if (payload != null && payload.getHostedDomain().equals("a.recife.ifpe.edu.br")) {
            perfilGoogle = new PerfilGoogle();
            perfilGoogle.setFamilyName((String) payload.get("family_name"));
            perfilGoogle.setGivenName((String) payload.get("given_name"));
            perfilGoogle.setHostedDomain(payload.getHostedDomain());
            perfilGoogle.setPicture((String) payload.get("picture"));
            perfilGoogle.setSubject(payload.getSubject());
            String email = payload.getEmail();
            String nome = (String) payload.get("name");

            ExternalContext ec = fc.getExternalContext();
            HttpSession session = (HttpSession) ec.getSession(true);

            Long id = loginBean.consultarIbByEmail(email);

            session.setAttribute("perfilGoogle", perfilGoogle);
            session.setAttribute("nome", nome);
            session.setAttribute("email", email);
            session.setAttribute("id", id);

            HttpServletRequest request = (HttpServletRequest) ec.getRequest();
            request.login(email, perfilGoogle.getSubject());
            ec.redirect("../comum/homepage.xhtml");
        }
        else {
            context.addCallbackParam("logou", "Utilize seu email instituncional");
        }
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

    public String getIdToken()
    {
        return idToken;
    }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }
}
