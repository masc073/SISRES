package teste;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src\\test\\java\\feature\\responsavel.feature", tags = "@ResponsavelTeste", 
glue = "passos", monochrome = true, dryRun = false)
public class ResponsavelTeste {
    
   
}
