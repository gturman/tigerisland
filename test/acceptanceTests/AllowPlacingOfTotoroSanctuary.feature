Feature: Allowing the building of a Totoro

  Background:
    Given I am a player
    And I am in the build phase of my turn
    And I still have Totoros
    And I choose to place a Totoro

  Scenario: I choose a hex to add a Totoro to
    Given the hex is inhabitable
    And the hex is unoccupied
    And the hex is adjacent to one of my settlements
    And the settlement is of size 5 or greater
    And the settlement does not already have a Totoro
    When I try to place my Totoro
    Then I should see that my Totoro was placed
    And I should see my Totoro count decrease by 1
    And I should see my point count increase by 200
    And increase settlement size by 1
