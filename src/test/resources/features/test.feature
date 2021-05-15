Feature: Basic file conversion

  Scenario: Check if program runs
    Given I have a default csv
    When I run the program
    Then I receive no errors

  Scenario: Generate TTL
    Given I have a default csv
    When I start converting
    Then I get converted file

  Scenario: Not enough arguments
    Given I have a default csv
    When I give 2 arguments
    Then I receive an error

  Scenario: Too many arguments
    Given I have a default csv
    When I give 4 arguments
    Then I receive an error

#java -jar target/csv2rdf-0.0.1-SNAPSHOT.jar examples/cars/template.ttl examples/cars/cars.csv cars.ttl