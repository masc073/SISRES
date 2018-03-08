package dominio;

import dominio.Atividade;
import dominio.Processo;
import dominio.UnidadeOrganizacional;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-07T23:05:20")
@StaticMetamodel(AtividadeModelo.class)
public class AtividadeModelo_ extends EntidadeNegocio_ {

    public static volatile ListAttribute<AtividadeModelo, Atividade> atividades;
    public static volatile SingularAttribute<AtividadeModelo, Processo> processo;
    public static volatile SingularAttribute<AtividadeModelo, String> nome;
    public static volatile SingularAttribute<AtividadeModelo, UnidadeOrganizacional> unidade_organizacional;
    public static volatile SingularAttribute<AtividadeModelo, Boolean> anexarArquivo;

}