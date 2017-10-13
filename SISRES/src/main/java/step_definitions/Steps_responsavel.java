package step_definitions;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;

public class Steps_responsavel {
    
//    WebDriver driver;
    
//    @Dado("^o responsavel com nome \"([^\"]*)\" ainda nao cadastrado$")
//    @Dado("^o responsavel com nome Carlos Macedo ainda nao cadastrado$")
//    public void o_reponsavel_ainda_nao_cadastrado_1(String nome)
//    {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://localhost:8080/SISRES/Responsavel/cadastroResponsavel.xhtml");
//    }
//    
//    @Dado("^o responsavel com nome \"([^\"]*)\" ainda nao cadastrado$")
//    public void o_reponsavel_ainda_nao_cadastrado_2(String nome)
//    {
//        driver = new ChromeDriver();
//       driver.manage().window().maximize();
//        driver.get("http://localhost:8080/SISRES/Responsavel/cadastroResponsavel.xhtml");
//    }
//     
//    @Dado("^o responsavel com nome \"(.*?)\" ainda nao cadastrado$")
//    public void o_reponsavel_ainda_nao_cadastrado_3(String nome)
//    {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://localhost:8080/SISRES/Responsavel/cadastroResponsavel.xhtml");
//    }
    
//    @Dado("^o responsavel com nome Carlos Macedo ainda nao cadastrado$")
//    public void o_reponsavel_ainda_nao_cadastrado(String nome)
//    {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://localhost:8080/SISRES/Responsavel/cadastroResponsavel.xhtml");
//    }
    
    @Quando("^o usuario informar o \"([^\"]*)\" e a senha \"([^\"]*)\" do responsavel$")
    public void o_usuario_informar_o_nome_e_a_senha_do_responsavel(String nome, String senha)
    {
    
    }
    
    @E("^confirmar a senha\"([^\"]*)\"$") 
    public void confirmar_a_senha(String senha)
    {
        
    }
    
    @Entao("^deve ser exibida a mensagem \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem(String mensagem)
    {
    
    }
    
    @E("^o registro deve ser inserido no banco de dados$")
    public void o_registro_deve_ser_inserido_no_banco_de_dados()
    {
    
    }
    
}
