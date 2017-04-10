Feature: Disallowing the building of an expansion

  Background:
    Given Im the player
    And I am build phase of my turn
    And I have chosen expand

  Scenario: The hex I want to expand to is occupied
    Given the hex I want to expand to is occupied
    When I try to pick that hex to expand to
    Then my expansion is prevented

  Scenario: The hex I want to expand to is uninhabitable
    Given the hex I want to expand to is on an uninhabitable terrain type
    When I try to pick that terrain to expand to
    Then my expansion is prevented

  Scenario: Trying to expand to a hex that is not adjacent to any of my settlements
    Given the hex I want to expand is not adjacent to any of my settlements
    When I try to pick that hex to expand to
    Then my expansion is prevented

  Scenario: Trying to expand with less villagers than needed to expand fully
    Given the chosen expansion requires more villagers than I currently have
    When I choose to expand
    Then my expansion is prevented
