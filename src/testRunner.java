import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

/**
 * Created by christine on 3/15/2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions (monochrome = true, features = "test\\acceptanceTests\\test.feature")
public class testRunner {

}