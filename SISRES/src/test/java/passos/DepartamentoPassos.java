package passos;

import cucumber.api.java.es.Dado;
import cucumber.api.java.pt.Entao;
import dominio.Departamento;
import dominio.Responsavel;
import junit.framework.Assert;
/**
 *
 * @author Natália
 */
public class DepartamentoPassos {
    
//    private Departamento _departamento;
//    private DepartamentoBean _departamentoBean;
//    
//    public DepartamentoPassos()
//    {
//        _departamentoBean = new DepartamentoBean();
//        _departamentoBean.setDepartamento(_departamento);
//    }
//    
//    @Dado("^que o departamento tem nome de \"(.*?)\"$ com sigla \"(.*?)\"$ e o responsavel$")
//    public void criaObjParaTeste(String nome, String sigla, Responsavel responsavel)
//    {
//        _departamento = new Departamento(nome, sigla, responsavel);
//    }
//    
////    
////    @Dado("^que o departamento tem nome de \"(.*?)\"$ com sigla \"(.*?)\"$ e o responsavel:$")
////    public void criaObjParaTeste(String nome, String sigla)
////    {
////        _departamento = new Departamento();
////        _departamento.setNome(nome);
////        _departamento.setSigla(sigla);
////    }
//    
//    @Dado("^a insercao do departamento$")
//    public void a_insercao_do_departamento()
//    {
//        _departamentoBean.salvar();
//    }
//    
//    @Entao("o departamento deve existir no banco de dados$")
//    public void o_departamento_deve_existir_no_banco_de_dados()
//    {
//       Assert.assertEquals(true, _departamentoBean.verifica_existencia());
//    }
}
