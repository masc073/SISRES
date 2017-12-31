package dominio;

import dominio.Arquivo;
import dominio.AtividadeModelo;
import dominio.Requerimento;
import dominio.SituacaoAtividade;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-31T00:03:45")
@StaticMetamodel(Atividade.class)
public class Atividade_ extends EntidadeNegocio_ {

    public static volatile SingularAttribute<Atividade, SituacaoAtividade> situacao;
    public static volatile SingularAttribute<Atividade, AtividadeModelo> atividademodelo;
    public static volatile SingularAttribute<Atividade, Arquivo> arquivo;
    public static volatile SingularAttribute<Atividade, String> descricao_sucesso;
    public static volatile SingularAttribute<Atividade, Requerimento> requerimento;
    public static volatile SingularAttribute<Atividade, String> descricao_erro;

}