import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
/**
 * Created by christine on 3/15/2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions (monochrome = true, features = "test\\test.feature")
public class testRunner {

}