package dominio;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-16T23:33:43")
@StaticMetamodel(Arquivo.class)
public class Arquivo_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Arquivo, String> extensao;
    public static volatile SingularAttribute<Arquivo, byte[]> arquivo;
    public static volatile SingularAttribute<Arquivo, String> nome;

}