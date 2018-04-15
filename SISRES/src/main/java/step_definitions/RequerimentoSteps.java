package step_definitions;

import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import java.util.List;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static step_definitions.BrowserManager.driver;
/**
 * Responsável por realizar o passo a passo da execução dos testes com o cucumber com relação ao Requerimento.
 * @author Natália
 */
public class RequerimentoSteps
{

    String requerimento;
    
    /** Construtor Padrão
     */
    public RequerimentoSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Requerimento) {
            DbUnitUtil.selecionaDataset(Dataset.Requerimento);
            DbUnitUtil.inserirDados();
            loga_usuario("ifpealuno@gmail.com", "aluno123", "/SISRES/comum/requerimento/requerimento.xhtml");
        }
    }
    
    public void loga_usuario(String usuario, String senha, String url)
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        if (driver.getCurrentUrl().equals("http://localhost:8080/SISRES/")) {

            driver.findElement(By.className("abcRioButtonContentWrapper")).click();
            
            driver.switchTo().window("");
            driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
            
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            if (!driver.getCurrentUrl().equals("http://localhost:8080/SISRES/template.xhtml")) {

                WebElement email_phone = driver.findElement(By.xpath("//input[@id='identifierId']"));
                email_phone.sendKeys(usuario);
                driver.findElement(By.id("identifierNext")).click();
                WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
                WebDriverWait wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.elementToBeClickable(password));
                password.sendKeys(senha);
                driver.findElement(By.id("passwordNext")).click();

                driver.switchTo().window("");
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

                driver.switchTo().window(driver.getWindowHandle());
            }
            driver.findElement(By.xpath("//a[@href=\'" + url + "\']")).click();
        }
    }
//
//    @Quando("^informar os seus dados da \"([^\"]*)\" , anexar \"([^\"]*)\" caso necessário e realizar e clicar no botao \"([^\"]*)\"$")
//    public void informar_os_seus_dados_da_anexar_caso_necessário_e_realizar_e_clicar_no_botao(String descricao, String nome_arquivo, String acao) throws Throwable
//    {
//        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//
//        driver.findElement(By.id("editar_atividade:descricao")).sendKeys(descricao);
//
//        if (!nome_arquivo.isEmpty()) {
//
//        }
//        if (acao.equals("Aprovar")) {
//            driver.findElement(By.id("editar_atividade:button_aprovar")).click();
//        }
//        else {
//            driver.findElement(By.id("editar_atividade:button_reprovar")).click();
//        }
//
//    }

    @Dado("^a tela inicial de requerimento aberta$")
    public void a_tela_inicial_de_requerimento_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/comum/requerimento/requerimento.xhtml");
    }

    @Quando("^o aluno informar a matricula \"([^\"]*)\" o processo \"([^\"]*)\" e salvar$")
    public void o_aluno_informar_a_matricula_o_processo_e_salvar(String matricula, String processo) throws Throwable
    {
//        BrowserManager.driver.findElement(By.id("requerimento:value_matricula")).sendKeys(matricula);

        WebElement div_responsavel = BrowserManager.driver.findElement(By.id("requerimento:processo"));
        WebElement exibir_lista = div_responsavel.findElement(By.tagName("span"));
        exibir_lista.click();

        List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

        for (WebElement option : options) {
            if (option.getText().equals(processo)) {
                option.click();
            }
        }
        BrowserManager.driver.findElement(By.id("requerimento:button_salvar")).click();
    }

    @Entao("^deve ser exibida a mensagem sobre o requerimento \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem_sobre_o_requerimento(String mensagemEsperada) throws Throwable
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
       
        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);
    }

    @Quando("^o aluno selecionar o \"([^\"]*)\" que deseja visualizar$")
    public void o_aluno_selecionar_o_que_deseja_visualizar(String requerimento) throws Throwable
    {
        int count = 0;
        String id;

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement table = driver.findElement(By.id("requerimento:table_requerimento_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));

            id = "requerimento:table_requerimento:" + count + ":consultar";

            for (WebElement column : columns) {
                if (column.getText().equals(requerimento)) {
                    row.findElement(By.id(id)).click();
                    break;
                }
            }
            count++;
        }
    }

    @Entao("^deve ser redirecionado para outra tela com as informacoes do requerimento$")
    public void deve_ser_redirecionado_para_outra_tela_com_as_informacoes_do_requerimento() throws Throwable
    {

    }

    @Quando("^o aluno selecionar o \"([^\"]*)\" que deseja encerrar$")
    public void o_aluno_selecionar_o_que_deseja_encerrar(String requerimento) throws Throwable
    {
        int contador = 0;
        String id;
        boolean removeu = false;

        WebElement table = driver.findElement(By.id("requerimento:table_requerimento_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));

            for (WebElement column : columns) {
                if (column.getText().equals(requerimento)) {
                    id = "requerimento:table_requerimento:" + contador + ":delete";
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
            ++contador;
        }
    }

}
