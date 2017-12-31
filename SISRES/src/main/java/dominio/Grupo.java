package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public List<Responsavel> getResponsaveis()
    {
        return responsaveis;
    }

    public Grupo()
    {
        responsaveis = new ArrayList<>();
    }

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
