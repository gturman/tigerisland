Feature: Allowing the building of a Tiger Playground

  Background:
    Given I am a player
    And I am in the build phase of my turn
    And I still have tigers
    And I choose to build a Tiger Playground

  Scenario: I choose a hex to build a Tiger Playground on
    Given the hex is inhabitable
    And the hex is unoccupied
    And the hex is adjacent to one of my settlements
    And the hex is of level 3 or greater
    And the settlement does not already have a Tiger Playground
    When I try to place my Tiger
    Then I should see that my Tiger was placed
    And I should see my Tiger count decrease by 1
    And I should see my point count increase by 75
    And Increase settlement size by 1
