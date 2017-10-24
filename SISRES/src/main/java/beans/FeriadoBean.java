package beans;

import dominio.Feriado;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import servico.FeriadoServico;
import servico.Servico;

@ManagedBean(name = "feriadoBean")
@ViewScoped
public class FeriadoBean extends Bean<Feriado> implements Serializable
{
    @EJB
    private FeriadoServico servico;
    
    
    @Override
    public Servico instanciaServico()
    {
       return new FeriadoServico();
    }
    
}
