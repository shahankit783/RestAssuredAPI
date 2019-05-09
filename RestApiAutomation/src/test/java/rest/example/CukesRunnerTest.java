package rest.example;

import cucumber.api.CucumberOptions;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import org.junit.runner.RunWith;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        retryCount = 0,
        detailedReport = true,
        overviewReport = true,
        outputFolder = "target")
@CucumberOptions(plugin = { "html:src/test/results/cucumber-html-report",
        "json:src/test/results/cucumber.json",
        "usage:src/test/results/cucumber-usage.json",
        "junit:src/test/results/cucumber-results.xml"
},
        features = {"./src/test/resources"},
        glue = { "rest/example" },
        tags = {"@reqres"})


public class CukesRunnerTest{
}