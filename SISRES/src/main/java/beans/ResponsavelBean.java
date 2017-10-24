package beans;

import dominio.Responsavel;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import servico.ResponsavelServico;
import servico.Servico;

@ManagedBean(name = "responsavelBean")
@ViewScoped
public class ResponsavelBean extends Bean<Responsavel> implements Serializable
{

    @EJB
    private ResponsavelServico servico;

    @Override
    public Servico instanciaServico()
    {
        return new ResponsavelServico();
    }

}
