    package dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Define a atividade modelo que será utilizada para definir o processo.
 * @author Natália Amâncio
 */
@Entity
@SequenceGenerator(name = "ATIVIDADE_MODELO_SEQUENCE", sequenceName = "ATIVIDADE_MODELO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class AtividadeModelo extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 60, message = "O nome deve conter entre 2 à 60 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve conter apenas letras.")
    private String nome;

    @NotNull(message = "A Unidade Organizacional deve ser selecionada.")
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_unidadeOrganizacional", referencedColumnName = "id")
    private UnidadeOrganizacional unidade_organizacional;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_processo", referencedColumnName = "id")
    protected Processo processo;

    @OneToMany(mappedBy = "atividademodelo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades;

    private boolean anexarArquivo;
    
    /** Construtor Padrão
     */
    public AtividadeModelo()
    {

    }
    
    /** Retorna nome da atividade modelo
     * @return String - Nome da atividade
     */
    public String getNome()
    {
        return nome;
    }
    
    /** Atribui nome da atividade modelo
     * @param nome - Nome da atividade
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    /** Retorna a unidade Organizacional responsável por essa atividade modelo
     * @return UnidadeOrganizacional - Unidade Organizacional 
     */
    public UnidadeOrganizacional getUnidade_organizacional()
    {
        return unidade_organizacional;
    }
    
    /** Atribui uma unidade Organizacional para essa atividade modelo
     * @param unidade_organizacional - Unidade Organizacional 
     */
    public void setUnidade_organizacional(UnidadeOrganizacional unidade_organizacional)
    {
        this.unidade_organizacional = unidade_organizacional;
    }

    /** Retorna o processo que esta atividademodelo define
     * @return Processo - Processo
     */
    public Processo getProcesso()
    {
        return processo;
    }

     /** Atribui um processo que esta atividademodelo 
     * @param processo - Processo
     */
    public void setProcesso(Processo processo)
    {
        this.processo = processo;
    }
    
     /** Retorna se a atividademodelo precisa anexar aquivo ou não.
     * @return boolean - True ( Precisa de arquivo ) - False ( Não precisa de arquivo )
     */
    public boolean isAnexarArquivo()
    {
        return anexarArquivo;
    }
    
     /** Atribui o valor do anexarArquivo.
     * @param anexarArquivo - True ( Precisa de arquivo ) - False ( Não precisa de arquivo )
     */
    public void setAnexarArquivo(boolean anexarArquivo)
    {
        this.anexarArquivo = anexarArquivo;
    }

    /** Lista de atividades que são definidas por meio desta atividade modelo.
     * @return List<Atividade>
     */
    public List<Atividade> getAtividades()
    {
        return atividades;
    }
    
    /** Atribui a Lista de atividades que são definidas por meio desta atividade modelo.
     * @param atividades
     */
    public void setAtividades(List<Atividade> atividades)
    {
        this.atividades = atividades;
    }

}
