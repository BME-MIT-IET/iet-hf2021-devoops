package test.java.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.java.com.complexible.common.csv.CSV2RDF;
import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class TestCSV {
    static String run() {
        String[] args = {"examples/cars/template.ttl", "examples/cars/cars.csv", "src/test/resources/testDump/cars.ttl"};
        try {
            CSV2RDF.main(args);
            return "success";
        } catch (Exception exception) {
            return "failure";
        }
    }

}


public class StepDefinitions {
    String givenString;
    String result;

    @Given("I have a default csv")
    public void i_have_a_default_csv() {
        // Write code here that turns the phrase above into concrete actions
        givenString = "success";
    }


    @When("I start converting")
    public void i_start_converting() {
        // Write code here that turns the phrase above into concrete actions
        result = TestCSV.run();
    }


    @Then("I get converted file")
    public void i_get_converted_file() {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        assertTrue(Files.exists(path));
    }

    @When("I give {int} arguments")
    public void iGiveArguments(int arg0) {

    }

    @Then("I receive an error")
    public void iReceiveAnError() {
    }

    @Then("I receive no errors")
    public void iReceiveNoErrors() {
        assertEquals(givenString, result);
    }

    @When("I run the program")
    public void iRunTheProgram() {
        result = TestCSV.run();
    }
}
