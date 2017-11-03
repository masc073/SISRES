
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( features = "src\\main\\java\\features\\Departamento.feature", 
        glue = "classpath:step_definitions")
public class RunTests {

}
