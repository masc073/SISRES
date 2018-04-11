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

/** Classe responsável por realizar a comunicação do jsf com a camada de serviço com relação ao grupo.
 * @author Natália Amâncio
 */
@ManagedBean
@SessionScoped
public class GrupoBean implements Serializable
{

    @EJB
    private GrupoServico grupoServico;
    
    /** Lista de grupos cadastrados no banco de dados.
     */
    public List<Grupo> grupos;
    
    /** Grupo que está sendo manipulado no momento.
     */
    public Grupo grupo;

    /** Construtor padrão.
     */
    public GrupoBean()
    {
        grupo = new Grupo();
        grupos = new ArrayList<>();
    }

    /** Retorna o objeto do GrupoServico
     * @return GrupoServico - permite acessar os métodos da classe GrupoServico
     */
    public GrupoServico getGrupoServico()
    {
        return grupoServico;
    }

    /** Seta uma instância de do grupoServico para o objeto da classe.
     * @param grupoServico nova instância do grupoServico.
     */
    public void setGrupoServico(GrupoServico grupoServico)
    {
        this.grupoServico = grupoServico;
    }

    /** Retorna a lista de Grupo
     * @return grupos
     */
    public List<Grupo> getGrupos()
    {
        return grupos;
    }

    /** Seta um valor para a lista de grupos.
     * @param grupos Lista de Grupos
     */
    public void setGrupos(List<Grupo> grupos)
    {
        listar();
        this.grupos = grupos;
    }

    /** Retorna o objeto grupo
     * @return grupo
     */
    public Grupo getGrupo()
    {
        return grupo;
    }

    /**Atribui valor para o objeto grupo
     * @param grupo
     */
    public void setGrupo(Grupo grupo)
    {
        this.grupo = grupo;
    }

    /** Lista todos os grupos cadastrados no banco de dados.
     */
    public void listar()
    {
        grupos = grupoServico.listar();
    }
    
    /** Exibe mensagens para o usuário com relação ao grupo.
     * @param mensagem  Mensagem que será exibida para o usuário
     * @param severity  Define o tipo da mensagem.
      */
    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem)
    {
        FacesMessage message = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
