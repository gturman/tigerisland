Feature: Allowing the building of an expansion

  Background:
    Given I am an player
    And I am in build phase of my turn
    And I still have some villagers
    And I have chosen to expand

  Scenario: I choose a hex to expand to
    Given the hex is a habitable terrain
    And the hex is currently unoccupied
    And the hex is adjacent to my settlement
    And I have enough villagers to expand fully
    When I try to expand to a hex
    Then I should see that each hex I can expand to has acquired the number of villagers equal to the hexes’ level
    And for each villager placed due to the expansion, I should see my villager count decrease by one
    And for each villagers placed due to the expansion, I should see my score increase by the total villagers occupying the hex multiplied by the hexes’ level
    And for each hex expanded to, increase the settlement size by one and merge those tiles into the original settlement expanded from
