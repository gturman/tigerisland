/**
 * Created by KJ on 4/6/2017.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\AllowPlacingOfTotoroSanctuary.feature")
public class testRunnerAllowPlacingOfTotoroSanctuary {

}
