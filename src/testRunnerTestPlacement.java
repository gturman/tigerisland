/**
 * Created by Kevin on 3/17/2017.
 */

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\tilePlacement.feature")
public class testRunnerTestPlacement {

}
