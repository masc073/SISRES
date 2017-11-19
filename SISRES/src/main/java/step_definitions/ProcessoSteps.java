/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package step_definitions;

import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import dominio.Atividade;
import java.util.List;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static step_definitions.BrowserManager.driver;

public class ProcessoSteps
{

    public ProcessoSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Processo)
        {
            DbUnitUtil.selecionaDataset(Dataset.Processo);
            DbUnitUtil.inserirDados();
        }
    }

    @Dado("^a tela inicial cadastro de processos aberta$")
    public void a_tela_inicial_cadastro_de_processos_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/processo/processo.xhtml");
    }

    @Quando("^o administrador informar o nome \"([^\"]*)\" a duração em dias \"([^\"]*)\" e o responsavel \"([^\"]*)\"$")
    public void o_administrador_informar_o_nome_a_duração_em_dias_e_o_responsavel(String nome, String duracao, String responsavel) throws Throwable
    {

        BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:value_nome")).sendKeys(nome);
        WebElement duracao_em_dias = BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:value_duracao_input_input"));

        duracao_em_dias.clear();
        duracao_em_dias.sendKeys(duracao);

        WebElement div_responsavel = BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:responsavel_processo"));
        WebElement exibir_lista = div_responsavel.findElement(By.tagName("span"));
        exibir_lista.click();

        List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

//        for (WebElement option : options)
//        {
//            if (option.getText().equals(responsavel))
//            {
//                option.click();
//            }
//        }
//        BrowserManager.driver.findElement(By.id("form:tab_view:button_salvar_processo")).click();
    }

    @Quando("^ir para a aba confirmação e salvar\\.$")
    public void ir_para_a_aba_confirmação_e_salvar() throws Throwable
    {
        List<WebElement> abas = BrowserManager.driver.findElements(By.tagName("a"));

        for (WebElement aba : abas)
        {
            if (aba.getText().equals("Confirmação"))
            {
                aba.click();
            }
        }

        BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:salvar")).click();
    }

    @Entao("^deve ser exibida a seguinte mensagem : \"([^\"]*)\"$")
    public void deve_ser_exibida_a_seguinte_mensagem(String mensagemEsperada) throws Throwable
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);
    }

    @Quando("^ir para aba de atividades e adicionar as seguintes atividades:$")
    public void ir_para_aba_de_atividades_e_adicionar_as_seguintes_atividades(DataTable atividades)
    {
        List<WebElement> abas = BrowserManager.driver.findElements(By.tagName("a"));

        for (WebElement aba : abas)
        {
            if (aba.getText().equals("Adicionar Atividades"))
            {
                aba.click();
            }
        }

        List<List<String>> atividades_processo;

        atividades_processo = atividades.raw();

        for (List<String> atividade_atual : atividades_processo)
        {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:form_atividades:value_nome_atividade")).sendKeys(atividade_atual.get(0));

            WebElement div_departamento = BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:form_atividades:departamento"));
            WebElement exibir_lista = div_departamento.findElement(By.tagName("span"));
            exibir_lista.click();

            List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

            for (WebElement option : options)
            {
                if (option.getText().equals(atividade_atual.get(1)))
                {
                    option.click();
                }
            }

            BrowserManager.driver.findElement(By.id("j_idt20:j_idt21:form_atividades:button_salvar")).click();

        }

    }

    @Dado("^a tela de listagem de processos aberta$")
    public void a_tela_de_listagem_de_processos_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/processo/listaprocessos.xhtml");
    }

    @Quando("^o administrador selecionar o registro \"([^\"]*)\" que deve alterar$")
    public void o_administrador_selecionar_o_registro_que_deve_alterar(String registro) throws Throwable
    {
        WebElement table = driver.findElement(By.id("processo:table_processo_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("td"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(registro))
                {
                    row.findElement(By.className("ui-row-editor-pencil")).click();
                    row.findElement(By.className("ui-row-editor-check")).click();
                }
            }
        }
    }

    @Quando("^ir para aba de atividades e alterar as seguintes atividades:$")
    public void ir_para_aba_de_atividades_e_alterar_as_seguintes_atividades(DataTable atividades) throws Throwable
    {
        List<WebElement> abas = BrowserManager.driver.findElements(By.tagName("a"));

        for (WebElement aba : abas)
        {
            if (aba.getText().equals("Adicionar Atividades"))
            {
                aba.click();
            }
        }
        
        List<List<String>> atividades_processo;

        atividades_processo = atividades.raw();

        for (List<String> atividade_atual : atividades_processo)
        {
            
        }
    }
}
