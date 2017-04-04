import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by christine on 4/4/2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\DisallowExpansion.feature")
public class testRunnerDisallowExpansion {
}

