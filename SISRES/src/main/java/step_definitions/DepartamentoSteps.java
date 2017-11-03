package step_definitions;

import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Quando;
import dominio.Responsavel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import servico.ResponsavelServico;

public class DepartamentoSteps
{

    private List<List<String>> campos;
    private List<Responsavel> responsaveis = new ArrayList<Responsavel>();
    private ResponsavelServico responsavelServico;
    
    public DepartamentoSteps()
    {
       
    }
 

    @Dado("^os responsaveis cadastrados no sistema$")
    public void os_responsaveis_cadastrados_no_sistema(DataTable responsaveis) throws Throwable
    {
        DbUnitUtil.inserirDados();
        Responsavel responsavel;
        this.campos = responsaveis.raw();

        for (List<String> campos_responsavel : campos)
        {
            if (!campos_responsavel.get(0).equals("nome") && !campos_responsavel.get(1).equals("senha"))
            {
                responsavel = new Responsavel(campos_responsavel.get(0), campos_responsavel.get(1));
                this.responsaveis.add(responsavel);
            }
        }
        
        if(this.responsaveis.size() == 5)
        {
            for(Responsavel r : this.responsaveis)
            {
                responsavelServico.salvar(r);
            }
        }
    }

    @Quando("^o administrador informar o nome \"([^\"]*)\" a senha \"([^\"]*)\" e o responsavel \"([^\"]*)\"$")
    public void o_administrador_informar_o_nome_a_senha_e_o_responsavel(String nome, String senha, String responsavel) throws Throwable
    {

    }
}
