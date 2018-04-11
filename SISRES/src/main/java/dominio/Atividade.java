package dominio;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/** Define a atividade que será utilizada no requerimento.
 * @author Natália Amâncio
 */

@Entity
@SequenceGenerator(name = "ATIVIDADE_SEQUENCE", sequenceName = "ATIVIDADE_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Atividade extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "A situação deve ser selecionada.")
    @Enumerated(EnumType.STRING)
    private SituacaoAtividade situacao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_requerimento", referencedColumnName = "id")
    protected Requerimento requerimento;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "atividademodelo", referencedColumnName = "id")
    private AtividadeModelo atividademodelo;

    private String descricao_sucesso;
    
    private String descricao_erro;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Arquivo arquivo;

    /** Construtor Padrão
     */
    public Atividade()
    {
        
    }

    /** Retorna o valor da situação da atividade
     * @return SituacaoAtividade Situação da Atividade
     */
    public SituacaoAtividade getSituacao()
    {
        return situacao;
    }

    /** Seta a situação da atividade, que pode ser : Em Andamento, Finalizada, Em Espera ou Rejeitada.
     * @param  situacao Situação da Atividade
     */
    public void setSituacao(SituacaoAtividade situacao)
    {
        this.situacao = situacao;
    }

    /** Retorna o arquivo da atividade
     * @return Arquivo - Arquivo da Atividade
     */
    public Arquivo getArquivo()
    {
        return arquivo;
    }

    /** Atribui um arquivo para a atividade
     * @param arquivo - Arquivo da atividade
     */
    public void setArquivo(Arquivo arquivo)
    {
        this.arquivo = arquivo;
    }

    /** Cria uma nova instância do arquivo
     * @return Arquivo - Nova instância do arquivo
     */
    public Arquivo criarArquivo()
    {
        return new Arquivo();
    }

    /** Retorna o requerimento ao qual esta atividade está relacionada
     * @return Requerimento - Requerimento vinculado a atividade
     */
    public Requerimento getRequerimento()
    {
        return requerimento;
    }

    /** Atribui a atividade a um requerimento
     * @param requerimento Requerimento ao qual a atividade será vinculada
     */
    public void setRequerimento(Requerimento requerimento)
    {
        this.requerimento = requerimento;
    }

    /** Retorna o Atividade Modelo desta atividade
     * @return AtividadeModelo
     */
    public AtividadeModelo getAtividademodelo()
    {
        return atividademodelo;
    }

    /** Define a atividade modelo desta atividade
     * @param atividademodelo 
     */
    public void setAtividademodelo(AtividadeModelo atividademodelo)
    {
        this.atividademodelo = atividademodelo;
    }

    /** Retorna descrição de sucesso
     * @return String Descrição de Sucesso
     */
    public String getDescricao_sucesso()
    {
        return descricao_sucesso;
    }

    /** Atribui uma mensagem de Sucesso
     * @param descricao_sucesso Mensagem de Sucesso
     */
    public void setDescricao_sucesso(String descricao_sucesso)
    {
        this.descricao_sucesso = descricao_sucesso;
    }

    /** Retorna descrição de erro
     * @return String Descrição de Erro
     */
    public String getDescricao_erro()
    {
        return descricao_erro;
    }
    
    /** Atribui uma mensagem de Erro
     * @param descricao_erro Mensagem de Erro
     */
    public void setDescricao_erro(String descricao_erro)
    {
        this.descricao_erro = descricao_erro;
    }

}
