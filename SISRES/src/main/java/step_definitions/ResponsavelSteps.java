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

public class ResponsavelSteps
{

    public ResponsavelSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Vazio)
        {
            DbUnitUtil.selecionaDataset(Dataset.Vazio);
            DbUnitUtil.inserirDados();
        }
    }

    WebElement input_nome, button_check;

    @Dado("^a tela inicial do responsavel aberta$")
    public void a_tela_inicial_do_responsavel_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/responsavel/responsavel.xhtml");
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

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(responsavel))
                {
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

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                text_column = column.getText();
                if (text_column.equals(responsavel))
                {
                    id = "responsavel:table_responsavel:" + contador + ":remove";
                    WebElement link_remove = row.findElement(By.id(id));
                    link_remove.click();
                    List<WebElement> buttons = driver.findElements(By.tagName("button"));

                    for (WebElement button : buttons)
                    {
                        if (button.getText().equals("Sim"))
                        {
                            button.click();
                            removeu = true;
                            break;
                        }
                    }
                    break;
                }
            }
            if (removeu == true)
            {
                break;
            }
            if (!columns.isEmpty())
            {
                ++contador;
            }
        }
    }

    @Quando("^confirmar a senha \"([^\"]*)\"$")
    public void confirmar_a_senha(String senha) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("responsavel:value_confirmacao")).sendKeys(senha);
        BrowserManager.driver.findElement(By.id("responsavel:button_salvar")).click();
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
