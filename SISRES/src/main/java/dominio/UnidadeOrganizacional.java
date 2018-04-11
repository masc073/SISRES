package dominio;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/** Define a Unidade Organizacional da Instituição, que pode ser : Diretoria, Coordenadoria ou Departamento.
 * @author Natália Amâncio
 */

@Entity
@SequenceGenerator(name = "DEPARTAMENTO_SEQUENCE", sequenceName = "DEPARTAMENTO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class UnidadeOrganizacional extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 60, message = "O nome deve conter entre 2 à 60 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve possuir apenas letras.")
    private String nome;

    @Size(min = 2, max = 6, message = "A sigla deve conter entre 2 à 6 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "A sigla deve possuir apenas letras.")
    private String sigla;
    
    @NotNull(message = "O Tipo da unidade organizacional deve ser selecionada.")
    @Enumerated(EnumType.STRING)
    private TiposUnidadesOrganizacionais tiposUnidadesOrganizacionais;
    
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    @OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_mae", referencedColumnName = "id")
    private UnidadeOrganizacional mae;

    /** Construtor Padrão
     */
    public UnidadeOrganizacional() {  }

    /** Retorna nome da unidade organizacional
     * @return String Nome da unidade organizacional
     */
    public String getNome()
    {
        return nome;
    }

    /** Atribui um nome da unidade organizacional
     * @param nome Nome da unidade organizacional
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /** Retorna a sigla da Unidade Organizacional
     * @return String Sigla da unidade organizacional
     */
    public String getSigla()
    {
        return sigla;
    }
    
    /** Atribui a sigla da Unidade Organizacional
     * @param sigla Sigla da unidade organizacional
     */
    public void setSigla(String sigla)
    {
        this.sigla = sigla;
    }

    /** Retorna o responsável pela unidade organizacional
     * @return Responsavel Responsável pela unidade organizacional
     */
    public Responsavel getResponsavel()
    {
        return responsavel;
    }

    /** Define o responsável pela unidade organizacional
     * @param responsavel Responsável pela unidade organizacional
     */
    public void setResponsavel(Responsavel responsavel)
    {
        this.responsavel = responsavel;
    }
    
    /** Retorna a unidade organizacional diretamente superior a esta.
     * @return UnidadeOrganizacional Unidade organizacional diretamente superior a esta.
     */
    public UnidadeOrganizacional getMae()
    {
        return mae;
    }
    
    /** Atribui a unidade organizacional diretamente superior a esta.
     * @param mae Unidade organizacional diretamente superior a esta.
     */
    public void setMae(UnidadeOrganizacional mae)
    {
        this.mae = mae;
    }
    
    /** Retorna o tipo da unidade organizacional, que pode ser: Diretoria, Coordenadoria ou Departamento.
     * @return TiposUnidadesOrganizacionais Tipo da Unidade Organizacional 
     */
    public TiposUnidadesOrganizacionais getTiposUnidadesOrganizacionais()
    {
        return tiposUnidadesOrganizacionais;
    }

     /** Atribui o tipo da unidade organizacional, que pode ser: Diretoria, Coordenadoria ou Departamento.
     * @param tiposUnidadesOrganizacionais Tipo da Unidade Organizacional 
     */
    public void setTiposUnidadesOrganizacionais(TiposUnidadesOrganizacionais tiposUnidadesOrganizacionais)
    {
        this.tiposUnidadesOrganizacionais = tiposUnidadesOrganizacionais;
    }
    
}
