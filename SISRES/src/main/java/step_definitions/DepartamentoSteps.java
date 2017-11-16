package step_definitions;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import java.util.List;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static step_definitions.BrowserManager.driver;

public class DepartamentoSteps
{

    WebElement input_nome, input_sigla, input_responsavel, button_check;

    public DepartamentoSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Departamento)
        {
            DbUnitUtil.selecionaDataset(Dataset.Departamento);
            DbUnitUtil.inserirDados();
        }
    }

    @Dado("^a tela inicial de departamentos aberta$")
    public void a_tela_inicial_de_departamentos_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/departamento/departamento.xhtml");
    }

    @Quando("^o administrador informar o nome \"([^\"]*)\" a sigla \"([^\"]*)\" e o responsavel \"([^\"]*)\"$")
    public void o_administrador_informar_o_nome_a_sigla_e_o_responsavel(String nome, String sigla, String responsavel) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("departamento:value_nome")).sendKeys(nome);
        BrowserManager.driver.findElement(By.id("departamento:value_sigla")).sendKeys(sigla);

        WebElement combo_box = BrowserManager.driver.findElement(By.id("departamento:responsavel_input"));

        WebElement div_responsavel = BrowserManager.driver.findElement(By.id("departamento:responsavel"));
        WebElement exibir_lista = div_responsavel.findElement(By.tagName("span"));
        exibir_lista.click();

        List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

        for (WebElement option : options)
        {
            if (option.getText().equals(responsavel))
            {
                option.click();
            }
        }

        WebElement button_salvar = BrowserManager.driver.findElement(By.id("departamento:button_salvar"));
        button_salvar.click();
    }

    @Quando("^o administrador selecionar o registro \"([^\"]*)\" que deseja alterar$")
    public void o_administrador_selecionar_o_registro_que_deseja_alterar(String registro) throws Throwable
    {
        WebElement button_edit = null;
        boolean edit_sigla = false;

        WebElement table = driver.findElement(By.id("departamento:tabela_departamentos_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(registro))
                {
                    edit_sigla = true;
                    button_edit = row.findElement(By.className("ui-row-editor-pencil"));
                    button_edit.click();

                    WebElement link_check = row.findElement(By.className("ui-row-editor-check"));
                    button_check = link_check.findElement(By.tagName("span"));

                    WebElement div_nome = column.findElement(By.className("ui-cell-editor-input"));
                    input_nome = div_nome.findElement(By.tagName("input"));

                } else if (edit_sigla == true)
                {
                    WebElement div_data = column.findElement(By.className("ui-cell-editor-input"));
                    input_sigla = div_data.findElement(By.tagName("input"));
                    edit_sigla = false;
                    break;
                }
            }
        }
    }

    @E("^o administrador informar o nome \"([^\"]*)\" a sigla \"([^\"]*)\" e o responsavel \"([^\"]*)\" para atualização$")
    public void o_administrador_informar_o_nome_a_sigla_e_o_responsavel_para_atualização(String nome, String sigla, String responsavel) throws Throwable
    {
        input_nome.clear();
        input_nome.sendKeys(nome);

        input_sigla.clear();
        input_sigla.sendKeys(sigla);

        List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

        for (WebElement option : options)
        {
            if (option.getText().equals(responsavel))
            {
                option.click();
            }
        }
        button_check.click();
    }

    @Quando("^o administrador deve selecionar o departamento \"([^\"]*)\" que deseja remover$")
    public void o_administrador_deve_selecionar_o_departamento_que_deseja_remover(String departamento) throws Throwable
    {
        int contador = 0;
        String id;
        boolean removeu = false;
        
        WebElement table = driver.findElement(By.id("departamento:tabela_departamentos_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(departamento))
                {
                    id = "departamento:tabela_departamentos:" + contador + ":delete";
                    WebElement link_remove = row.findElement(By.id(id));
                    link_remove.click();
                    List<WebElement> buttons = driver.findElements(By.tagName("button"));
                    
                    for(WebElement button : buttons)
                    {
                        if(button.getText().equals("Sim"))
                        {
                            button.click();
                            removeu = true;
                            break;
                        }
                    }
                    break;
                }
            }
            if(removeu == true)
                break;
            ++contador;
        }
    }
    
    @Entao("^deve ser exibida a mensagem para o departamento \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem(String mensagemEsperada)
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);

    }
}
