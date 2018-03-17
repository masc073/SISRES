package dominio;

import dominio.Responsavel;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T09:47:49")
@StaticMetamodel(PerfilGoogle.class)
public class PerfilGoogle_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<PerfilGoogle, String> subject;
    public static volatile SingularAttribute<PerfilGoogle, String> givenName;
    public static volatile SingularAttribute<PerfilGoogle, String> familyName;
    public static volatile SingularAttribute<PerfilGoogle, String> hostedDomain;
    public static volatile SingularAttribute<PerfilGoogle, Responsavel> usuario;
    public static volatile SingularAttribute<PerfilGoogle, String> picture;

}