package dominio;

import dominio.Grupo;
import dominio.Titulos;
import dominio.UnidadeOrganizacional;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-15T23:33:57")
@StaticMetamodel(Responsavel.class)
public class Responsavel_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Responsavel, UnidadeOrganizacional> unidadeOrganizacional;
    public static volatile SingularAttribute<Responsavel, Boolean> aprovado;
    public static volatile SingularAttribute<Responsavel, String> matricula;
    public static volatile SingularAttribute<Responsavel, Grupo> grupo;
    public static volatile SingularAttribute<Responsavel, Titulos> titulo;
    public static volatile SingularAttribute<Responsavel, String> nome;
    public static volatile SingularAttribute<Responsavel, String> email;

}