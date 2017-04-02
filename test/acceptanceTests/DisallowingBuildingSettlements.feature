Feature: Disallowing building a settlement

  Background:
    Given I am the player
    And Im in the build phase of my turn
    And I chose to settle

  Scenario: I place a piece on an unhabitable terrain hex
    Given the hex is an uninhabitable terrain hex
    When I try to place my piece on a hex
    Then my piece is prevented from being placed

  Scenario: I place a piece on an occupied hex
    Given the hex already has a piece on it
    When I try to place my piece on a hex
    Then my piece is prevented from being placed

  Scenario: I place a villager on a hex with level greater than 1
    Given the hex level is greater than one
    When I try to place my piece on a hex
    Then my piece is prevented from being placed
