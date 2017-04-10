/**
 * Created by christine on 3/24/2017.
 */

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, features = "test\\acceptanceTests\\InvalidTilePlacement.feature")

public class testRunnerInvalidTilePlacement {

}