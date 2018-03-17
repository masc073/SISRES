package dominio;

import dominio.Responsavel;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T09:47:49")
@StaticMetamodel(Grupo.class)
public class Grupo_ extends EntidadeNegocio_ {

    public static volatile ListAttribute<Grupo, Responsavel> responsaveis;
    public static volatile SingularAttribute<Grupo, String> nome;

}