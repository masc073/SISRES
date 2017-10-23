package step_definitions;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.openqa.selenium.By;

public class ResponsavelSteps {

    @Dado("^o responsavel com nome \"(.*?)\" ainda nao cadastrado$")
    public void o_reponsavel_ainda_nao_cadastrado(String nome) throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/Responsavel/cadastroResponsavel.xhtml");
    }

    @Quando("^o usuario informar o \"(.*?)\" e a senha \"(.*?)\" do responsavel$")
    public void o_usuario_informar_o_nome_e_a_senha_do_responsavel(String nome, String senha) throws Throwable 
    {
        BrowserManager.driver.findElement(By.id("responsavel:value_nome_input")).sendKeys("Natália Amâncio");        
        BrowserManager.driver.findElement(By.id("responsavel:value_senha")).sendKeys("ABC1234");
    }

    @E("^confirmar a senha \"(.*?)\"$")
    public void confirmar_a_senha(String senha) throws Throwable 
    {
        BrowserManager.driver.findElement(By.id("responsavel:value_confirmacao")).sendKeys("ABC1234");
    }

    @Entao("^deve ser exibida a mensagem \"(.*?)\"$")
    public void deve_ser_exibida_a_mensagem(String mensagem) throws Throwable 
    {
        BrowserManager.driver.findElement(By.id("responsavel:button_salvar")).click();
        
        // Validar a mensagem que deve ser exibida.
    }

    @E("^o registro deve ser inserido no banco de dados$")
    public void o_registro_deve_ser_inserido_no_banco_de_dados() throws Throwable {

    }

    @Dado("^o responsavel ja cadastrado no sistema$")
    public void o_responsavel_ja_cadastrado_no_sistema() throws Throwable {

    }

    @Quando("^o usuario selecionadar o <responsavel> que deseja alterar$")
    public void o_usuario_selecionadar_o_responsavel_que_deseja_alterar() throws Throwable {

    }

    @Quando("^alterar o seu \"([^\"]*)\"$")
    public void alterar_o_seu(String arg1) throws Throwable {

    }

    @Entao("^o registro deve ser alterado no banco de dados$")
    public void o_registro_deve_ser_alterado_no_banco_de_dados() throws Throwable {

    }

    @Quando("^o usuario selecionar o <responsavel> que deseja alterar$")
    public void o_usuario_selecionar_o_responsavel_que_deseja_alterar() throws Throwable {
    }

    @Quando("^informar a sua \"([^\"]*)\" atual$")
    public void informar_a_sua_atual(String arg1) throws Throwable {

    }

    @Quando("^informar a sua nova \"([^\"]*)\" e confirmá-la$")
    public void informar_a_sua_nova_e_confirmá_la(String arg1) throws Throwable {

    }

    @Quando("^o usuario selecionar o <responsavel> que deseja remover$")
    public void o_usuario_selecionar_o_responsavel_que_deseja_remover() throws Throwable {

    }

    @Entao("^o registro deve ser removido do banco de dados$")
    public void o_registro_deve_ser_removido_do_banco_de_dados() throws Throwable {

    }

}
