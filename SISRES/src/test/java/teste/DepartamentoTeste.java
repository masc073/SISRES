package teste;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src\\test\\java\\feature\\departamento.feature", tags = "DepartamentoTeste", 
glue = "passos", monochrome = true, dryRun = false)
public class DepartamentoTeste {
    
   
}
