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

    WebElement input_nome, button_check, input_duracao, input_responsavel;

    @Dado("^a tela inicial cadastro de processos aberta$")
    public void a_tela_inicial_cadastro_de_processos_aberta() throws Throwable
    {
        BrowserManager.openFirefox("http://localhost:8080/SISRES/processo/processo.xhtml");
    }

    @Quando("^o administrador informar o nome \"([^\"]*)\" a duração em dias \"([^\"]*)\" o responsavel \"([^\"]*)\" e salvar$")
    public void o_administrador_informar_o_nome_a_duração_em_dias_o_responsavel_e_salvar(String nome, String duracao, String responsavel) throws Throwable
    {

        BrowserManager.driver.findElement(By.id("processo:value_nome")).sendKeys(nome);
        WebElement duracao_em_dias = BrowserManager.driver.findElement(By.id("processo:value_duracao_input_input"));

        duracao_em_dias.clear();
        duracao_em_dias.sendKeys(duracao);

        WebElement div_responsavel = BrowserManager.driver.findElement(By.id("processo:responsavel_processo"));
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
        BrowserManager.driver.findElement(By.id("processo:button_salvar")).click();
    }

    @Entao("^deve ser exibida a seguinte mensagem : \"([^\"]*)\"$")
    public void deve_ser_exibida_a_seguinte_mensagem(String mensagemEsperada) throws Throwable
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);
    }

    @Quando("^o administrador definir o fluxo do processo com as seguintes  atividades:$")
    public void o_administrador_definir_o_fluxo_do_processo_com_as_seguintes_atividades(DataTable atividades)
    {
        List<List<String>> atividades_processo;

        atividades_processo = atividades.raw();

        for (List<String> atividade_atual : atividades_processo)
        {

            BrowserManager.driver.findElement(By.id("form_atividades:value_nome_atividade")).sendKeys(atividade_atual.get(0));

            WebElement div_departamento = BrowserManager.driver.findElement(By.id("form_atividades:departamento"));
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

            BrowserManager.driver.findElement(By.id("form_atividades:button_adicionar")).click();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
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

    @Quando("^o administrador selecionar o registro \"([^\"]*)\" que deseja remover$")
    public void o_administrador_selecionar_o_registro_registro_que_deseja_remover(String registro) throws Throwable
    {
        int contador = 0;
        String id;
        boolean removeu = false;

        WebElement table = driver.findElement(By.id("processo:table_processo_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(registro))
                {
                    id = "processo:table_processo:" + contador + ":delete";
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
            ++contador;
        }
    }

    @Quando("^o administrador selecionar o processo \"([^\"]*)\" que deseja alterar$")
    public void o_administrador_selecionar_o_processo_que_deseja_alterar(String registro)
    {
        WebElement button_edit = null;
        boolean edit_duracao = false, edit_responsavel = false;

        WebElement table = driver.findElement(By.id("processo:table_processo_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(registro))
                {
                    button_edit = row.findElement(By.className("ui-row-editor-pencil"));
                    button_edit.click();

                    WebElement link_check = row.findElement(By.className("ui-row-editor-check"));
                    button_check = link_check.findElement(By.tagName("span"));

                    WebElement div_nome = column.findElement(By.className("ui-cell-editor-input"));
                    input_nome = div_nome.findElement(By.tagName("input"));
                    edit_duracao = true;

                } else if (edit_duracao == true)
                {
                    WebElement div_data = column.findElement(By.className("ui-cell-editor-input"));
                    input_duracao = div_data.findElement(By.tagName("input"));
                    edit_duracao = false;
                    edit_responsavel = true;
                } else if (edit_responsavel == true)
                {
                    WebElement div_data = column.findElement(By.className("ui-cell-editor-input"));
                    input_responsavel = div_data.findElement(By.tagName("div"));
                    edit_responsavel = false;
                    break;
                }
            }
        }
    }

    @Quando("^informar o \"([^\"]*)\" a duracao \"([^\"]*)\" e o \"([^\"]*)\" para atualização$")
    public void informar_o_a_duracao_e_o_para_atualização(String nome, String duracao, String responsavel) throws Throwable
    {
        input_nome.clear();
        input_nome.sendKeys(nome);

        input_duracao.clear();
        input_duracao.sendKeys(duracao);

        WebElement exibir_lista = input_responsavel.findElement(By.tagName("span"));
        exibir_lista.click();

        List<WebElement> options = driver.findElements(By.tagName("li"));

        for (WebElement option : options)
        {
            if (option.getText().equals(responsavel))
            {
                option.click();
            }
        }

        button_check.click();
    }

    @Quando("^o administrador selecionar o processo \"([^\"]*)\" que deve alterar$")
    public void o_administrador_selecionar_o_processo_que_deve_alterar(String registro) throws Throwable
    {
        int contador = 0;
        String id;
        boolean removeu = false;

        WebElement table = driver.findElement(By.id("processo:table_processo_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(registro))
                {
                    id = "processo:table_processo:" + contador + ":atividades_processo";
                    WebElement link_remove = row.findElement(By.id(id));
                    link_remove.click();
                    break;
                }
            }
            if (removeu == true)
            {
                break;
            }
            ++contador;
        }
         driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Quando("^alterar as seguintes atividades:$")
    public void alterar_as_seguintes_atividades(DataTable atividades) throws Throwable
    {
        boolean edit_nome = false, edit_responsavel = false;
        List<List<String>> atividades_processo;

        atividades_processo = atividades.raw();
        
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        
        WebElement table = driver.findElement(By.id("form_atividades:table_atividade_data"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (List<String> atividade_atual : atividades_processo)
        {
            for (WebElement row : rows)
            {
                List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

                for (WebElement column : columns)
                {
                    if (column.getText().equals(atividade_atual.get(0)))
                    {
                        edit_responsavel = true;
                        row.findElement(By.className("ui-row-editor-pencil")).click();

                        WebElement link_check = row.findElement(By.className("ui-row-editor-check"));
                        button_check = link_check.findElement(By.tagName("span"));

                        WebElement div_nome = column.findElement(By.className("ui-cell-editor-input"));
                        input_nome = div_nome.findElement(By.tagName("input"));
                        input_nome.clear();
                        input_nome.sendKeys(atividade_atual.get(1));

                    } else if (edit_responsavel == true)
                    {
                        WebElement exibir_lista = column.findElement(By.tagName("span"));
                        exibir_lista.click();

                        List<WebElement> options = BrowserManager.driver.findElements(By.tagName("li"));

                        for (WebElement option : options)
                        {
                            if (option.getText().equals(atividade_atual.get(2)))
                            {
                                option.click();
                            }
                        }
                        button_check.click();
                        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

                        edit_responsavel = false;
                        break;
                    }
                }
            }
        }
          driver.findElement(By.id("j_idt40:button_salvar")).click();
    }
}
