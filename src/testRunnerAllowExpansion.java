
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 * Created by Christine Chierico on 4/2/2017.
 */


@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\AllowExpansion.feature")

public class testRunnerAllowExpansion {
}
