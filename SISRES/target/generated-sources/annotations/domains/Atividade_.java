package domains;

import domains.Atividade;
import domains.Departamento;
import domains.Processo;
import domains.SituacaoAtividade;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-05-28T15:57:58")
@StaticMetamodel(Atividade.class)
public class Atividade_ { 

    public static volatile SingularAttribute<Atividade, Atividade> proximaAtividade;
    public static volatile SingularAttribute<Atividade, Date> duracaoMaxima;
    public static volatile SingularAttribute<Atividade, SituacaoAtividade> situacao;
    public static volatile SingularAttribute<Atividade, Processo> processo;
    public static volatile SingularAttribute<Atividade, Date> dataDeInicio;
    public static volatile SingularAttribute<Atividade, Departamento> departamento;
    public static volatile SingularAttribute<Atividade, String> nome;
    public static volatile SingularAttribute<Atividade, Integer> id;
    public static volatile SingularAttribute<Atividade, Date> dataDeFim;

}