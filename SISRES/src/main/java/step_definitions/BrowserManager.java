package step_definitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Realiza o gerenciamento no browser para executação dos testes com o cucumber e Selenium.
 *
 * @author Natália Amâncio
 */

public class BrowserManager
{

    static WebDriver driver;
    
    /** Abre uma janela do Firefox.
     * @param url - Página que será testada.
     */
    public static void openFirefox(String url)
    {
        System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
        if (driver == null)
        {
            driver = new FirefoxDriver();
            driver.manage().window();
        }
        driver.get(url);
    }
}
