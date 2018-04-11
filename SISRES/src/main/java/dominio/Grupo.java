package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/** Define os grupos do sistema
 * @author Natália Amâncio
 */

@Entity
@SequenceGenerator(name = "GRUPO_SEQUENCE", sequenceName = "GRUPO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Grupo extends EntidadeNegocio implements Serializable
{

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "[A-Za-z ]+")
    private String nome;

    @OneToMany(mappedBy = "grupo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Responsavel> responsaveis;

    /** Retorna nome do grupo
     * @return String Nome do grupo
     */
    public String getNome()
    {
        return nome;
    }

     /** Atribui um nome ao grupo
     * @param nome Nome do grupo
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /** Retorna a lista de responsáveis vinculados ao grupo
     * @return List<Responsavel> Lista de Responsáveis
     */
    public List<Responsavel> getResponsaveis()
    {
        return responsaveis;
    }

    /** Construtor Padrão
     */
    public Grupo()
    {
        responsaveis = new ArrayList<>();
    }

    /** Vincula responsáveis ao grupo
     * @param novos_responsaveis Lista de Responsáveis
     */
    public void setResponsaveis(List<Responsavel> novos_responsaveis)
    {
        if (novos_responsaveis != null) {
            for (Responsavel responsavel : novos_responsaveis) {
                if (!responsaveis.contains(responsavel)) {
                    responsaveis.add(responsavel);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null) {
            if (o instanceof Grupo) {
                Grupo outra = (Grupo) o;
                if (this.id == outra.id) {
                    return true;
                }
            }
        }
        return false;
    }

}
