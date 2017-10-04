package dominio;

import dominio.Responsavel;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-10-01T00:53:02")
@StaticMetamodel(Departamento.class)
public class Departamento_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Departamento, String> sigla;
    public static volatile SingularAttribute<Departamento, String> nome;
    public static volatile SingularAttribute<Departamento, Responsavel> responsavel;

}