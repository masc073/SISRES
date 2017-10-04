package dominio;

import dominio.Departamento;
import dominio.Processo;
import dominio.SituacaoAtividade;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-10-01T00:53:02")
@StaticMetamodel(Atividade.class)
public class Atividade_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Atividade, SituacaoAtividade> situacao;
    public static volatile SingularAttribute<Atividade, Processo> processo;
    public static volatile SingularAttribute<Atividade, Departamento> departamento;
    public static volatile SingularAttribute<Atividade, String> nome;

}