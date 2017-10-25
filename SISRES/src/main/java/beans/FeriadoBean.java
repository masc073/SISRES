package beans;

import dominio.Feriado;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import servico.FeriadoServico;

@ManagedBean(name = "feriadoBean")
@ViewScoped
public class FeriadoBean extends Bean<Feriado> implements Serializable
{

    @EJB
    private FeriadoServico feriadoServico;

    @Override
    public void setServico()
    {

        super.servico = feriadoServico;
    }

}
