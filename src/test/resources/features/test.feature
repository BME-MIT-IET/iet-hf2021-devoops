Feature: Basic file conversion

  Scenario: Check if program runs
    Given I have a default csv
    When I start converting
    Then I receive no errors

  Scenario: Generate TTL
    Given I have a default csv
    When I start converting
    Then I get converted file

  Scenario: Not enough arguments
    Given I have a default csv
    When I give 2 arguments
    Then I do not get new file

  Scenario: Too many arguments
    Given I have a default csv
    When I give 4 arguments
    Then I do not get new file

  Scenario: Empty input file
    Given I have an empty csv
    When I start converting
    Then I do not get new file

  Scenario: Incorrect csv structure
    Given I have an incorrect csv
    When I start converting
    Then I get an empty file
