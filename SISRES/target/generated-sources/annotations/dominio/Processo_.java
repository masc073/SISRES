package dominio;

import dominio.Atividade;
import dominio.Responsavel;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-12T17:05:04")
@StaticMetamodel(Processo.class)
public class Processo_ extends EntidadeNegocio_ {

    public static volatile ListAttribute<Processo, Atividade> atividades;
    public static volatile SingularAttribute<Processo, Integer> duracaoMaximaEmDias;
    public static volatile SingularAttribute<Processo, Date> dataDeInicio;
    public static volatile SingularAttribute<Processo, Date> dataDeCriacao;
    public static volatile SingularAttribute<Processo, String> nome;
    public static volatile SingularAttribute<Processo, Date> dataDeFim;
    public static volatile SingularAttribute<Processo, Responsavel> responsavel;

}