/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package step_definitions;

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
 *
 * @author natal
 */
public class Atendimento_requerimentoSteps
{

    public Atendimento_requerimentoSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Atendimento_requerimento) {
            DbUnitUtil.selecionaDataset(Dataset.Atendimento_requerimento);
            DbUnitUtil.inserirDados();
            loga_usuario("ifpeservidor@gmail.com", "servidor01", "/SISRES/comum/atividade/atividade.xhtml");
        }
    }

    public void loga_usuario(String usuario, String senha, String url)
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        if (driver.getCurrentUrl().equals("http://localhost:8080/SISRES/")) {
            
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

            driver.findElement(By.className("abcRioButtonContentWrapper")).click();

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

    @Dado("^a tela da fila de requerimentos aberta$")
    public void a_tela_da_fila_de_requerimentos_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/comum/atividade/atividade.xhtml");
    }

    @Quando("^o responsavel selecionar o requerimento \"([^\"]*)\" que deseja dar andamento$")
    public void o_responsavel_selecionar_o_requerimento_que_deseja_dar_andamento(String requerimento) throws Throwable
    {
        int linha = 0;
        WebElement table = driver.findElement(By.id("atividade:table_atividade_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {

            List<WebElement> columns = row.findElements(By.tagName("td"));

            for (WebElement column : columns) {
                if (column.getText().equals(requerimento)) {
                    row.findElement(By.id("atividade:table_atividade:" + linha + ":editar")).click();
                    break;
                }
            }
            ++linha;
        }
    }

    @Quando("^informar os seus dados da \"([^\"]*)\" , anexar  caso necessário e realizar e clicar no botao \"([^\"]*)\"$")
    public void informar_os_seus_dados_da_anexar_caso_necessário_e_realizar_e_clicar_no_botao(String descricao, String acao) throws Throwable
    {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        driver.findElement(By.id("editar_atividade:descricao")).sendKeys(descricao);

        if (acao.equals("Aprovar")) {
            driver.findElement(By.id("editar_atividade:button_aprovar")).click();
        }
        else {
            driver.findElement(By.id("editar_atividade:button_reprovar")).click();
        }
    }

    @Entao("^deve ser exibida a mensagem sobre o fluxo do requerimento \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem_sobre_o_fluxo_do_requerimento(String mensagemEsperada) throws Throwable
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);
    }

}
