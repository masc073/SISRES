package dominio;

import dominio.Arquivo;
import dominio.Departamento;
import dominio.Processo;
import dominio.SituacaoAtividade;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-07T11:06:25")
@StaticMetamodel(Atividade.class)
public class Atividade_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Atividade, SituacaoAtividade> situacao;
    public static volatile SingularAttribute<Atividade, Processo> processo;
    public static volatile SingularAttribute<Atividade, Departamento> departamento;
    public static volatile SingularAttribute<Atividade, Arquivo> arquivo;
    public static volatile SingularAttribute<Atividade, String> nome;
    public static volatile SingularAttribute<Atividade, Boolean> anexarArquivo;
    public static volatile SingularAttribute<Atividade, String> descricao;

}