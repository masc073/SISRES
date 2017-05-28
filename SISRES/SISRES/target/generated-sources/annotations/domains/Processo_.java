package domains;

import domains.Atividade;
import domains.Responsavel;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-05-27T23:11:42")
@StaticMetamodel(Processo.class)
public class Processo_ { 

    public static volatile ListAttribute<Processo, Atividade> atividades;
    public static volatile SingularAttribute<Processo, Responsavel> criador;
    public static volatile SingularAttribute<Processo, Atividade> atividadeInicial;
    public static volatile SingularAttribute<Processo, String> instituicao;
    public static volatile SingularAttribute<Processo, Date> dataDeCriacao;
    public static volatile SingularAttribute<Processo, String> nome;
    public static volatile SingularAttribute<Processo, Integer> id;
    public static volatile SingularAttribute<Processo, Atividade> atividadeFinal;

}