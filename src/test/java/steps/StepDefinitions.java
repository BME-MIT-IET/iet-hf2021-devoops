package test.java.steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.java.com.complexible.common.csv.CSV2RDF;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class TestCSV {
    static void run(String[] args) throws Exception {
        CSV2RDF.main(args);
    }
}


public class StepDefinitions {
    String[] correctArgs = {"examples/cars/template.ttl", "examples/cars/cars.csv", "src/test/resources/testDump/cars.ttl"};
    String[] notEnoughArgs = {"examples/cars/template.ttl"};
    String[] tooManyArgs = {"cars", "cars", "cars", "cars"};
    String givenString;
    String result;
    String success = "success";
    String failure = "failure";

    @Given("I have a default csv")
    public void i_have_a_default_csv() {
        // Write code here that turns the phrase above into concrete actions
        givenString = "success";
    }


    @When("I start converting")
    public void i_start_converting() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TestCSV.run(correctArgs);
    }


    @Then("I get converted file")
    public void i_get_converted_file() {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        assertTrue(Files.exists(path));
    }

    @When("I give {int} arguments")
    public void i_give_arguments(int numberOfArguments) throws Exception {
        if (numberOfArguments < 3) {
            TestCSV.run(notEnoughArgs);
        } else if (numberOfArguments > 3) {
            TestCSV.run(tooManyArgs);
        }
    }

    @Then("I receive no errors")
    public void i_receive_no_errors() {
        assertEquals(givenString, result);
    }

    @When("I run the program")
    public void i_run_the_program() {
        try {
            TestCSV.run(correctArgs);
            result = success;
        } catch (Exception e) {
            result = failure;
        }
    }

    @Then("I do not get new file")
    public void i_do_not_get_new_file() {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        assertFalse(Files.exists(path));
    }

    @After
    public void doSomethingAfter(Scenario scenario) {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
