import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by KJ on 4/6/2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\AllowTigerPlayground.feature")
public class testRunnerAllowTigerPlayground {
}
