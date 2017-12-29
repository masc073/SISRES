package dominio;

import dominio.Departamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-28T22:39:48")
@StaticMetamodel(Responsavel.class)
public class Responsavel_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Responsavel, Boolean> servidor;
    public static volatile SingularAttribute<Responsavel, String> senhaDigital;
    public static volatile SingularAttribute<Responsavel, Boolean> aprovado;
    public static volatile SingularAttribute<Responsavel, Boolean> lider;
    public static volatile SingularAttribute<Responsavel, Departamento> departamento;
    public static volatile SingularAttribute<Responsavel, String> nome;
    public static volatile SingularAttribute<Responsavel, String> email;
    public static volatile SingularAttribute<Responsavel, Integer> numeroAleatorio;

}