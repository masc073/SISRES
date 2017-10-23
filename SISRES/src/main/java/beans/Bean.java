package beans;

import dominio.EntidadeNegocio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import servico.Servico;

public class Bean<T extends EntidadeNegocio> implements Serializable
{
    private Servico<T> servico;

    protected List<T> entidades = new ArrayList<>();

    protected T entidade;
    

    public Servico<T> getServico()
    {
        return servico;
    }

    public void setServico(Servico<T> servico)
    {
        this.servico = servico;
    }

    public List<T> getEntidades()
    {
        return entidades;
    }

    public void setEntidades(List<T> entidades)
    {
        this.entidades = entidades;
    }

    public T getEntidade()
    {
        return entidade;
    }

    public void setEntidade(T entidade)
    {
        this.entidade = entidade;
    }
  
}
