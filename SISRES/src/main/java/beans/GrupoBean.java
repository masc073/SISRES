/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dominio.Grupo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import servico.GrupoServico;

@ManagedBean
@SessionScoped
public class GrupoBean implements Serializable
{

    @EJB
    private GrupoServico grupoServico;
    public List<Grupo> grupos;
    public Grupo grupo;

    public GrupoBean()
    {
        grupo = new Grupo();
        grupos = new ArrayList<>();
    }

    public GrupoServico getGrupoServico()
    {
        return grupoServico;
    }

    public void setGrupoServico(GrupoServico grupoServico)
    {
        this.grupoServico = grupoServico;
    }

    public List<Grupo> getGrupos()
    {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos)
    {
        listar();
        this.grupos = grupos;
    }

    public Grupo getGrupo()
    {
        return grupo;
    }

    public void setGrupo(Grupo grupo)
    {
        this.grupo = grupo;
    }

    public void listar()
    {
        grupos = grupoServico.listar();
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
