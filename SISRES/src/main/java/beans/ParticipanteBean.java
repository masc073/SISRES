/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dominio.Participante;
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
import servico.ParticipanteServico;

@ManagedBean(name = "participanteBean")
@ViewScoped
public class ParticipanteBean implements Serializable
{

    @EJB
    private ParticipanteServico participanteServico;

    protected List<Participante> participantes = new ArrayList<>();

    protected Participante participante;
    
    protected String senhaConfirmacao;

    public ParticipanteBean()
    {
        participante = new Participante();
    }

    public void salvar()
    {
        try {
            participanteServico.salvar(participante);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Participante cadastrado com Sucesso!");
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

        participante = new Participante();
        listar();
    }

    public void editar(RowEditEvent event) throws ExcecaoNegocio
    {
        participante = (Participante) event.getObject();
        listar();

        try {
            participanteServico.atualizar(participante);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Participante alterado com Sucesso!");
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

    public void remover(Participante participante)
    {
        try {
            participanteServico.remover(participante);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Participante removido com Sucesso!");

        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMensagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            }
        }
        listar();
    }

    public void listar()
    {
        participantes = participanteServico.listar();
    }

    public ParticipanteServico getParticipanteServico()
    {
        return participanteServico;
    }

    public void setParticipanteServico(ParticipanteServico participanteServico)
    {
        this.participanteServico = participanteServico;
    }

    public List<Participante> getParticipantes()
    {
        if (participantes.isEmpty()) {
            participantes = participanteServico.listar();
        }

        return participantes;
    }

    public void setParticipantes(List<Participante> participantes)
    {
        this.participantes = participantes;
    }

    public Participante getParticipante()
    {
        return participante;
    }

    public void setParticipante(Participante participante)
    {
        this.participante = participante;
    }

    protected void adicionarMensagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severity, mensagem, null));
    }
    
    public String getSenhaConfirmacao()
    {
        return senhaConfirmacao;
    }

    public void setSenhaConfirmacao(String senhaConfirmacao)
    {
        this.senhaConfirmacao = senhaConfirmacao;
    }
}
