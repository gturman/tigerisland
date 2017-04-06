/**
 * Created by KJ on 4/6/2017.
 */

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\InvalidTotoroPlacement.feature")

public class testRunnerInvalidTotoroPlacement {
}
