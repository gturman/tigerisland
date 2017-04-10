Feature: Disallowing the building of a Totoro

  Background:
    Given I am a player
    And I am in the build phase of my turn
    And I still have Totoros
    And I choose to place a Totoro

  Scenario: I choose a hex that is uninhabitable
    Given I choose a hex that is uninhabitable
    When I try to place my Totoro
    Then my Totoro is prevented from being placed

  Scenario: I choose a hex that is occupied
    Given I choose a hex that is occupied
    When I try to place my Totoro
    Then my Totoro is prevented from being placed

  Scenario: I choose a hex not adjacent to one of my settlements
    Given I choose a hex not adjacent to one of my settlements
    When I try to place my Totoro
    Then my Totoro is prevented from being placed

  Scenario: I choose a hex adjacent to a settlement of size 4 or less
    Given I choose a hex adjacent to a settlement of size 4 or less
    When I try to place my Totoro
    Then my Totoro is prevented from being placed

  Scenario: the settlement already has a Totoro
    Given the settlement already has a Totoro
    When I try to place my Totoro
    Then my Totoro is prevented from being placed