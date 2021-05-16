package test.java.steps;

import main.java.com.complexible.common.csv.CSV2RDF;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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
    String givenString;
    String result;
    String success = "success";
    String failure = "failure";

    String pathToCSV;
    String[] conversionArguments;

    @Given("I have a default csv")
    public void i_have_a_default_csv() {
        givenString = "success";
        pathToCSV = "examples/cars/cars.csv";
        conversionArguments = new String[]{"examples/cars/template.ttl", pathToCSV, "src/test/resources/testDump/cars.ttl"};
    }

    @Given("I have an empty csv")
    public void i_have_an_empty_csv() {
        pathToCSV = "examples/nodata/nodata.csv";
        conversionArguments = new String[]{"examples/cars/template.ttl", pathToCSV, "src/test/resources/testDump/cars.ttl"};
    }

    @Given("I have an incorrect csv")
    public void i_have_an_incorrect_csv() {
        pathToCSV = "examples/wrongdata/wrongdata.csv";
        conversionArguments = new String[]{"examples/cars/template.ttl", pathToCSV, "src/test/resources/testDump/cars.ttl"};
    }

    @When("I start converting")
    public void i_start_converting() {
        try {
            TestCSV.run(conversionArguments);
            result = success;
        } catch (Exception e) {
            result = failure;
        }
    }

    @Then("I get converted file")
    public void i_get_converted_file() {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        assertTrue(Files.exists(path));
    }

    @When("I give {int} arguments")
    public void i_give_arguments(int numberOfArguments) throws Exception {
        if (numberOfArguments < 3) {
            conversionArguments = new String[]{"examples/cars/template.ttl"};
            TestCSV.run(conversionArguments);
        } else if (numberOfArguments > 3) {
            conversionArguments = new String[]{"cars", "cars", "cars", "cars"};
            TestCSV.run(conversionArguments);
        }
    }

    @Then("I receive no errors")
    public void i_receive_no_errors() {
        assertEquals(givenString, result);
    }

    @Then("I do not get new file")
    public void i_do_not_get_new_file() {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        assertFalse(Files.exists(path));
    }

    @Then("I get an empty file")
    public void i_get_an_empty_file() throws IOException {
        Path path = Paths.get("src/test/resources/testDump/cars.ttl");
        assertEquals(0, Files.size(path));
    }

    @After
    public void clean_up_after_scenarios() {
        givenString = "";
        result = "";
        System.gc();
        Path pathCars = Paths.get("src/test/resources/testDump/cars.ttl");
        if (Files.exists(pathCars)) {
            try {
                Files.delete(pathCars);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
