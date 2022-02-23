package runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/Apitesting.feature"},
glue= {"Steps"})

public class TestRunner {

}
