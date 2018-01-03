package dominio;

import dominio.Atividade;
import dominio.Departamento;
import dominio.Processo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-02T21:01:50")
@StaticMetamodel(AtividadeModelo.class)
public class AtividadeModelo_ extends EntidadeNegocio_ {

    public static volatile ListAttribute<AtividadeModelo, Atividade> atividades;
    public static volatile SingularAttribute<AtividadeModelo, Processo> processo;
    public static volatile SingularAttribute<AtividadeModelo, Departamento> departamento;
    public static volatile SingularAttribute<AtividadeModelo, String> nome;
    public static volatile SingularAttribute<AtividadeModelo, Boolean> anexarArquivo;

}