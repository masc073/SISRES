package step_definitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserManager
{

    static WebDriver driver;

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
