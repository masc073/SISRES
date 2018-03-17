package dominio;

import dominio.Atividade;
import dominio.Processo;
import dominio.Responsavel;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T09:47:49")
@StaticMetamodel(Requerimento.class)
public class Requerimento_ extends EntidadeNegocio_ {

    public static volatile ListAttribute<Requerimento, Atividade> atividades;
    public static volatile SingularAttribute<Requerimento, Boolean> atrasado;
    public static volatile SingularAttribute<Requerimento, String> matriculaAluno;
    public static volatile SingularAttribute<Requerimento, Processo> processo;
    public static volatile SingularAttribute<Requerimento, Atividade> estadoAtual;
    public static volatile SingularAttribute<Requerimento, Responsavel> solicitante;
    public static volatile SingularAttribute<Requerimento, Date> dataDeInicio;
    public static volatile SingularAttribute<Requerimento, Boolean> finalizado;
    public static volatile SingularAttribute<Requerimento, Date> dataDeFim;

}