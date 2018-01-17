/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class PerfilGoogle extends EntidadeNegocio
{

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    private Responsavel usuario;

    @Column(name = "TXT_SUBJECT")
    private String subject;

    @Column(name = "TXT_PICTURE")
    private String picture;

    @Column(name = "TXT_GIVEN_NAME")
    private String givenName;

    @Column(name = "TXT_FAMILY_NAME")
    private String familyName;

    @Column(name = "TXT_HOSTED_DOMAIN")
    private String hostedDomain;

    public Responsavel getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Responsavel usuario)
    {
        this.usuario = usuario;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getGivenName()
    {
        return givenName;
    }

    public void setGivenName(String givenName)
    {
        this.givenName = givenName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
    }

    public String getHostedDomain()
    {
        return hostedDomain;
    }

    public void setHostedDomain(String hostedDomain)
    {
        this.hostedDomain = hostedDomain;
    }

}
