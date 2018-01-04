package dominio;

import dominio.Departamento;
import dominio.Grupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-03T22:27:38")
@StaticMetamodel(Responsavel.class)
public class Responsavel_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Responsavel, Boolean> servidor;
    public static volatile SingularAttribute<Responsavel, String> senhaDigital;
    public static volatile SingularAttribute<Responsavel, Boolean> aprovado;
    public static volatile SingularAttribute<Responsavel, Boolean> lider;
    public static volatile SingularAttribute<Responsavel, Grupo> grupo;
    public static volatile SingularAttribute<Responsavel, Departamento> departamento;
    public static volatile SingularAttribute<Responsavel, String> nome;
    public static volatile SingularAttribute<Responsavel, String> email;
    public static volatile SingularAttribute<Responsavel, Integer> numeroAleatorio;

}