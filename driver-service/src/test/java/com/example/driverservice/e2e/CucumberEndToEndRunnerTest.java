package com.example.driverservice.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report.html"},
        glue = "com.example.driverservice.e2e",
        features = {"src/test/java/resources/features"}
)
public class CucumberEndToEndRunnerTest {
}
