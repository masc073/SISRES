package step_definitions;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import java.util.List;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static step_definitions.BrowserManager.driver;
/**
 * Responsável por realizar o passo a passo da execução dos testes com o cucumber com relação ao Responsável.
 * @author Natália
 */
public class ResponsavelSteps
{
    /** Construtor Padrão
     */
    public ResponsavelSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Responsavel) {
            DbUnitUtil.selecionaDataset(Dataset.Responsavel);
            DbUnitUtil.inserirDados();
        }
    }

    WebElement input_nome, button_check;
    
   
    @Dado("^a tela inicial do cadastro aberta$")
    public void a_tela_inicial_do_responsavel_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/publico/cadastro.xhtml");
    }

    @Quando("^o usuario informar o \"([^\"]*)\" , \"([^\"]*)\" , \"([^\"]*)\" e \"([^\"]*)\"$")
    public void o_usuario_informar_o_e(String nome, String email, String titulo, String unidade_organizacional) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("form:primeiroNome")).sendKeys(nome);
        BrowserManager.driver.findElement(By.id("form:email")).sendKeys(email);

        WebElement div_titulo = BrowserManager.driver.findElement(By.id("form:titulo"));
        WebElement exibir_lista = div_titulo.findElement(By.tagName("span"));
        exibir_lista.click();

        List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

        for (WebElement option : options) {
            if (option.getText().equals(titulo)) {
                option.click();
            }
        }

        if (!unidade_organizacional.isEmpty()) {

            WebElement div_unidadeOrganizacional = BrowserManager.driver.findElement(By.id("form:unidadeOrganizacional"));
            exibir_lista = div_unidadeOrganizacional.findElement(By.tagName("span"));
            exibir_lista.click();

            options = BrowserManager.driver.findElements(By.tagName("li"));

            for (WebElement option : options) {
                if (option.getText().equals(unidade_organizacional)) {
                    option.click();
                }
            }

        }

        BrowserManager.driver.findElement(By.id("form:button_salvar")).click();
    }

    @Quando("^o usuario informar o \"([^\"]*)\" , \"([^\"]*)\"  se e \"([^\"]*)\" \\. Caso seja tambem informar se e \"([^\"]*)\" e o \"([^\"]*)\"$")
    public void o_usuario_informar_o_se_e_Caso_seja_tambem_informar_se_e_e_o(String nome, String email, String servidor, String chefe, String unidade_organizacional) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("form:primeiroNome")).sendKeys(nome);
        BrowserManager.driver.findElement(By.id("form:email")).sendKeys(email);

        List<WebElement> radioButtons = BrowserManager.driver.findElements(By.className(("ui-radiobutton")));

//        if (servidor.equals("true")) {
//            radioButtons.get(0).click();
        if (!unidade_organizacional.equals("")) {
            List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

            for (WebElement option : options) {
                if (option.getText().equals(unidade_organizacional)) {
                    option.click();
                }
            }
        }
    }

    @Quando("^o administrador informar o \"([^\"]*)\" e a senha \"([^\"]*)\" do responsavel$")
    public void o_administrador_informar_o_e_a_senha_do_responsavel(String nome, String senha) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("responsavel:value_nome")).sendKeys(nome);
        BrowserManager.driver.findElement(By.id("responsavel:value_senha")).sendKeys(senha);
    }

    @Quando("^o administrador selecionar o \"([^\"]*)\" que deseja alterar$")
    public void o_administrador_selecionar_o_responsavel_que_deseja_alterar(String responsavel) throws Throwable
    {
        WebElement button_edit = null;

        WebElement table = driver.findElement(By.id("responsavel:table_responsavel_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns) {
                if (column.getText().equals(responsavel)) {
                    button_edit = row.findElement(By.className("ui-row-editor-pencil"));
                    button_edit.click();

                    WebElement link_check = row.findElement(By.className("ui-row-editor-check"));
                    button_check = link_check.findElement(By.tagName("span"));

                    WebElement div_nome = column.findElement(By.className("ui-cell-editor-input"));
                    input_nome = div_nome.findElement(By.tagName("input"));

                }
            }
        }
    }

    @Quando("^o administrador selecionar o \"([^\"]*)\" que deseja remover$")
    public void o_administrador_selecionar_o_responsavel_que_deseja_remover(String responsavel) throws Throwable
    {
        int contador = 0;
        String id, text_column;
        boolean removeu = false;

        WebElement table = driver.findElement(By.id("responsavel:table_responsavel"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns) {
                text_column = column.getText();
                if (text_column.equals(responsavel)) {
                    id = "responsavel:table_responsavel:" + contador + ":remove";
                    WebElement link_remove = row.findElement(By.id(id));
                    link_remove.click();
                    List<WebElement> buttons = driver.findElements(By.tagName("button"));

                    for (WebElement button : buttons) {
                        if (button.getText().equals("Sim")) {
                            button.click();
                            removeu = true;
                            break;
                        }
                    }
                    break;
                }
            }
            if (removeu == true) {
                break;
            }
            if (!columns.isEmpty()) {
                ++contador;
            }
        }
    }

    @Quando("^confirmar a senha \"([^\"]*)\"$")
    public void confirmar_a_senha(String senha) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("form:value_confirmacao")).sendKeys(senha);
        BrowserManager.driver.findElement(By.id("form:button_salvar")).click();
    }

    @Entao("^deve ser exibida a mensagem \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem(String mensagemEsperada) throws Throwable
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);
    }

    @Quando("^alterar o seu \"([^\"]*)\"$")
    public void alterar_o_seu(String nome) throws Throwable
    {
        input_nome.clear();
        input_nome.sendKeys(nome);

        button_check.click();
    }
}
