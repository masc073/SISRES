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
/**
 * Responsável por realizar o passo a passo da execução dos testes com o cucumber com relação a Unidade Organizacional.
 * @author Natália
 */
public class UnidadeOrganizacionalSteps
{

    WebElement input_nome, input_sigla, input_responsavel, button_check;
    
    /** Construtor Padrão
     */
    public UnidadeOrganizacionalSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Departamento)
        {
            DbUnitUtil.selecionaDataset(Dataset.Departamento);
            DbUnitUtil.inserirDados();
        }
    }

    @Dado("^a tela inicial de Unidades Organizacionais aberta$")
    public void a_tela_inicial_de_unidades_organizacionais_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/administrador/unidadeOrganizacional/unidadeOrganizacional.xhtml");
    }

    @Quando("^o administrador informar o nome \"([^\"]*)\" a sigla \"([^\"]*)\" e o responsavel \"([^\"]*)\"$")
    public void o_administrador_informar_o_nome_a_sigla_e_o_responsavel(String nome, String sigla, String responsavel) throws Throwable
    {
        BrowserManager.driver.findElement(By.id("unidadeOrganizacional:value_nome")).sendKeys(nome);
        BrowserManager.driver.findElement(By.id("unidadeOrganizacional:value_sigla")).sendKeys(sigla);

        WebElement combo_box = BrowserManager.driver.findElement(By.id("unidadeOrganizacional:responsavel_input"));

        WebElement div_responsavel = BrowserManager.driver.findElement(By.id("unidadeOrganizacional:responsavel"));
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

        WebElement button_salvar = BrowserManager.driver.findElement(By.id("unidadeOrganizacional:button_salvar"));
        button_salvar.click();
    }

    @Quando("^o administrador selecionar o registro \"([^\"]*)\" que deseja alterar$")
    public void o_administrador_selecionar_o_registro_que_deseja_alterar(String registro) throws Throwable
    {
        WebElement button_edit = null;
        boolean edit_sigla = false;

        WebElement table = driver.findElement(By.id("unidadeOrganizacional:tabela_unidadeOrganizacionals_data"));

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

    @Quando("^o administrador deve selecionar a Unidade Organizacional \"([^\"]*)\" que deseja remover$")
    public void o_administrador_deve_selecionar_o_departamento_que_deseja_remover(String unidade_organizacional) throws Throwable
    {
        int contador = 0;
        String id;
        boolean removeu = false;
        
        WebElement table = driver.findElement(By.id("unidadeOrganizacional:tabela_unidadeOrganizacionals_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(unidade_organizacional))
                {
                    id = "unidadeOrganizacional:tabela_unidadeOrganizacionals:" + contador + ":delete";
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
    
    @Entao("^deve ser exibida a mensagem para a Unidade Organizacional \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem(String mensagemEsperada)
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);

    }
}
