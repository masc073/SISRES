package dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** Define o Responsável
 * @author Natália Amâncio
 */
@Entity
@SequenceGenerator(name = "RESPONSAVEL_SEQUENCE", sequenceName = "RESPONSAVEL_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Responsavel extends EntidadeNegocio implements Serializable
{

    @NotNull(message = "O nome deve ser preenchido.")
    @Size(min = 2, max = 40, message = "O nome deve conter entre 2 à 40 caracteres.")
    @Pattern(regexp = "[A-Za-zà-úÀ-Ú ]+", message = "O nome deve possuir apenas letras.")
    private String nome;

    @validadores.ValidaEmail
    @NotNull(message = "O Email deve ser preenchido.")
    @Email(message = "Informar email válido!")
    private String email; // Validar se é gmail ou institucional.

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    private UnidadeOrganizacional unidadeOrganizacional;

//    @NotNull(message = "O título deve ser selecionado.")
    @Enumerated(EnumType.STRING)
    private Titulos titulo;

    @Column
    private boolean aprovado;
    
    @Column
    private String matricula;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    private Grupo grupo;

     /** Construtor Padrão
     */  
    public Responsavel()
    {

    }

    /** Retorna Email do Responsável
     * @return String Email do Responsável
     */
    public String getEmail()
    {
        return email;
    }
    
    /** Atribui Email do Responsável
     * @param email Email do Responsável
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /** Retorna a unidade organizacional do Responsável
     * @return UnidadeOrganizacional Unidade Organizacional do Responsável
     */
    public UnidadeOrganizacional getUnidadeOrganizacional()
    {
        return unidadeOrganizacional;
    }
    
    /** Atribui a unidade organizacional do Responsável
     * @param unidadeOrganizacional Unidade Organizacional do Responsável
     */
    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional)
    {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }
    
     /** Retorna se a responsável foi aprovado ou não.
     * @return boolean - True ( Aprovado ) - False ( Reprovado )
     */
    public boolean isAprovado()
    {
        return aprovado;
    }
    
     /** Define se a responsável foi aprovado ou não.
     * @param aprovado - True ( Aprovado ) - False ( Reprovado )
     */
    public void setAprovado(boolean aprovado)
    {
        this.aprovado = aprovado;
    }
    
    /** Retorna nome do responsável
     * @return String Nome do responsável
     */
    public String getNome()
    {
        return nome;
    }
    
    /** Atribui um nome ao responsável
     * @param nome Nome do responsável
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /** Retorna o grupo do responsável.
     * @return Grupo Grupo do responsável.
     */
    public Grupo getGrupo()
    {
        return grupo;
    }
    
    /** Atribui o grupo do responsável.
     * @param grupo Grupo do responsável.
     */
    public void setGrupo(Grupo grupo)
    {
        this.grupo = grupo;
    }

    /** Retorna o titulo do responsável, ou seja, se ele é  Coordenador,  Diretor, Servidor, Professor, Administrador ou Aluno.
     * @return Titulos Titulo do responsável
     */
    public Titulos getTitulo()
    {
        return titulo;
    }

    /** Define o título do responsável
     * @param titulo Título do responsável
     */
    public void setTitulo(Titulos titulo)
    {
        this.titulo = titulo;
    }
    
    /** Retorna a Matrícula, caso o responsável seja um aluno.
     * @return String Matrícula.
     */
    public String getMatricula()
    {
        return matricula;
    }

    /** Atribui a Matrícula, caso o responsável seja um aluno.
     * @param matricula Matrícula.
     */
    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }

}
