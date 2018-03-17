/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package step_definitions;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import junit.framework.Assert;
import org.openqa.selenium.WebElement;
import static step_definitions.BrowserManager.driver;

/**
 *
 * @author Natália
 */
public class FeriadoSteps
{

    public FeriadoSteps()
    {
        if (DbUnitUtil.ultimo_executado != Dataset.Vazio)
        {
            DbUnitUtil.selecionaDataset(Dataset.Vazio);
            DbUnitUtil.inserirDados();
        }
    }

    WebElement input_nome, input_data, button_check;
    
    @Dado("^tela inicial de feriados aberta$")
    public void a_tela_de_cadastro_de_feriados_aberta()
    {
        
        BrowserManager.openFirefox("http://localhost:8080/SISRES/administrador/feriado/feriado.xhtml");
    }

    @Quando("^o administrador informar a \"([^\"]*)\" e o \"([^\"]*)\" do feriado$")
    public void o_administrador_informar_a_e_o_do_feriado(String data, String nome) throws Throwable
    {
        String array[] = new String[3];

        BrowserManager.driver.findElement(By.id("feriado:value_nome")).sendKeys(nome);

        array = data.split("/");

        SelectDateFromDatePicker(array[2], array[1], array[0]);

        BrowserManager.driver.findElement(By.id("feriado:button_salvar")).click();
    }

    @Entao("^deve ser exibida a mensagem para o feriado \"([^\"]*)\"$")
    public void deve_ser_exibida_a_mensagem(String mensagemEsperada)
    {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement message_container = driver.findElement(By.className("ui-growl-title"));

        Assert.assertEquals(message_container.getText(), mensagemEsperada);

    }

    @E("^o administrador deve selecionar o registro \"([^\"]*)\" que deseja alterar$")
    public void o_administrador_deve_selecionar_o_registro_que_deseja_alterar(String nome)
    {

        WebElement button_edit = null;
        boolean edit_data = false;

        WebElement table = driver.findElement(By.id("feriado:table_feriado_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(nome))
                {
                    edit_data = true;
                    button_edit = row.findElement(By.className("ui-row-editor-pencil"));
                    button_edit.click();

                    WebElement link_check = row.findElement(By.className("ui-row-editor-check"));
                    button_check = link_check.findElement(By.tagName("span"));

                    WebElement div_nome = column.findElement(By.className("ui-cell-editor-input"));
                    input_nome = div_nome.findElement(By.tagName("input"));

                } else if (edit_data == true)
                {

                    WebElement div_data = column.findElement(By.className("ui-cell-editor-input"));
                    input_data = div_data.findElement(By.tagName("input"));
                    edit_data = false;
                    break;
                }
            }
        }

    }

    @Quando("^o administrador informar a \"([^\"]*)\" e o \"([^\"]*)\" do feriado para atualização$")
    public void o_administrador_informar_a_data_e_o_nome_do_feriado(String data, String feriado)
    {
        input_nome.clear();
        input_nome.sendKeys(feriado);

        input_data.clear();
        input_data.sendKeys(data);
        button_check.click();
    }

    @Quando("^o administrador deve selecionar o registro \"([^\"]*)\" que deseja remover$")
    public void o_administrador_deve_selecionar_o_resgistro_que_deseja_remover(String registro)
    {
        int contador = 0;
        String id, text_column;
        boolean removeu = false;

        WebElement table = driver.findElement(By.id("feriado:table_feriado_data"));

        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows)
        {
            List<WebElement> columns = row.findElements(By.className("ui-editable-column"));

            for (WebElement column : columns)
            {
                if (column.getText().equals(registro))
                {
                    id = "feriado:table_feriado:" + contador + ":delete";
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

    public void SelectDateFromDatePicker(String year, String month, String day)
    {
        int ano, ano_atual;
        String mes_atual, dia_atual;

        WebElement btnPrevious = driver.findElement(By.xpath("//a[@title=\"Anterior\"]"));
        WebElement btnNext = driver.findElement(By.xpath("//a[@title=\"Próximo\"]"));
        WebElement lblYear = driver.findElement(By.className("ui-datepicker-year"));
        WebElement lblMonth = driver.findElement(By.className("ui-datepicker-month"));

        switch (month)
        {
            case "01":
                month = "Janeiro";
                break;
            case "02":
                month = "Fevereiro";
                break;
            case "03":
                month = "Março";
                break;
            case "04":
                month = "Abril";
                break;
            case "05":
                month = "Maio";
                break;
            case "06":
                month = "Junho";
                break;
            case "07":
                month = "Julho";
                break;
            case "08":
                month = "Agosto";
                break;
            case "09":
                month = "Setembro";
                break;
            case "10":
                month = "Outubro";
                break;
            case "11":
                month = "Novembro";
                break;
            case "12":
                month = "Dezembro";
                break;
        }
        
        lblYear = driver.findElement(By.className("ui-datepicker-year"));
        String teste = lblYear.getText();
        
        while (!year.equals(lblYear.getText()))
        {
            ano = Integer.parseInt(year);
          
            ano_atual = Integer.parseInt(lblYear.getText());

            if (ano < ano_atual)
            {
                btnPrevious = driver.findElement(By.xpath("//a[@title=\"Anterior\"]"));
                btnPrevious.click();
            } else
            {
                btnNext = driver.findElement(By.xpath("//a[@title=\"Próximo\"]"));
                btnNext.click();
            }
             lblYear = driver.findElement(By.className("ui-datepicker-year"));
        }

        lblMonth = driver.findElement(By.className("ui-datepicker-month"));
        mes_atual = lblMonth.getText();

        while (!mes_atual.equals("Janeiro"))
        {
            btnPrevious = driver.findElement(By.xpath("//a[@title=\"Anterior\"]"));
            btnPrevious.click();
            lblMonth = driver.findElement(By.className("ui-datepicker-month"));
            btnPrevious = driver.findElement(By.xpath("//a[@title=\"Anterior\"]"));
            mes_atual = lblMonth.getText();
        }

        while (!month.equals(mes_atual))
        {
            btnNext = driver.findElement(By.xpath("//a[@title=\"Próximo\"]"));
            btnNext.click();
            lblMonth = driver.findElement(By.className("ui-datepicker-month"));
            mes_atual = lblMonth.getText();
        }

        WebElement dateWidget = driver.findElement(By.className("ui-datepicker-calendar"));
        List<WebElement> dias = dateWidget.findElements(By.tagName("td"));

        for (WebElement cell : dias)
        {
            dia_atual = cell.getText();

            if (dia_atual.equals(day))
            {
                cell.click();
                break;
            }
        }
    }
}
