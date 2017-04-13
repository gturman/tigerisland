Feature: Disallowing the building of a Tiger Playground

  Background:
    Given I am a player
    And I am in the build phase of my turn
    And I still have tigers
    And I choose to build a Tiger Playground

  Scenario: I choose a hex that is uninhabitable
    Given I choose a hex that is uninhabitable
    When I try to place my Tiger
    Then my Tiger is prevented from being placed

  Scenario: I choose a hex that is occupied
    Given I choose a hex that is occupied
    When I try to place my Tiger
    Then my Tiger is prevented from being placed

  Scenario: I choose a hex not adjacent to one of my settlements
    Given I choose a hex not adjacent to one of my settlements
    When I try to place my Tiger
    Then my Totoro is prevented from being placed

  Scenario: I choose a hex that is of level 2 or lower
    Given I choose a hex that is level 2 or lower
    When I try to place my Tiger
    Then my Tiger is prevented from being placed

  Scenario: the settlement already has a Tiger Playground
    Given the settlement already has a Tiger Playground
    When I try to place my Tiger
    Then my Tiger is prevented from being placed
