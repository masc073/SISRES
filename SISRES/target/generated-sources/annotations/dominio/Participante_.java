package dominio;

import dominio.Departamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-22T23:38:14")
@StaticMetamodel(Participante.class)
public class Participante_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Participante, String> senha;
    public static volatile SingularAttribute<Participante, Boolean> aprovado;
    public static volatile SingularAttribute<Participante, Departamento> departamento;
    public static volatile SingularAttribute<Participante, String> nome;
    public static volatile SingularAttribute<Participante, String> email;

}